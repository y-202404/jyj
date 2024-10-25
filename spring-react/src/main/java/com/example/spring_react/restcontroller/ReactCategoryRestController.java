package com.example.spring_react.restcontroller;

import com.example.spring_react.entity.Category;
import com.example.spring_react.service.ReactCategoryRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactCategoryRestController {

    @Autowired
    private ReactCategoryRestService reactCategoryRestService;

    @GetMapping("/categoryData")
    public ResponseEntity<Page<Category>> categoryData(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {

        Page<Category> categoryPage = reactCategoryRestService.creatCategoryPage(pageable);

        if(categoryPage == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        for(Category category : categoryPage) {
            category.setChildren(null);
            category.setParent(null);
        }

        return ResponseEntity.ok(categoryPage);
    }
}
