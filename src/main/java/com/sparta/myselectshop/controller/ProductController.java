package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.entity.ProductMypriceRequestDto;
import com.sparta.myselectshop.entity.ProductRequestDto;
import com.sparta.myselectshop.entity.ProductResponseDto;
import com.sparta.myselectshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductMypriceRequestDto requestDto, BindingResult bindingResult) {
        if (!bindingResult.getFieldErrors().isEmpty()) {
            return new ResponseEntity<>(productService.getProduct(id), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productService.updateProduct(id, requestDto), HttpStatus.OK);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }
}
