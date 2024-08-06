package com.grab.cartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason= "No cart with this ID found")
public class CartNotFound extends Exception {
    public CartNotFound(String msg){
        super(msg);
    }
}
