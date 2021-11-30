package com.proyecto.servidor.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="recerva_item ")
public class ReservaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    //------------ Mapped Column -----------//
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
    private VerificarCart shoppingCart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
    //--------------------------------------//

    @Column(name = "quantity")
    private Long quantity=0L;

    @Column(name = "our_price")
    private Float ourPrice=0.0F;

    @Column(name = "total_price")
    private Float totalPrice=0.0F;

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", shoppingCart=" + shoppingCart +
                //", product=" + product +
                ", quantity=" + quantity +
                ", ourPrice=" + ourPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
