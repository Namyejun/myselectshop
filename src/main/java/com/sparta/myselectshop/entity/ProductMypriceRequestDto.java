package com.sparta.myselectshop.entity;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMypriceRequestDto {
    @Min(100)
    private int myprice;
}
