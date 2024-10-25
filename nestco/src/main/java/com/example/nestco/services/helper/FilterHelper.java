package com.example.nestco.services.helper;

import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.models.entity.QNestcoItems;
import com.example.nestco.services.CategoryService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class FilterHelper {

    // 정렬 순서 적용 메서드
    public static OrderSpecifier<?> applySortOrder(QNestcoItems nestcoItems, String sortOrder) {
        PathBuilder<NestcoItems> entityPath = new PathBuilder<>(NestcoItems.class, "nestcoItems");

        if ("latest".equals(sortOrder)) {
            return entityPath.getDateTime("createDate", LocalDateTime.class).desc();
        } else if ("oldest".equals(sortOrder)) {
            return entityPath.getDateTime("createDate", LocalDateTime.class).asc();
        }
        return entityPath.getDateTime("createDate", LocalDateTime.class).desc();
    }

    // 거래 상태 필터링
    public static void addStatusFilter(BooleanBuilder builder, String status, QNestcoItems nestcoItems) {
        if (status != null && !status.equals("all")) {
            builder.and("available".equals(status) ? nestcoItems.status.isFalse() : nestcoItems.status.isTrue());
        }
    }

    // 지역 필터링
    public static void addLocationFilter(BooleanBuilder builder, String location, QNestcoItems nestcoItems) {
        if (location != null && !location.equals("all")) {
            builder.and(nestcoItems.uploader.address.contains(location));
        }
    }

    // 카테고리 필터링
    public static void addCategoryFilter(BooleanBuilder builder, Long categoryId, QNestcoItems nestcoItems, CategoryService categoryService) {
        if (categoryId != null) {
            List<Long> categoryIds = categoryService.getAllSubCategoryIds(categoryId);
            builder.and(nestcoItems.category.id.in(categoryIds));
        }
    }

    // 검색어 필터링
    public static void addSearchFilter(BooleanBuilder builder, String searchQuery, QNestcoItems nestcoItems) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            builder.and(nestcoItems.title.containsIgnoreCase(searchQuery));
        }
    }
}
