package me.project.progetto.support.dto;

import lombok.Data;
import me.project.progetto.entities.Address;
import me.project.progetto.entities.Customer;
import me.project.progetto.entities.Order;
import me.project.progetto.entities.OrderItem;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
