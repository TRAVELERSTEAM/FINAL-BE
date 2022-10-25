package com.travelers.biz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travelers.biz.domain.product.Product;
import com.travelers.biz.domain.product.embeddable.Price;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.dto.ProductRequestDto;
import com.travelers.dto.ProductResponseDto;
import com.travelers.exception.TravelersException;
import com.travelers.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.travelers.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 공용
    // 여행상품 전체목록 보기
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProduct() {
        return productRepository.findAll()
                .stream().map(ProductResponseDto::of).collect(Collectors.toList());
    }

    // 여행상품 상세보기
    @Transactional(readOnly = true)
    public ProductResponseDto findProduct(Long ProductId) {
        Product product = productRepository.findById(ProductId)
                .orElseThrow(() -> new TravelersException(PRODUCT_NOT_FOUND));
        return ProductResponseDto.of(product);
    }

    /*****************************************************************************/

    // 여행상품 넣기
    @Transactional
    public void loadData() throws IOException {
        JsonArray jsonProduct = JsonUtil.getJson("json/product.json");
        for (int i = 0; i < jsonProduct.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonProduct.get(i);
            JsonObject priceObj = jsonObject.getAsJsonObject("price");
            Long adultPrice = priceObj.get("adult").getAsLong();
            Long kidPrice = priceObj.get("kid").getAsLong();
            Long infantPrice = priceObj.get("infant").getAsLong();
            Price price = Price.builder()
                    .adult(adultPrice)
                    .kid(kidPrice)
                    .infant(infantPrice)
                    .build();

            JsonArray startTemp = jsonObject.get("startDate").getAsJsonArray();
            JsonArray summaryTemp = jsonObject.get("summary").getAsJsonArray();
            JsonArray subImageTemp = jsonObject.get("subImage").getAsJsonArray();
            List<String> st = new ArrayList<>();
            List<String> sy = new ArrayList<>();
            List<String> si = new ArrayList<>();
            for (int j = 0; j < startTemp.size(); j++) {
                st.add(startTemp.get(j).toString());
            }
            for (int j = 0; j < summaryTemp.size(); j++) {
                sy.add(summaryTemp.get(j).toString());
            }
            for (int j = 0; j < subImageTemp.size(); j++) {
                si.add(subImageTemp.get(j).toString());
            }

            ProductRequestDto productRequestDto = ProductRequestDto.builder()
                            .id(jsonObject.get("id").getAsLong())
                            .title(jsonObject.get("title").getAsString())
                            .target(jsonObject.get("target").getAsString())
                            .area(jsonObject.get("area").getAsString())
                            .theme(jsonObject.get("theme").getAsString())
                            .startDate(st)
                            .summary(sy)
                            .period(jsonObject.get("period").getAsInt())
                            .mainImage(jsonObject.get("mainImage").getAsString())
                            .subImages(si)
                            .price(price)
                    .build();

            productRepository.save(productRequestDto.toProduct());
        }
    }

}
