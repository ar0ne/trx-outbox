package org.example.consumer.service;

import org.example.consumer.domain.model.Product;
import org.example.consumer.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ProductDto findById(Long id);

    ProductDto save(ProductDto product);

    ProductDto updateProduct(Long id, Product product);

}
