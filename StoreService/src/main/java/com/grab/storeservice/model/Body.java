package com.grab.storeservice.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gstId;
    private String storename;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Body() {
    }

    public Body(int gstId, String storename, double totalPrice, Product product) {
        this.gstId = gstId;
        this.storename = storename;
        this.totalPrice = totalPrice;
        this.product = product;
    }

    public int getGstId() {
        return gstId;
    }

    public void setGstId(int gstId) {
        this.gstId = gstId;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Body{" +
                "gstId=" + gstId +
                ", storename='" + storename + '\'' +
                ", totalPrice=" + totalPrice +
                ", product=" + product +
                '}';
    }
}

