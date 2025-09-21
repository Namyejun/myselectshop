package com.sparta.myselectshop.service;

import com.sparta.myselectshop.entity.*;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto, User user) {
        Product product = new Product(productRequestDto, user);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    // 영속성 컨텍스트로 변경감지를 하려면 해줘야 함
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.update(requestDto);
        return new ProductResponseDto(product);
    }

    public ProductResponseDto getProduct(Long id) {
        return new ProductResponseDto(productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found")));
    }

    public List<ProductResponseDto> getProducts(User user) {
        return productRepository.findAllByUser(user).stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductResponseDto::new).toList();
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.updateByItemDto(itemDto);
    }
}
