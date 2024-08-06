package com.grab.cartservice.service;

import com.grab.cartservice.exception.CartAlreadyExist;
import com.grab.cartservice.exception.CartNotFound;
import com.grab.cartservice.model.Body;
import com.grab.cartservice.model.Cart;
import com.grab.cartservice.model.Product;

import java.util.List;

public interface IService {
    Cart addcart(Cart p, int cartId) throws CartAlreadyExist;
    boolean deleteCartbyCartid(int cartId) throws CartNotFound;
    Cart addproduct(int cartId, Product p) throws CartNotFound;
    List<Product> getproductsbycartId(int cartId) throws CartNotFound;
    boolean deleteproductBypnameinCart(int cartId, String pname) throws CartNotFound;
    boolean emptycartbycartId(int cartId) throws CartNotFound;
    double checkoutcart();
    List<Body> getproductsforcheckout();
    boolean emptycartaftercheckout();
    void receiveProductFromStore();
}
