package com.my.springdataelasticsearchexample.entity;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product,String> {
    // 이름으로 검색 (대소문자 무시)
    List<Product> findByNameContainingIgnoreCase(String name);

    // 카테고리로 검색
    List<Product> findByCategory(String category);

    // 가격 범위로 검색
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);


}
