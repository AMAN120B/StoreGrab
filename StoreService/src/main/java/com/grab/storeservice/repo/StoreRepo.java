package com.grab.storeservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grab.storeservice.model.Store;

import java.util.Optional;

@Repository
public interface StoreRepo extends JpaRepository<Store,Integer> {
    public Optional<Store> findByGstId(int gstId);
    public long deleteByGstId(int gstId);
}
