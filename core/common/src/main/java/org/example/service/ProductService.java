package org.example.service;

import org.example.domain.model.Product;
import org.example.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ProductDto findById(Long id);

    ProductDto save(ProductDto product);

    ProductDto updateProduct(Long id, Product product);

}
