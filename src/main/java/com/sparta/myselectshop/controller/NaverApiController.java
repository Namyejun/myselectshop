package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.entity.ItemDto;
import com.sparta.myselectshop.service.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NaverApiController {
    private final NaverApiService naverApiService;

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String query) {
        return naverApiService.searchItems(query);
    }
}

