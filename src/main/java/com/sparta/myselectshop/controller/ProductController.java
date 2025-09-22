package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.entity.ProductMypriceRequestDto;
import com.sparta.myselectshop.entity.ProductRequestDto;
import com.sparta.myselectshop.entity.ProductResponseDto;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products/{productId}/folder")
    public void addFolder(@PathVariable Long productId, @RequestParam Long folderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        productService.addFolder(productId, folderId, userDetails.getUser());
    }

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.createProduct(productRequestDto, userDetails.getUser());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductMypriceRequestDto requestDto, BindingResult bindingResult) {
        if (!bindingResult.getFieldErrors().isEmpty()) {
            return new ResponseEntity<>(productService.getProduct(id), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productService.updateProduct(id, requestDto), HttpStatus.OK);
    }

    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("isAsc") boolean isAsc,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getProducts(userDetails.getUser(), page - 1, size, sortBy, isAsc);
    }

    @GetMapping("/admin/products")
    public List<ProductResponseDto> getAdminProducts() {
        return productService.getAllProducts();
    }
}
