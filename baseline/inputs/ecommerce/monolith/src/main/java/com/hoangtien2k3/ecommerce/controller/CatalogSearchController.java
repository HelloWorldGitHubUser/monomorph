package com.hoangtien2k3.ecommerce.controller;

import com.hoangtien2k3.ecommerce.constants.SortType;
import com.hoangtien2k3.ecommerce.dto.ProductListGetVm;
import com.hoangtien2k3.ecommerce.dto.ProductNameListVm;
import com.hoangtien2k3.ecommerce.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class CatalogSearchController {

    private final ProductSearchService productSearchService;

    @GetMapping("/storefront/catalog-search")
    public ResponseEntity<ProductListGetVm> findProductAdvance(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String attribute,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "DEFAULT") SortType sortType) {
        return ResponseEntity.ok(productSearchService.findProductAdvance(
                keyword, page, size, brand, category, attribute, minPrice, maxPrice, sortType));
    }

    @GetMapping("/storefront/search_suggest")
    public ResponseEntity<ProductNameListVm> productSearchAutoComplete(
            @RequestParam String keyword) {
        return ResponseEntity.ok(productSearchService.autoCompleteProductName(keyword));
    }
}
