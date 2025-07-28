package com.my.springdataelasticsearchexample.service;

import com.my.springdataelasticsearchexample.entity.Product;
import com.my.springdataelasticsearchexample.entity.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(String productId) {
        return productRepository.findById(productId);
    }

    // 모든 상품 조회
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    // 이름으로 상품 검색
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // 카테고리로 상품 검색
    public List<Product> searchByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // 가격 범위로 상품 검색
    public List<Product> searchByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // 상품 삭제
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
}
