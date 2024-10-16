package org.example.outbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.outbox.domain.model.Product;
import org.example.outbox.dto.ProductDto;
import org.example.outbox.exception.OutboxException;
import org.example.outbox.persistence.repository.ProductRepository;
import org.example.outbox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(ProductDto.Mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(ProductDto.Mapper::toDto)
                .orElseThrow(() -> new OutboxException("Can not find product with ID " + id));
    }

    @Transactional
    @Override
    public ProductDto save(ProductDto dto) {
        Product product = ProductDto.Mapper.toModel(dto);
        product.setId(null);
        return ProductDto.Mapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(base -> updateFields(base, product))
                .map(productRepository::save)
                .map(ProductDto.Mapper::toDto)
                .orElseThrow(() -> new OutboxException("Can not find product with ID " + id));
    }

    private Product updateFields(Product base, Product updatedProduct) {
        base.setDescription(updatedProduct.getDescription());
        base.setName(updatedProduct.getName());
        return base;
    }
}
