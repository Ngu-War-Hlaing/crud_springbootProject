package com.example.springbootCRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbootCRUD.model.Product;

public interface ProductsRepository extends JpaRepository<Product,Integer>{

}

