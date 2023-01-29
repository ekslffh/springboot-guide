package com.example.untitled.controller;

import com.example.untitled.dto.ProductDto;
import com.example.untitled.dto.ProductResponseDto;
import com.example.untitled.service.impl.ProductServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductServiceImpl productService;

    @Test
    @DisplayName("MockMvc를 통한 Product 데이터 가져오기 테스트")
    void getProductTest() throws Exception {

        // given
        BDDMockito.given(productService.getProduct(123L)).willReturn(
                new ProductResponseDto(123L, "pen", 5000, 2000));

        String productId = "123";

        // perform: when, andExpect: then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/product?number=" + productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.number").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.stock").exists())
                .andDo(MockMvcResultHandlers.print());

        // productService의 getProduct메서드가 실행됐는지 검증
        Mockito.verify(productService).getProduct(123L);

    }

    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws Exception {
        // given
        BDDMockito.given(productService.saveProduct(new ProductDto("pen", 5000, 2000)))
                .willReturn(new ProductResponseDto(1L, "pen", 5000, 2000));

        ProductDto productDto = ProductDto.builder()
                        .name("pen")
                        .price(5000)
                        .stock(2000)
                        .build();

        // 직렬화 (serialization)
        Gson gson = new Gson();
        String content = gson.toJson(productDto);

        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/product")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.number").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.stock").exists())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(productService).saveProduct(productDto);

    }
}
