package com.my.springdataelasticsearchexample;

import com.my.springdataelasticsearchexample.entity.Product;
import com.my.springdataelasticsearchexample.entity.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Arrays;

@SpringBootApplication
public class SpringDataElasticSearchExampleApplication {


	@Autowired
	ElasticsearchOperations operations;
	@Autowired
	ProductRepository repository;


	public static void main(String[] args) {
		SpringApplication.run(SpringDataElasticSearchExampleApplication.class, args);
	}

	@PreDestroy
	public void deleteIndex() {
		operations.indexOps(Product.class).delete();
	}

	@PostConstruct
	public void insertDataSample() {

		operations.indexOps(Product.class).refresh();

		// Save data sample

		var documents = Arrays.asList(
				Product.builder().name("kms1").category("Spring eXchange 2014 - London")
						.price(10.3).description("test1").build(), //
				Product.builder().name("kms2").category("Spring eXchange 2015 - London")
						.price(10.4).description("test2").build(), //
				Product.builder().name("kms3").category("Spring eXchange 2016 - London")
						.price(10.5).description("test3").build(), //
				Product.builder().name("kms4").category("Spring eXchange 2017 - London")
						.price(10.6).description("test4").build(), //
				Product.builder().name("kms5").category("Spring eXchange 2018 - London")
						.price(10.7).description("test5").build()
		);

		repository.saveAll(documents);
	}


}
