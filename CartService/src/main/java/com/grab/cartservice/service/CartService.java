package com.grab.cartservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grab.cartservice.exception.CartAlreadyExist;
import com.grab.cartservice.exception.CartNotFound;
import com.grab.cartservice.model.Body;
import com.grab.cartservice.model.Cart;
import com.grab.cartservice.model.Product;
import com.grab.cartservice.repo.BodyRepo;
import com.grab.cartservice.repo.CartRepo;
import com.grab.cartservice.repo.ProductRepo;

@Service
public class CartService implements IService {

    @Autowired
    private BodyRepo bodyRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Cart addcart(Cart c, int cartId) throws CartAlreadyExist {
        Optional<Cart> opt = cartRepo.findById(cartId);
        if (opt.isPresent()) {
            throw new CartAlreadyExist("The cart already exists");
        }
        return cartRepo.save(c);
    }

    @Override
    public boolean deleteCartbyCartid(int cartId) {
        cartRepo.deleteById(cartId);
        return true;
    }

    @Override
    public Cart addproduct(int cartId, Product p) throws CartNotFound {
        Optional<Cart> opt = cartRepo.findById(cartId);
        if (opt.isPresent()) {
            Cart c = opt.get();
            List<Product> pl = c.getProducts();
            pl.add(p);
            c.setProducts(pl);
            return cartRepo.save(c);
        } else {
            throw new CartNotFound("The Cart with given id does not exist");
        }
    }

    @Override
    public List<Product> getproductsbycartId(int cartId) throws CartNotFound {
        Optional<Cart> getcart = cartRepo.findById(cartId);
        if (getcart.isPresent()) {
            return getcart.get().getProducts();
        } else {
            throw new CartNotFound("The Cart with given id does not exist");
        }
    }

    @Override
    public boolean deleteproductBypnameinCart(int cartId, String productname) throws CartNotFound {
        Optional<Cart> oc = cartRepo.findById(cartId);
        if (oc.isPresent()) {
            Cart c = oc.get();
            List<Product> pl = c.getProducts();
            pl.removeIf(p -> p.getPname().equals(productname));
            c.setProducts(pl);
            cartRepo.save(c);
            return true;
        } else {
            throw new CartNotFound("The Cart with given id does not exist");
        }
    }

    @Override
    public boolean emptycartbycartId(int cartId) throws CartNotFound {
        Optional<Cart> ec = cartRepo.findById(cartId);
        if (ec.isPresent()) {
            Cart cart = ec.get();
            cart.getProducts().clear();
            cartRepo.save(cart);
            return true;
        } else {
            throw new CartNotFound("The Cart with given id does not exist");
        }
    }

    @Override
    public boolean emptycartaftercheckout() {
        bodyRepo.deleteAll();
        return true;
    }

    @Override
    public double checkoutcart() {
        List<Body> bodylist = bodyRepo.findAll();
        double price = bodylist.stream()
                .mapToDouble(p -> 0.01 * (100 - p.getProduct().getDiscount()) * p.getProduct().getPrice() * p.getProduct().getUnit())
                .sum();
        return price;
    }

    @Override
    public List<Body> getproductsforcheckout() {
        return bodyRepo.findAll();
    }

    @Override
    public void receiveProductFromStore() {
        String storeServiceUrl = "http://localhost:8092/api/store/products"; 
        Body[] products = restTemplate.getForObject(storeServiceUrl, Body[].class);
        if (products != null) {
            for (Body body : products) {
                bodyRepo.save(body);
            }
        }
    }
}
