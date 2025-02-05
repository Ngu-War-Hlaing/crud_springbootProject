package com.example.springbootCRUD.model;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="products")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String brand;
	private String category;
	private double price;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private Date createAt;
	private String imageFileName;
	
	public Product() {
		super();
	}
	

	public Product(int id, String name, String brand, String category, double price, String description, Date createAt,
			String imageFileName) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.price = price;
		this.description = description;
		this.createAt = createAt;
		this.imageFileName = imageFileName;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", category=" + category + ", price="
				+ price + ", description=" + description + ", createAt=" + createAt + ", imageFileName=" + imageFileName
				+ "]";
	}
	
	
	
	

}
