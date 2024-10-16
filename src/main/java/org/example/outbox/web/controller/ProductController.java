package org.example.outbox.web.controller;

import org.example.outbox.service.ProductService;
import org.example.outbox.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> listProducts() {
        return productService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ProductDto findProduct(@PathVariable Long id) {
        return productService.findById(id);
    }
}
