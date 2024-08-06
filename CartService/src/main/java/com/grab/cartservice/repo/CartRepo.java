package com.grab.cartservice.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grab.cartservice.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {

    //public boolean deleteByCid(int cid);
    public Cart findByCartId(int cartId);
    public long deleteByCartId(int cartId);
}
