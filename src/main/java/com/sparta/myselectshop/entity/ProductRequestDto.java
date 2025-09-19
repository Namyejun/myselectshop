package com.sparta.myselectshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String title;
    private String link;
    private String image;
    private int lprice;
}
