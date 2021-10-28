package com.github.tomekmazurek.petstore.order.repository;

import com.github.tomekmazurek.petstore.order.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
