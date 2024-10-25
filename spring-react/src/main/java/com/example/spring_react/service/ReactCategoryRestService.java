package com.example.spring_react.service;

import com.example.spring_react.entity.Category;
import com.example.spring_react.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReactCategoryRestService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Page<Category> creatCategoryPage(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage;
    }
}
