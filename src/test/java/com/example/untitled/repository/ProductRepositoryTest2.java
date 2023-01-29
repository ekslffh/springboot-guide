package com.example.untitled.repository;

import com.example.untitled.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest2 {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void basicCRUDTest() {
        // <create>
        // given
        Product givenProduct = Product.builder()
                .name("노트")
                .price(1000)
                .stock(500)
                .build();

        // when
        Product savedProduct = productRepository.save(givenProduct);

        productRepository.findAll().forEach(System.out::println);
        System.out.println(givenProduct.getNumber());

        // then
        Assertions.assertEquals(givenProduct.getNumber(), savedProduct.getNumber());
        Assertions.assertEquals(givenProduct.getName(), savedProduct.getName());
        Assertions.assertEquals(givenProduct.getPrice(), savedProduct.getPrice());
        Assertions.assertEquals(givenProduct.getStock(), savedProduct.getStock());

        // <read>
        // when
        Product selectedProduct = productRepository.findById(savedProduct.getNumber())
                .orElseThrow(RuntimeException::new);

        // then
        Assertions.assertEquals(givenProduct.getNumber(), selectedProduct.getNumber());
        Assertions.assertEquals(givenProduct.getName(), selectedProduct.getName());
        Assertions.assertEquals(givenProduct.getPrice(), selectedProduct.getPrice());
        Assertions.assertEquals(givenProduct.getStock(), selectedProduct.getStock());

        // <update>
        // when
        Product foundProduct = productRepository.findById(selectedProduct.getNumber())
                .orElseThrow(RuntimeException::new);

        foundProduct.setName("장난감");

        Product updatedProduct = productRepository.save(foundProduct);

        // then
        Assertions.assertEquals("장난감", updatedProduct.getName());

        // <delete>
        // when
        productRepository.delete(updatedProduct);

        // then
        Assertions.assertFalse(productRepository.findById(selectedProduct.getNumber()).isPresent());
    }
}
