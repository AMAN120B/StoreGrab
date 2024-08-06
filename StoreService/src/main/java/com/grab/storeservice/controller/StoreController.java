package com.grab.storeservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grab.storeservice.exception.AllreadyExistingStoreException;
import com.grab.storeservice.exception.ProductAlreadyExists;
import com.grab.storeservice.exception.ProductNotFound;
import com.grab.storeservice.exception.QuantityLessInStore;
import com.grab.storeservice.exception.StoreNotFoundException;
import com.grab.storeservice.model.Product;
import com.grab.storeservice.model.Store;
import com.grab.storeservice.service.StoreService;

import java.util.List;

@RestController
@RequestMapping("api/store")
public class StoreController {
    @Autowired
    private StoreService storeser;



    //controller to add store
    @PostMapping("addstore")
    public ResponseEntity<?> addstore(@RequestBody Store s) throws AllreadyExistingStoreException {
        try {
            Store add = storeser.addStore(s,s.getGstId());
            return new ResponseEntity<>(add, HttpStatus.CREATED);
        }
        catch(AllreadyExistingStoreException ae){
            throw new AllreadyExistingStoreException("The store is already present");
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    //controller to delete any store
    @DeleteMapping("deletestore/{GstId}")
    public ResponseEntity<?> deleteStorebyGstid(@PathVariable int GstId) throws StoreNotFoundException {
        try {
            boolean status = storeser.deleteStore(GstId);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        }
        catch(StoreNotFoundException se){
            throw new StoreNotFoundException("No store found");
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    //controller to get all stores
    @GetMapping("getallstores")
    public ResponseEntity<?> getallstores(){
        List<Store> stores=storeser.getAllStores();
        return new ResponseEntity<>(stores,HttpStatus.OK);
    }



    //controller to update store by its gst id
    @PutMapping("updatestore/{gstId}")
    public ResponseEntity<?> updateStore(@RequestBody Store s, @PathVariable int gstId) throws StoreNotFoundException{
        try {
            Store ss = storeser.updateStore(gstId, s);
            return new ResponseEntity<>(ss, HttpStatus.OK);
        }

        catch(StoreNotFoundException sn){
            throw new StoreNotFoundException("The store does not exist");
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    //controller to add any product by its gstid
    @PostMapping("addProduct/{GstId}")
    public ResponseEntity<?>addProductByGstId(@RequestBody Product p,@PathVariable int GstId) throws StoreNotFoundException, ProductAlreadyExists {
        try {
            boolean status = storeser.addProductByGstId(p, GstId);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        }
        catch(ProductAlreadyExists pe){
            throw new ProductAlreadyExists("The product already exists");
        }
        catch(StoreNotFoundException se){
            throw new StoreNotFoundException("No store found");
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    //controller to delete any product by its gstid and pname
    @DeleteMapping("deleteproduct/{gstId}/{pname}")
    public ResponseEntity<?> deleteProduct(@PathVariable int gstId, @PathVariable String pname) throws StoreNotFoundException, ProductNotFound {
        try {
            boolean status = storeser.deleteProduct(gstId, pname);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }
        catch(ProductNotFound pe){
            throw new ProductNotFound("No product found");
        }
        catch(StoreNotFoundException se){
            throw new StoreNotFoundException("no store found");
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    //controller to show all products of a store
    @GetMapping("showproducts/{gstId}")
    public ResponseEntity<?> showProducts(@PathVariable int gstId) throws StoreNotFoundException {
       try {
           List<Product> l1 = storeser.showProducts(gstId);
           return new ResponseEntity<>(l1, HttpStatus.OK);
       }
       catch(StoreNotFoundException se){
           throw new StoreNotFoundException("The store does not exist");
       }
       catch(Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }





    //update any product by gstid and pname
    @PutMapping("updateproduct/{gstId}/{pname}")
    public ResponseEntity<?> updateproduct(@PathVariable int gstId,@PathVariable String pname,@RequestBody Product p) throws StoreNotFoundException, ProductNotFound {
        try {
            Store ss = storeser.updateProduct(gstId, pname, p);
            return new ResponseEntity<>(ss, HttpStatus.OK);
        }
        catch(ProductNotFound pe){
            throw new ProductNotFound("Product does not exist");
        }
        catch(StoreNotFoundException se){
            throw new StoreNotFoundException("Store not found");
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    //show best discout of a product present in many stores
    @GetMapping("showbestdiscount/{pname}") //product not exist exception needed
    public ResponseEntity<?> showBestDiscount(@PathVariable String pname){
        Store stores=storeser.showBestDiscount(pname);
        return new ResponseEntity<>(stores,HttpStatus.OK);
    }





    //controller to send a body to cart
    @PostMapping("addproducttocart/{gstId}/{pname}/{qty}")
    public ResponseEntity<?> addProductToCart(@PathVariable int gstId,@PathVariable String pname,@PathVariable double qty) throws StoreNotFoundException, ProductNotFound, QuantityLessInStore {
       try{
           storeser.addProductToCart(gstId,pname,qty);
           return new ResponseEntity<>("Successfully Added..",HttpStatus.OK);
       }
       catch(QuantityLessInStore qe){
           throw new QuantityLessInStore("Less quantity");
       }
       catch(Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @DeleteMapping("deleteproducttocart/{gstId}/{pname}/{qty}")
    public ResponseEntity<?> deleteproductincart(@PathVariable int gstId,@PathVariable String pname,@PathVariable double qty) throws StoreNotFoundException, ProductNotFound {

        storeser.delProductToCart(gstId, pname,qty);
        return new ResponseEntity<>("Successfully deleted..", HttpStatus.OK);


    }
}
