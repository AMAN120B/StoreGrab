package com.grab.cartservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grab.cartservice.model.Body;

@Repository
public interface BodyRepo extends JpaRepository<Body,Integer> {
}
