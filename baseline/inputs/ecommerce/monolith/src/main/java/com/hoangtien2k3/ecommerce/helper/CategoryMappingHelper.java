package com.hoangtien2k3.ecommerce.helper;

import com.hoangtien2k3.ecommerce.model.product.Category;
import com.hoangtien2k3.ecommerce.dto.CategoryDto;

public interface CategoryMappingHelper {

    static CategoryDto map(final Category category) {
        final var builder = CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .imageUrl(category.getImageUrl())
                ;

        if (category.getParentCategory() != null) {
            final var parentCategory = category.getParentCategory();
            builder.parentCategoryDto(
                    CategoryDto.builder()
                            .categoryId(parentCategory.getCategoryId())
                            .categoryTitle(parentCategory.getCategoryTitle())
                            .imageUrl(parentCategory.getImageUrl())
                            .build());
        }

        return builder.build();
    }

    static Category map(CategoryDto categoryDto) {
        final var builder = Category.builder()
                .categoryId(categoryDto.getCategoryId())
                .categoryTitle(categoryDto.getCategoryTitle())
                .imageUrl(categoryDto.getImageUrl())
                ;

        if (categoryDto.getParentCategoryDto() != null) {
            final var parentCategoryDto = categoryDto.getParentCategoryDto();
            builder.parentCategory(Category.builder()
                    .categoryId(parentCategoryDto.getCategoryId())
                    .categoryTitle(parentCategoryDto.getCategoryTitle())
                    .imageUrl(parentCategoryDto.getImageUrl())
                    .build());
        }

        return builder.build();
    }

}
