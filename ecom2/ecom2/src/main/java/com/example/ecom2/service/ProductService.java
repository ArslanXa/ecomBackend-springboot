package com.example.ecom2.service;


import com.example.ecom2.repo.ProductRepo;
import com.example.ecom2.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepo repo;


    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }


    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductByID(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
       product.setImageName(imageFile.getOriginalFilename());
       product.setImageType((imageFile.getContentType()));
       product.setImageData(imageFile.getBytes());
       return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());  
        product.setImageType((imageFile.getContentType()));
        product.setImageData(imageFile.getBytes());
        return repo.save(product);


    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
