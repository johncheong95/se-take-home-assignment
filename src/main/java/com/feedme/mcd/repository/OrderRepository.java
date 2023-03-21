package com.feedme.mcd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.feedme.mcd.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

}
