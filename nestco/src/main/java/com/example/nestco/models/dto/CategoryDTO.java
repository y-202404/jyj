package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private Long parentId; // 부모 카테고리 ID
    private int depth; // 카테고리 깊이 (1차, 2차, 3차 등)
    private String icon; // 아이콘 정보 (Font Awesome 등)
    private boolean isDisable; // Soft Delete 여부 (노출 여부)
    private int displayOrder; // 카테고리 순서
    private boolean isSelected;

    private List<CategoryDTO> children = new ArrayList<>();; // 자식 카테고리 목록

    // 기본 생성자와 생성자 정의
    public CategoryDTO(Long id, String name, Long parentId, int depth, String icon, boolean isDisable, int displayOrder, List<CategoryDTO> children) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
        this.icon = icon;
        this.isDisable = isDisable;
        this.displayOrder = displayOrder;
        this.children = children;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
