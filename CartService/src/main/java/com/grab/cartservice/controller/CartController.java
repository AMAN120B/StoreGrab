package com.grab.cartservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grab.cartservice.exception.CartAlreadyExist;
import com.grab.cartservice.exception.CartNotFound;
import com.grab.cartservice.model.Body;
import com.grab.cartservice.model.Cart;
import com.grab.cartservice.model.Product;
import com.grab.cartservice.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cservice;


    @GetMapping("showcart")
    public ResponseEntity<?> showcart() {
        List<Body> getprolist = cservice.getproductsforcheckout();

        return new ResponseEntity<>(getprolist , HttpStatus.OK);
    }
    @GetMapping("checkoutcart")
    public ResponseEntity<?> checkoutcart() {
        double checkoutprice = cservice.checkoutcart();
        List<Body> getprolist = cservice.getproductsforcheckout();
        boolean status = cservice.emptycartaftercheckout();
        return new ResponseEntity<>(getprolist + "\n" + checkoutprice, HttpStatus.OK);
    }





    @PostMapping("addcart")
    public ResponseEntity<?> addcart(@RequestBody Cart c) throws CartAlreadyExist {

        try {
            //Store add = storeser.addStore(s,s.getGstId());
            Cart cc = cservice.addcart(c, c.getCartId());
            return new ResponseEntity<>(cc, HttpStatus.CREATED);
        } catch (CartAlreadyExist ce) {
            throw new CartAlreadyExist("The cart already exists");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deletecart/{cartId}")
    public ResponseEntity<?> deletecart(@PathVariable int cartId) {
        boolean deletestatus = cservice.deleteCartbyCartid(cartId);
        return new ResponseEntity<>(deletestatus, HttpStatus.OK);
    }

    @PostMapping("addproduct/{cartId}")
    public ResponseEntity<?> addproduct(@PathVariable int cartId, @RequestBody Product pr) throws CartNotFound {

        try {
            Cart cc = cservice.addproduct(cartId, pr);
            return new ResponseEntity<>(cc, HttpStatus.CREATED);
        } catch (CartNotFound cf) {
            throw new CartNotFound("The Cart with given does not exist");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getproductbycartId/{cartId}")
    public ResponseEntity<?> getproductsbycartId(@PathVariable int cartId) throws CartNotFound {
        List<Product> pros = cservice.getproductsbycartId(cartId);
        return new ResponseEntity<>(pros, HttpStatus.OK);
    }

    @DeleteMapping("delproductbycons/{cartId}/{productname}")
    public ResponseEntity<?> deleteproductsbyconstraints(@PathVariable int cartId, @PathVariable String productname) throws CartNotFound {
        boolean deletestatus = cservice.deleteproductBypnameinCart(cartId, productname);
        return new ResponseEntity<>(deletestatus, HttpStatus.OK);
    }



    //    @DeleteMapping("emptycart/{cartId}")
//    public ResponseEntity<?> empcart(@PathVariable int cartId)
//    {
//        boolean status= cservice.emptycartbycartId(cartId);
//
//        return new ResponseEntity<>(status, HttpStatus.OK);
//    }
//    @GetMapping("checkout/{cartId}")
//    public ResponseEntity<?> checkout(@PathVariable int cartId)
//    {
//        double checkoutprice= cservice.checkout(cartId);
//        boolean emptystatus=cservice.deleteCartbyCartid(cartId);
//        return new ResponseEntity<>(checkoutprice, HttpStatus.OK);
//    }
//    @DeleteMapping("deleteproductandupdatestore/{gstId}/{pname}")
//    public ResponseEntity<?> deleteproductandupdatesotre(@PathVariable int gstId, @PathVariable String pname) {
//
//        cservice.deleteproductandupdatestore(gstId, pname);
//        return new ResponseEntity<>("Successfully deleted,store reflected", HttpStatus.OK);
//
//    }
}
