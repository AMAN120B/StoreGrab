package com.grab.cartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason="Cart is already present you can't add")
public class CartAlreadyExist extends Exception{
    public CartAlreadyExist(String msg){
        super(msg);
    }


}
