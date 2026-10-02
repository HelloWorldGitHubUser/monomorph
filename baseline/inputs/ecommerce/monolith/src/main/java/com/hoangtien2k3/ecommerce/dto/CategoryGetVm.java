package com.hoangtien2k3.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryGetVm(Integer categoryId, String categoryTitle, String imageUrl) {
}
