package com.sparta.myselectshop;

import com.sparta.myselectshop.entity.ItemDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.service.NaverApiService;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "Scheduled Process")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final NaverApiService naverApiService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void updatePrice() throws InterruptedException {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            TimeUnit.SECONDS.sleep(1); // Naver API를 사용하기 때문에 요청 속도 제한

            List<ItemDto> itemDtoList = naverApiService.searchItems(product.getTitle());

            if (!itemDtoList.isEmpty()) {
                ItemDto itemDto = itemDtoList.get(0);

                Long productId = product.getId();

                try {
                    productService.updateBySearch(productId, itemDto);
                } catch (Exception exception) {
                    log.error("{} : {}", productId, exception.getMessage());
                }
            }

        }
    }
}
