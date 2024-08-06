package com.grab.cartservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grab.cartservice.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
	
}
