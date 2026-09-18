package com.bootforge.productservice.service;

import com.bootforge.commons.dto.productservice.CreateProductRequest;
import com.bootforge.commons.dto.productservice.ProductResponse;
import com.bootforge.commons.exception.product.DuplicateProductException;
import com.bootforge.commons.exception.product.ProductNotFoundException;
import com.bootforge.productservice.entity.Product;
import com.bootforge.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new DuplicateProductException("Product already exists with product!!");
        }
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .build();

        Product savedProduct = productRepository.save(product);

        return toProductResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toProductResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(Long id) {
        return toProductResponse(getProductById(id));
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow(
                () -> new ProductNotFoundException("Product not found with productName: " + name)
        );
        return toProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, CreateProductRequest request) {
        Product product = getProductById(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        Product updatedProduct = productRepository.save(product);

        return toProductResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.delete(getProductById(id));
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with productId: " + id)
        );
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
