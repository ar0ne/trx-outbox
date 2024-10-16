package org.example.outbox.service;

import org.example.outbox.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ProductDto findById(Long id);

    ProductDto save(ProductDto product);

}
