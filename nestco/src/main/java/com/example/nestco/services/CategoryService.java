package com.example.nestco.services;

import com.example.nestco.dao.mapper.CategoryMapper;
import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.entity.Category;
import com.example.nestco.dao.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 카테고리 비즈니스 로직 처리 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 ID로 조회
    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
    }

    // 카테고리 ID로 조회하고 CategoryDTO로 변환
    public CategoryDTO getCategoryById(Long id) {
        Category category = getCategoryEntityById(id);  // 기존 메서드 재사용
        return CategoryMapper.toDTO(category);  // 엔티티를 DTO로 변환하여 반환
    }

    // 카테고리 추가
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category parentCategory = null;

        // parentId가 있으면 부모 카테고리 조회
        if (categoryDTO.getParentId() != null) {
            parentCategory = getCategoryEntityById(categoryDTO.getParentId());
            log.info("Parent Category: {}", parentCategory.getName());
        } else {
            log.info("No Parent (Top-level Category)");
        }

        // 부모 카테고리의 depth에 맞춰 자식 카테고리의 depth 설정
        Category category = CategoryMapper.fromDTO(categoryDTO, parentCategory);
        setCategoryDepth(category, parentCategory);

        category = categoryRepository.save(category);
        return CategoryMapper.toDTO(category);
    }

    // 카테고리 수정
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = getCategoryEntityById(id);
        Category parentCategory = categoryDTO.getParentId() != null ? getCategoryEntityById(categoryDTO.getParentId()) : null;

        category.setName(categoryDTO.getName());
        category.setParent(parentCategory);
        category.setDisplayOrder(categoryDTO.getDisplayOrder());
        category.setIcon(categoryDTO.getIcon());
        category.setDisable(categoryDTO.isDisable());

        setCategoryDepth(category, parentCategory);
        category = categoryRepository.save(category);
        return CategoryMapper.toDTO(category);
    }

    // 카테고리 삭제
    public void softDeleteCategory(Long id) {
        Category category = getCategoryEntityById(id);
        category.setDisable(true);  // 소프트 삭제
        categoryRepository.save(category);
    }

    // 실제 삭제 처리 (DB에서 완전히 삭제)
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // 계층 구조로 정렬된 모든 카테고리 조회
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByOrderByDepthAscDisplayOrderAsc();
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
    // 대분류만 가져오기 (1차 카테고리)
    public List<CategoryDTO> getTopCategories() {
        List<Category> topCategories = categoryRepository.findAll().stream()
                .filter(category -> category.getDepth() == 0)  // 대분류만 필터링
                .collect(Collectors.toList());
        return topCategories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 특정 부모 ID에 따른 하위 카테고리 조회( 바로 아래 단계만)
    public List<CategoryDTO> getSubCategories(Long parentId) {
        List<Category> categories = categoryRepository.findByParentId(parentId);
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 중분류만 가져오기 (2차 카테고리)
    public List<CategoryDTO> getMiddleCategories(Long parentId) {
        return getSubCategories(parentId);  // 대분류 선택 후 중분류 가져오기
    }

    // 소분류만 가져오기 (3차 카테고리)
    public List<CategoryDTO> getChildCategories(Long parentId) {
        return getSubCategories(parentId);  // 중분류 선택 후 소분류 가져오기
    }

    // 카테고리 트리 구조 반환 (계층 구조)
    @Transactional
    public List<CategoryDTO> getCategoryTree(Long selectedParentId) {
        List<Category> categories = categoryRepository.findAll();
        log.info("카테고리 데이터 개수: {}", categories.size());  // 데이터 개수 로그로 확인

        return categories.stream()
                .filter(category -> category.getDepth() == 0)  // 대분류만 필터링
                .map(topCategory -> {
                    // 중분류 찾기
                    List<CategoryDTO> middleCategories = categories.stream()
                            .filter(category -> category.getParent() != null && category.getParent().getId().equals(topCategory.getId()) && category.getDepth() == 1)
                            .map(middleCategory -> {
                                // 소분류 찾기
                                List<CategoryDTO> subCategories = categories.stream()
                                        .filter(category -> category.getParent() != null && category.getParent().getId().equals(middleCategory.getId()) && category.getDepth() == 2)
                                        .map(subCategory -> {
                                            CategoryDTO subCategoryDTO = CategoryMapper.toDTO(subCategory);
                                            // 소분류 선택 처리
                                            if (selectedParentId != null && selectedParentId.equals(subCategory.getId())) {
                                                subCategoryDTO.setIsSelected(true);
                                            }
                                            return subCategoryDTO;
                                        })
                                        .collect(Collectors.toList());

                                // 중분류에 소분류 설정
                                CategoryDTO middleCategoryDTO = CategoryMapper.toDTO(middleCategory);
                                middleCategoryDTO.setChildren(subCategories);
                                // 중분류 선택 처리
                                if (selectedParentId != null && selectedParentId.equals(middleCategory.getId())) {
                                    middleCategoryDTO.setIsSelected(true);
                                }
                                return middleCategoryDTO;
                            })
                            .collect(Collectors.toList());

                    // 대분류에 중분류 설정
                    CategoryDTO topCategoryDTO = CategoryMapper.toDTO(topCategory);
                    topCategoryDTO.setChildren(middleCategories);
                    // 대분류 선택 처리
                    if (selectedParentId != null && selectedParentId.equals(topCategory.getId())) {
                        topCategoryDTO.setIsSelected(true);
                    }
                    return topCategoryDTO;
                })
                .collect(Collectors.toList());
    }

    // 카테고리 깊이 설정(계층 설정)
    private void setCategoryDepth(Category category, Category parentCategory) {
        if (parentCategory != null) {
            category.setDepth(parentCategory.getDepth() + 1);
        } else {
            category.setDepth(0);  // 대분류
        }

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            for (Category child : category.getChildren()) {
                setCategoryDepth(child, category);  // 자식의 depth를 업데이트
            }
        }
    }

    // 전체 하위 카테고리 모두 포함하여 조회(카테고리 검색용)
    public List<Long> getAllSubCategoryIds(Long categoryId) {
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(categoryId); // 상위 카테고리 포함

        // 하위 카테고리들 ID 추가
        List<Category> subCategories = categoryRepository.findByParentId(categoryId);
        for (Category subCategory : subCategories) {
            categoryIds.add(subCategory.getId());

                // 중분류의 하위 카테고리(소분류)까지 포함
                List<Category> childCategories = categoryRepository.findByParentId(subCategory.getId());
                for (Category childCategory : childCategories) {
                    categoryIds.add(childCategory.getId());
                }
            }
        return categoryIds;
    }
}

