package com.example.springbootCRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.springbootCRUD.model.Product;
import com.example.springbootCRUD.model.ProductDto;
import com.example.springbootCRUD.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products") // to see url_name as products
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping({ "", "/" }) // default mapping
    public String showProductList(Model model) {
        List<Product> products = productService.getAllproducts();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/createProducts";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("productDto") ProductDto productDto,
            BindingResult bindingResult, Model model) {
    	  if (productDto.getImageFile() == null || productDto.getImageFile().isEmpty()) {
              bindingResult.addError(new FieldError("productDto", "imageFile", "The image file is required"));
          }
        if (bindingResult.hasErrors()) {
            return "products/createProducts";
        }
        productService.createProduct(productDto);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
    
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model) {
        Product product = productService.getAllproducts().stream().filter(p -> p.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        model.addAttribute("productDto", productDto);
        return "products/editProducts";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @Valid @ModelAttribute("productDto") ProductDto productDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "products/editProducts";
        }
        productService.editProduct(id, productDto);
        return "redirect:/products";
    }

}
