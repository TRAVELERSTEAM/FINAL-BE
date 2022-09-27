package com.travelers.biz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.ProductImage;
import com.travelers.biz.domain.ProductStartDate;
import com.travelers.biz.repository.ProductImageRepository;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.biz.repository.ProductStartDateRepository;
import com.travelers.dto.ProductReqDto;
import com.travelers.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    public void registAll(List<ProductReqDto> productReqDtoList) {
        List<Product> productList = new ArrayList<>();
        for(ProductReqDto productReqDto : productReqDtoList) {
            List<ProductStartDate> productStartDates = new ArrayList<>();
            List<ProductImage> productImages = new ArrayList<>();
            Product product = Product.builder()
                    .title(productReqDto.getTitle())
                    .price(productReqDto.getPrice())
                    .thumbnail(productReqDto.getThumbnail())
                    .target(productReqDto.getTarget())
                    .destination(productReqDto.getDestination())
                    .theme(productReqDto.getTheme())
                    .priority(productReqDto.getPriority())
                    .summary(productReqDto.getSummary())
                    .packaging(productReqDto.getPackaging())
                    .startDates(productStartDates)
                    .images(productImages)
                    .build();
            for (Integer startDate : productReqDto.getStartDate() ) {
                ProductStartDate productStartDate = ProductStartDate.builder().product(product).startDate(startDate).build();
                productStartDates.add(productStartDate);
            }
            for (String image : productReqDto.getImage() ) {
                ProductImage productImage = ProductImage.builder().product(product).image(image).build();
                productImages.add(productImage);
            }
            productList.add(product);
        }
        productRepository.saveAll(productList);
    }

    public void loadData() throws IOException {
        List<ProductReqDto> productReqDtoList = new ArrayList<>();
        JsonArray jsonProduct = JsonUtil.getJson("json/product.json");
        for (int i = 0; i < jsonProduct.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonProduct.get(i);
            ProductReqDto productReqDto = ProductReqDto.builder()
                    .title(jsonObject.get("title").getAsString())
                    .price(jsonObject.get("price").getAsInt())
                    .target(jsonObject.get("target").getAsString())
                    .destination(jsonObject.get("destination").getAsString())
                    .theme(jsonObject.get("theme").getAsString())
                    .priority(jsonObject.get("priority").getAsInt())
                    .summary(jsonObject.get("summary").getAsString())
                    .packaging(jsonObject.get("packaging").getAsString())
                    .build();
            for (Object arr : jsonObject.get("startDate").getAsJsonArray()) {
                productReqDto.getStartDate().add(((JsonPrimitive) arr).getAsInt());
            }
            for (Object arr : jsonObject.get("image").getAsJsonArray()) {
                productReqDto.getImage().add(((JsonPrimitive) arr).getAsString());
            }
            productReqDtoList.add(productReqDto);
        }
        JsonArray jsonThumb = JsonUtil.getJson("json/thumbnail.json");
        for (int i = 0; i < jsonThumb.size()&& i< productReqDtoList.size(); i++) {
            ProductReqDto productReqDto = productReqDtoList.get(i);
            productReqDto.setThumbnail(jsonThumb.get(i).getAsString());
        }
        registAll(productReqDtoList);
    }

    public List<Product> getProductAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductDetails(Long id) {
        return productRepository.findById(id);
    }

    public void modifyProduct(Long id, ProductReqDto productReqDto) {
        productRepository.findById(id).ifPresent(product -> {
            product.setTitle(productReqDto.getTitle());
            product.setPrice(productReqDto.getPrice());
            product.setThumbnail(productReqDto.getThumbnail());
            product.setTarget(productReqDto.getTarget());
            product.setDestination(productReqDto.getDestination());
            product.setTheme(productReqDto.getTheme());
            product.setPriority(productReqDto.getPriority());
            product.setSummary(productReqDto.getSummary());
            product.setPackaging(productReqDto.getPackaging());
            // startDate list
            if(product.getStartDates().size() > productReqDto.getStartDate().size()) {
                List<Long> ids = new ArrayList<>();
                for (int idx = product.getStartDates().size(); idx> productReqDto.getStartDate().size(); idx--){
                    product.getStartDates().remove(idx-1);
                }
            }
            for (int idx =0; idx<product.getStartDates().size(); idx++){
                ProductStartDate startDate = product.getStartDates().get(idx);
                startDate.setStartDate(productReqDto.getStartDate().get(idx));
            }
            if(product.getStartDates().size() < productReqDto.getStartDate().size()) {
                for (int idx = product.getStartDates().size(); idx< productReqDto.getStartDate().size(); idx++){
                    ProductStartDate startDate = ProductStartDate.builder()
                            .startDate(productReqDto.getStartDate().get(idx))
                            .product(product).build();
                    product.getStartDates().add(startDate);
                }
            }
            // image list
            if(product.getImages().size() > productReqDto.getImage().size()) {
                List<Long> ids = new ArrayList<>();
                for (int idx = product.getImages().size(); idx> productReqDto.getImage().size(); idx--){
                    product.getImages().remove(idx-1);
                }
            }
            for (int idx =0; idx<product.getImages().size(); idx++){
                ProductImage image = product.getImages().get(idx);
                image.setImage(productReqDto.getImage().get(idx));
            }
            if(product.getImages().size() < productReqDto.getImage().size()) {
                for (int idx = product.getImages().size(); idx< productReqDto.getImage().size(); idx++){
                    ProductImage image = ProductImage.builder()
                            .image(productReqDto.getImage().get(idx))
                            .product(product).build();
                    product.getImages().add(image);
                }
            }
        });
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteProductStartDate(Long productId, Integer startDate) {
        productStartDateRepository.deleteByProductIdAndStartDate(productId, startDate);
    }

    public void deleteProductImage(Long productId, String image) {
        productImageRepository.deleteByProductIdAndImage(productId, image);
    }
}
