package com.travelers.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-21
 */
@Data
public class BannerDto {
    private List<String> image = new ArrayList<>();
}
