import { CartItem } from './cart-item';

export class OrderItem {
    imageURL!: string;
    unitPrice!: number;
    quantity!: number;
    productId!: string;

    constructor(cartItem: CartItem) {
        this.imageURL = cartItem.imageURL;
        this.quantity = cartItem.quantity;
        this.unitPrice = cartItem.unitPrice;
        this.productId = cartItem.id;
    }
    
}