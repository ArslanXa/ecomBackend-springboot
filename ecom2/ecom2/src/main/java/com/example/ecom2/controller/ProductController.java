package com.example.ecom2.controller;

import com.example.ecom2.model.Product;
import com.example.ecom2.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String greet() {
        return "hello";
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct (@PathVariable int id){

        Product product = service.getProductByID(id);

        if(product !=null)
            return new ResponseEntity<>(service.getProductByID(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




        @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> addProduct(@RequestPart Product product,
                                            @RequestPart("imageFile") MultipartFile imageFile){
            try {
                Product product1 = service.addProduct(product,imageFile);
                return new ResponseEntity<>(product1, HttpStatus.CREATED);
            }
            catch (Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("product/{productid}/image")
        public ResponseEntity<byte[]> getImageByProductID(@PathVariable int productid){

        Product product = service.getProductByID(productid);// cz were getting image in a product
        byte[] imageFile= product.getImageData();


        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
        }

        @PutMapping("/product/{id}")
        public ResponseEntity<?> updateProduct(@PathVariable int id,@RequestPart Product product,
                                            @RequestPart MultipartFile imageFile){
            try {
                Product product1 =service.updateProduct(id,product,imageFile);
            } catch (IOException e) {
                return new ResponseEntity<>("failed to update", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }

        @DeleteMapping("/product/{id}")
        public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product= service.getProductByID(id);///checking if it exists
            if(product!=null) {
                service.deleteProduct(id);
                return new ResponseEntity<>("deleted", HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("failed to delete, product doesnt exist", HttpStatus.NOT_FOUND);
        }

        @GetMapping("/products/search")
        public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products =service.searchProducts(keyword);
            System.out.println("searching with" + keyword);
            return new ResponseEntity<>(products , HttpStatus.OK);
        }
}
