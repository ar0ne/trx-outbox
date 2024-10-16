package org.example.api.web.controller;

import org.example.persistence.model.Product;
import org.example.dto.ProductDto;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Validated ProductDto dto) {
        return productService.save(dto);
    }

    @PutMapping(value = "/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody @Validated ProductDto dto) {
        Product updatedProduct = ProductDto.Mapper.toModel(dto);
        return productService.updateProduct(id, updatedProduct);
    }
}
