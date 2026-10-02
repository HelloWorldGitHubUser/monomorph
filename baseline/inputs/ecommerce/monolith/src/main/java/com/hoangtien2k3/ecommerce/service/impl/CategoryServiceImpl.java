package com.hoangtien2k3.ecommerce.service.impl;

import com.hoangtien2k3.ecommerce.dto.CategoryDto;
import com.hoangtien2k3.ecommerce.exception.wrapper.CategoryNotFoundException;
import com.hoangtien2k3.ecommerce.helper.CategoryMappingHelper;
import com.hoangtien2k3.ecommerce.model.product.Category;
import com.hoangtien2k3.ecommerce.repository.product.CategoryRepository;
import com.hoangtien2k3.ecommerce.repository.product.CategoryRepositoryPagingAndSorting;
import com.hoangtien2k3.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final CategoryRepositoryPagingAndSorting categoryRepositoryPagingAndSorting;

    @Override
    public List<CategoryDto> findAll() {
        log.info("Category List Service, fetch all category");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMappingHelper::map)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Page<CategoryDto> findAllCategory(int page, int size) {
        log.info("*** CategoryDto List, service; fetch all categories ***");

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<CategoryDto> categoryDtos = categoryPage.getContent()
                .stream()
                .map(CategoryMappingHelper::map)
                .distinct()
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDtos, pageable, categoryPage.getTotalElements());
    }

    @Override
    public List<CategoryDto> getAllCategories(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Category> pagedResult = categoryRepositoryPagingAndSorting.findAllPagedAndSortedCategories(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent()
                    .stream()
                    .map((element) -> modelMapper.map(element, CategoryDto.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public CategoryDto findById(Integer categoryId) {
        log.info("CategoryDto Service, fetch category by id");
        return categoryRepository.findById(categoryId)
                .map(CategoryMappingHelper::map)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id[%d] not found", categoryId)));
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        log.info("CategoryDto, service; save category");
        try {
            Category category = CategoryMappingHelper.map(categoryDto);
            return CategoryMappingHelper.map(categoryRepository.save(category));
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNotFoundException("Bad Request", e);
        }
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("CategoryDto Service, update category");

        try {
            Category existingCategory = categoryRepository.findById(categoryDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryDto.getCategoryId()));

            BeanUtils.copyProperties(categoryDto, existingCategory, "categoryId", "parentCategoryDto");

            if (categoryDto.getParentCategoryDto() != null) {
                existingCategory.setParentCategory(CategoryMappingHelper.map(categoryDto.getParentCategoryDto()));
            }

            return CategoryMappingHelper.map(categoryRepository.save(existingCategory));
        } catch (CategoryNotFoundException e) {
            log.error("Error updating category. Category with id [{}] not found.", categoryDto.getCategoryId());
            throw new CategoryNotFoundException(String.format("Category with id [%d] not found.", categoryDto.getCategoryId()), e);
        } catch (DataIntegrityViolationException e) {
            log.error("Error updating category: Data integrity violation", e);
            throw new CategoryNotFoundException("Error updating category: Data integrity violation", e);
        } catch (Exception e) {
            log.error("Error updating category", e);
            throw new CategoryNotFoundException("Error updating category", e);
        }
    }

    @Override
    public CategoryDto update(Integer categoryId, CategoryDto categoryDto) {
        log.info("CategoryDto Service: Updating category with categoryId");

        try {
            CategoryDto existingCategoryDto = this.findById(categoryId);

            Category existingCategory = CategoryMappingHelper.map(existingCategoryDto);
            BeanUtils.copyProperties(categoryDto, existingCategory, "categoryId", "parentCategoryDto");

            if (categoryDto.getParentCategoryDto() != null) {
                existingCategory.setParentCategory(CategoryMappingHelper.map(categoryDto.getParentCategoryDto()));
            }

            Category updatedCategory = categoryRepository.save(existingCategory);

            return CategoryMappingHelper.map(updatedCategory);
        } catch (CategoryNotFoundException e) {
            log.error("Error updating category. Category with id [{}] not found.", categoryId);
            throw new CategoryNotFoundException(String.format("Category with id [%d] not found.", categoryId), e);
        } catch (DataIntegrityViolationException e) {
            log.error("Error updating category: Data integrity violation", e);
            throw new CategoryNotFoundException("Error updating category: Data integrity violation", e);
        } catch (Exception e) {
            log.error("Error updating category", e);
            throw new CategoryNotFoundException("Error updating category", e);
        }
    }

    @Override
    public void deleteById(Integer categoryId) {
        log.info("Void Service, delete category by id");
        try {
            categoryRepository.deleteById(categoryId);
        } catch (CategoryNotFoundException e) {
            log.error("Error delete category", e);
            throw new CategoryNotFoundException("Error updating category", e);
        }
    }
}
