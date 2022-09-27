package com.travelers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.travelers.admin.controller", "com.travelers.api.controller"})
public class SwaggerConfig {

    @Bean
    public Docket TravelerAdminApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .groupName("관리자페이지 API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.travelers.admin.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(TravelerApiInfo())
                .tags( new Tag("admin-auth-controller", "어드민 로그인"))
                .tags( new Tag("admin-member-controller", "사용자 관리"))
                .tags( new Tag("admin-product-controller", "여행 상품 관리"))
                .tags( new Tag("admin-banner-controller", "배너 관리"))
                ;
    }

    @Bean
    public Docket TravelerUserApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .groupName("사용자페이지 API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.travelers.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(TravelerApiInfo())
                .tags( new Tag("auth-controller", "회원가입/로그인"))
                .tags( new Tag("member-controller", "사용자 관리"))
                .tags( new Tag("email-controller", "이메일 인증"))
                .tags( new Tag("product-controller", "상품 관리"))
                .tags( new Tag("reservation-controller", "예약 관리"))
                .tags( new Tag("content-controller", "컨텐츠 관리"))
                ;
    }

    private ApiInfo TravelerApiInfo(){
        return new ApiInfoBuilder()
                .title("여행상품 추천 API")
                .description("여행상품 추천 API 목록")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer +accessToken", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Bearer +accessToken", authorizationScopes));
    }
}
