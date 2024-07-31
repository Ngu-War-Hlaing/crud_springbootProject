package com.example.springbootCRUD.service;

import java.util.List;

import com.example.springbootCRUD.model.Product;
import com.example.springbootCRUD.model.ProductDto;

public interface ProductService {
	List<Product> getAllproducts();
	void createProduct(ProductDto productDto);
	void deleteProduct(int id);
	void editProduct(int id, ProductDto productDto);
}
