package com.devsuperior.descommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.descommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
