package me.project.progetto.services;

import me.project.progetto.entities.Customer;
import me.project.progetto.entities.Order;
import me.project.progetto.entities.OrderItem;
import me.project.progetto.entities.Product;
import me.project.progetto.repositories.CustomerRepository;
import me.project.progetto.repositories.ProductRepository;
import me.project.progetto.support.dto.Purchase;
import me.project.progetto.support.dto.PurchaseResponse;
import me.project.progetto.support.exceptions.QuantityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutService {

    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutService(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //Ricaviamo le informazioni dal dto
        Order order = purchase.getOrder();

        //Generiamo un numero di tracking
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //Aggiungiamo gli orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();

            //---CHECK QUANTITY---
            for(OrderItem item : orderItems) {
                Optional<Product> op = productRepository.findById(item.getProductId());
                Product product = op.get();
                int quantity = product.getUnitsInStock() - item.getQuantity();
                if(quantity < 0) throw new QuantityException("ERROR: Quantity unavailable!");
                product.setUnitsInStock(quantity);
                productRepository.save(product);
            }//---CHECK QUANTITY

        orderItems.forEach(item -> order.add(item));

        //Aggiungiamo gli indirizzi di spedizione e fatturazione
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //Aggiungiamo il cliente
        Customer customer = purchase.getCustomer();

        //Check per verificare che il cliente esista
        String email = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(email);

        if (customerFromDB != null) {
            //Cliente trovato!
            customer = customerFromDB;
        }

        customer.add(order);

        //Salviamolo nel database
        customerRepository.save(customer);

        //Restituiamo una risposta
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

}