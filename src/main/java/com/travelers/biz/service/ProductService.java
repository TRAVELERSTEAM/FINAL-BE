package com.travelers.biz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.ProductImage;
import com.travelers.biz.domain.ProductStartDate;
import com.travelers.biz.repository.ProductImageRepository;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.biz.repository.ProductStartDateRepository;
import com.travelers.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author kei
 * @since 2022-09-06
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductStartDateRepository productStartDateRepository;
    private final ProductImageRepository productImageRepository;

    public void registAll(List<ProductDto> productDtoList) {
        List<Product> productList = new ArrayList<>();
        for(ProductDto productDto: productDtoList) {
            List<ProductStartDate> productStartDates = new ArrayList<>();
            for (Integer startDate : productDto.getStartDate() ) {
                ProductStartDate productStartDate = ProductStartDate.builder().startDate(startDate).build();
                productStartDates.add(productStartDate);
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (String image : productDto.getImage() ) {
                ProductImage productImage = ProductImage.builder().image(image).build();
                productImages.add(productImage);
            }
            Product product = Product.builder()
                    .title(productDto.getTitle())
                    .price(productDto.getPrice())
                    .thumbnail(productDto.getThumbnail())
                    .target(productDto.getTarget())
                    .destination(productDto.getDestination())
                    .theme(productDto.getTheme())
                    .startDates(productStartDates)
                    .images(productImages)
                    .build();
            productList.add(product);
        }
        productRepository.saveAll(productList);
    }

    private JsonArray getJson(String data) throws IOException {
        ClassPathResource resource = new ClassPathResource(data);
        JsonArray jsonArray = (JsonArray) JsonParser.parseReader(new InputStreamReader(resource.getInputStream()));
        return jsonArray;
    }

    public void loadData() throws IOException {
        List<ProductDto> productDtoList = new ArrayList<>();
        JsonArray jsonProduct = getJson("json/product.json");
        for (int i = 0; i < jsonProduct.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonProduct.get(i);
            ProductDto productDto = ProductDto.builder()
                    .title(jsonObject.get("title").getAsString())
                    .price(jsonObject.get("price").getAsInt())
                    .target(jsonObject.get("target").getAsString())
                    .destination(jsonObject.get("destination").getAsString())
                    .theme(jsonObject.get("theme").getAsString())
                    .build();
            for (Object arr : jsonObject.get("startDate").getAsJsonArray()) {
                productDto.getStartDate().add(((JsonPrimitive) arr).getAsInt());
            }
            for (Object arr : jsonObject.get("image").getAsJsonArray()) {
                productDto.getImage().add(((JsonPrimitive) arr).getAsString());
            }
            productDtoList.add(productDto);
        }
        JsonArray jsonThumb = getJson("json/thumbnail.json");
        for (int i = 0; i < jsonThumb.size()&& i<productDtoList.size(); i++) {
            ProductDto productDto = productDtoList.get(i);
            productDto.setThumbnail(jsonThumb.get(i).getAsString());
        }
        registAll(productDtoList);
    }

    public List<Product> getProductAll() {
        return productRepository.findAll();
    }
}
