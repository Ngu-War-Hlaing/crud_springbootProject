package com.example.springbootCRUD.service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootCRUD.model.Product;
import com.example.springbootCRUD.model.ProductDto;
import com.example.springbootCRUD.repository.ProductsRepository;

@Service
public class ProductServiceimpl implements ProductService{
	
	@Autowired
	private ProductsRepository repo;

	@Override
	public List<Product> getAllproducts() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public void createProduct(ProductDto productDto) {
        MultipartFile image = productDto.getImageFile();
        Date createAt = new Date();
        
        String originalFileName = image.getOriginalFilename();
               
	      try {
	      	String uploadDir="public/images/";
	      	Path uploadPath=Paths.get(uploadDir);
	      	
	        	if(!Files.exists(uploadPath)){
	        		Files.createDirectories(uploadPath);
	        	}
			       	String storageFileName = generateUniqueFileName(originalFileName);
			       	try (InputStream inputStream=image.getInputStream()){
			       		Files.copy(inputStream, Paths.get(uploadDir+storageFileName),
			      				StandardCopyOption.REPLACE_EXISTING);
			       	}catch(Exception ex) {
			       			System.out.println("Exception:"+ex.getMessage());
			       		}
	       	
	        // Create and save the product
	        Product pd = new Product();
	        pd.setName(productDto.getName());
	        pd.setBrand(productDto.getBrand());
	        pd.setCategory(productDto.getCategory());
	        pd.setPrice(productDto.getPrice());
	        pd.setDescription(productDto.getDescription());
	        pd.setCreateAt(createAt);
	        pd.setImageFileName(storageFileName);
	       
	
	        repo.save(pd);
	      } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	    	  
	      }
        }
	       
   private String generateUniqueFileName(String originalFileName) {
       
       String extension = "";
       int dotIndex = originalFileName.lastIndexOf('.');
       if (dotIndex >= 0) {
           extension = originalFileName.substring(dotIndex);
       }
       return System.currentTimeMillis()+ extension;
   }
   
   @Override
   public void editProduct(int id, ProductDto productDto) {
       Optional<Product> optionalProduct = repo.findById(id);
       if (optionalProduct.isPresent()) {
           Product existingProduct = optionalProduct.get();
           existingProduct.setName(productDto.getName());
           existingProduct.setBrand(productDto.getBrand());
           existingProduct.setCategory(productDto.getCategory());
           existingProduct.setPrice(productDto.getPrice());
           existingProduct.setDescription(productDto.getDescription());

           MultipartFile image = productDto.getImageFile();
           if (image != null && !image.isEmpty()) {
               String originalFileName = image.getOriginalFilename();
               try {
                   String uploadDir = "public/images/";
                   Path uploadPath = Paths.get(uploadDir);

                   if (!Files.exists(uploadPath)) {
                       Files.createDirectories(uploadPath);
                   }
                   String storageFileName = generateUniqueFileName(originalFileName);
                   try (InputStream inputStream = image.getInputStream()) {
                       Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                   } catch (Exception ex) {
                       System.out.println("Exception:" + ex.getMessage());
                   }
                   existingProduct.setImageFileName(storageFileName);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           repo.save(existingProduct);
       } else {
           throw new RuntimeException("Product not found");
       }
   }

   @Override
   public void deleteProduct(int id) {
       Optional<Product> optionalProduct = repo.findById(id);
       if (optionalProduct.isPresent()) {
           Product product = optionalProduct.get();
           
           // Get the image file name
           String imageFileName = product.getImageFileName();
           
           // Delete the product from the repository
           repo.deleteById(id);
           
           // Delete the image file from the file system
           if (imageFileName != null && !imageFileName.isEmpty()) {
               Path imagePath = Paths.get("public/images/", imageFileName);
               try {
                   Files.deleteIfExists(imagePath);
               } catch (IOException e) {
                   e.printStackTrace();
                   System.out.println("Failed to delete image file: " + imageFileName);
               }
           }
       } else {
           throw new RuntimeException("Product not found");
       }
   }
   
   
       
}
