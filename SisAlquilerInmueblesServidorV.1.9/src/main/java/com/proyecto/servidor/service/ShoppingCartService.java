package com.proyecto.servidor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.servidor.model.ReservaItem;
import com.proyecto.servidor.model.Customer;
import com.proyecto.servidor.model.Product;
import com.proyecto.servidor.model.VerificarCart;
import com.proyecto.servidor.repository.CartItemRepository;
import com.proyecto.servidor.repository.ShoppingCartRepository;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private Float TAX_RATE =5.0F; //5% Percent

    public VerificarCart findById(Long shoppingCartId){
        return shoppingCartRepository.findById(shoppingCartId).get();
    }

    private ReservaItem findCartItem(VerificarCart shoppingCart, Long productId){

        System.out.println("Cart item count"+shoppingCart.getCartItemList().stream().count());

        for (ReservaItem cartItem : shoppingCart.getCartItemList()) {
            //item found
            if (cartItem.getProduct().getId().equals(productId)) {
                return cartItem;
            }
        }
        return null;
    }
    private Float getSubTotal(VerificarCart shoppingCart){
        Float subTotal=0.0F;
        for (ReservaItem cartItem : shoppingCart.getCartItemList()) {
            subTotal += cartItem.getTotalPrice();
        }
        return subTotal;
    }
    public VerificarCart findShoppingCart(Customer customer){
        if(customer.getShoppingCart() == null){
            VerificarCart shoppingCart = new VerificarCart();

            shoppingCart.setPaymentMethod("Pago contra reembolso");
            shoppingCart.setShippingMethod("telefono + delivery");

            shoppingCart.setCustomer(customer);
            shoppingCart.setCartItemList(new ArrayList<ReservaItem>());

            return shoppingCart;
        }
        return customer.getShoppingCart();
    }

    public VerificarCart addItemToCart(Product product, Long quantity, Customer customer) {
        Float subTotal = 0.0F, totalPrice = 0.0F, totalTax = 0.0F, grandTotal = 0.0F;
        long totalQty = 0;

        VerificarCart shoppingCart = findShoppingCart(customer);
        ReservaItem cartItem = findCartItem(shoppingCart, product.getId());
        if(cartItem == null){
            cartItem = new ReservaItem();

            cartItem.setProduct(product);
            cartItem.setShoppingCart(shoppingCart);

            //get items list and item in it
            List<ReservaItem> cartItemList = shoppingCart.getCartItemList();
            cartItemList.add(cartItem);

            shoppingCart.setCartItemList(cartItemList);
        }

        cartItem.setOurPrice(product.getOurPrice());

        //quantity
        totalQty = cartItem.getQuantity() + quantity;
        cartItem.setQuantity(totalQty);

        //total
        totalPrice = product.getOurPrice() * totalQty;
        cartItem.setTotalPrice(totalPrice);


        //-------------------//
        shoppingCart.setShippingTotal(0.0F);

        //sub total
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);

        //Tax rate
        shoppingCart.setTaxRate(TAX_RATE);

        //total Tax
        totalTax = (TAX_RATE / 100) * subTotal;
        shoppingCart.setTaxTotal(totalTax);

        //grand total
        grandTotal = subTotal + totalTax;
        shoppingCart.setGrandTotal(grandTotal);

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }
    public VerificarCart removeItemFromCart(Product product, Customer customer){
        Float subTotal = 0.0F, totalPrice = 0.0F, totalTax = 0.0F, grandTotal = 0.0F;
        long totalQty = 0;

        VerificarCart shoppingCart = findShoppingCart(customer);
        ReservaItem cartItem = findCartItem(shoppingCart, product.getId());

        System.out.println("cart item="+cartItem);

        List<ReservaItem> cartItemList = shoppingCart.getCartItemList();
        System.out.println("before size="+cartItemList.stream().count());

        cartItemList.remove(cartItem);

        System.out.println("after size="+cartItemList.stream().count());

        //set updated cart item
        shoppingCart.setCartItemList(cartItemList);

        //-------------------//
        shoppingCart.setShippingTotal(0.0F);

        //sub total
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);

        //Tax rate
        shoppingCart.setTaxRate(TAX_RATE);

        //total Tax
        totalTax = (TAX_RATE / 100) * subTotal;
        shoppingCart.setTaxTotal(totalTax);

        //grand total
        grandTotal = subTotal + totalTax;
        shoppingCart.setGrandTotal(grandTotal);

        shoppingCartRepository.save(shoppingCart);

        //deleted instance passed to merge:

        //Delete child item
        cartItemRepository.delete(cartItem);

        return shoppingCart;
    }

    public VerificarCart updateItemFromCart(Product product, Long quantity, Customer customer){
        Float subTotal = 0.0F, totalPrice = 0.0F, totalTax = 0.0F, grandTotal = 0.0F;
        long totalQty = 0;

        VerificarCart shoppingCart = findShoppingCart(customer);
        ReservaItem cartItem = findCartItem(shoppingCart, product.getId());

        cartItem.setOurPrice(product.getOurPrice());

        //quantity
        totalQty = quantity;
        cartItem.setQuantity(totalQty);

        //total
        totalPrice = product.getOurPrice() * totalQty;
        cartItem.setTotalPrice(totalPrice);


        //-------------------//
        shoppingCart.setShippingTotal(0.0F);

        //sub total
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);

        //Tax rate
        shoppingCart.setTaxRate(TAX_RATE);

        //total Tax
        totalTax = (TAX_RATE / 100) * subTotal;
        shoppingCart.setTaxTotal(totalTax);

        //grand total
        grandTotal = subTotal + totalTax;
        shoppingCart.setGrandTotal(grandTotal);

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }


    public void emptyShoppingCart(Customer customer) {
        if(customer == null || customer.getShoppingCart()==null) return;

        shoppingCartRepository.delete(customer.getShoppingCart());
    }

}