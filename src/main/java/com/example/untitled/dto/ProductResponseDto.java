package com.example.untitled.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDto {

    private Long number;
    private String name;
    private int price;
    private int stock;

}
