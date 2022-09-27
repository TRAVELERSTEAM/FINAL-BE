package com.travelers.biz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.ProductImage;
import com.travelers.biz.domain.ProductStartDate;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.dto.ProductDto;
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

    public void registAll(List<ProductDto> productDtoList) {
        List<Product> productList = new ArrayList<>();
        for(ProductDto productDto: productDtoList) {
            List<ProductStartDate> productStartDates = new ArrayList<>();
            List<ProductImage> productImages = new ArrayList<>();
            Product product = Product.builder()
                    .title(productDto.getTitle())
                    .price(productDto.getPrice())
                    .thumbnail(productDto.getThumbnail())
                    .target(productDto.getTarget())
                    .destination(productDto.getDestination())
                    .theme(productDto.getTheme())
                    .priority(productDto.getPriority())
                    .summary(productDto.getSummary())
                    .packaging(productDto.getPackaging())
                    .startDates(productStartDates)
                    .images(productImages)
                    .build();
            for (Integer startDate : productDto.getStartDate() ) {
                ProductStartDate productStartDate = ProductStartDate.builder().product(product).startDate(startDate).build();
                productStartDates.add(productStartDate);
            }
            for (String image : productDto.getImage() ) {
                ProductImage productImage = ProductImage.builder().product(product).image(image).build();
                productImages.add(productImage);
            }
            productList.add(product);
        }
        productRepository.saveAll(productList);
    }

    public void loadData() throws IOException {
        List<ProductDto> productDtoList = new ArrayList<>();
        JsonArray jsonProduct = JsonUtil.getJson("json/product.json");
        for (int i = 0; i < jsonProduct.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonProduct.get(i);
            ProductDto productDto = ProductDto.builder()
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
                productDto.getStartDate().add(((JsonPrimitive) arr).getAsInt());
            }
            for (Object arr : jsonObject.get("image").getAsJsonArray()) {
                productDto.getImage().add(((JsonPrimitive) arr).getAsString());
            }
            productDtoList.add(productDto);
        }
        JsonArray jsonThumb = JsonUtil.getJson("json/thumbnail.json");
        for (int i = 0; i < jsonThumb.size()&& i<productDtoList.size(); i++) {
            ProductDto productDto = productDtoList.get(i);
            productDto.setThumbnail(jsonThumb.get(i).getAsString());
        }
        registAll(productDtoList);
    }

    public List<Product> getProductAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductDetails(Long id) {
        return productRepository.findById(id);
    }

    public void modifyProduct(Long id, ProductDto productDto) {
        productRepository.findById(id).ifPresent(product -> {
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            product.setThumbnail(productDto.getThumbnail());
            product.setTarget(productDto.getTarget());
            product.setDestination(productDto.getDestination());
            product.setTheme(productDto.getTheme());
            product.setPriority(productDto.getPriority());
            product.setSummary(productDto.getSummary());
            product.setPackaging(productDto.getPackaging());
            // startDate list
            if(product.getStartDates().size() > productDto.getStartDate().size()) {
                List<Long> ids = new ArrayList<>();
                for (int idx = product.getStartDates().size(); idx>productDto.getStartDate().size(); idx--){
                    product.getStartDates().remove(idx-1);
                }
            }
            for (int idx =0; idx<product.getStartDates().size(); idx++){
                ProductStartDate startDate = product.getStartDates().get(idx);
                startDate.setStartDate(productDto.getStartDate().get(idx));
            }
            if(product.getStartDates().size() < productDto.getStartDate().size()) {
                for (int idx =product.getStartDates().size(); idx<productDto.getStartDate().size(); idx++){
                    ProductStartDate startDate = ProductStartDate.builder()
                            .startDate(productDto.getStartDate().get(idx))
                            .product(product).build();
                    product.getStartDates().add(startDate);
                }
            }
            // image list
            if(product.getImages().size() > productDto.getImage().size()) {
                List<Long> ids = new ArrayList<>();
                for (int idx = product.getImages().size(); idx>productDto.getImage().size(); idx--){
                    product.getImages().remove(idx-1);
                }
            }
            for (int idx =0; idx<product.getImages().size(); idx++){
                ProductImage image = product.getImages().get(idx);
                image.setImage(productDto.getImage().get(idx));
            }
            if(product.getImages().size() < productDto.getImage().size()) {
                for (int idx =product.getImages().size(); idx<productDto.getImage().size(); idx++){
                    ProductImage image = ProductImage.builder()
                            .image(productDto.getImage().get(idx))
                            .product(product).build();
                    product.getImages().add(image);
                }
            }
        });
    }


}
