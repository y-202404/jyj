package com.example.spring_react.restcontroller;

import com.example.spring_react.dto.SearchNestcoItemsDTO;
import com.example.spring_react.entity.Category;
import com.example.spring_react.entity.ItemThumbnail;
import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.NestcoItems;
import com.example.spring_react.service.ReactNestcoItemsRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReactNestcoItemsRestController {

    @Autowired
    private ReactNestcoItemsRestService reactNestcoItemsRestService;

    @GetMapping("/nestCoData")
    public ResponseEntity<Page<NestcoItems>> nestcoItemsPage(@PageableDefault(size = 5, sort = "createDate", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<NestcoItems> nestcoItemsPage = reactNestcoItemsRestService.nestcoItemsPage(pageable);

        if(nestcoItemsPage == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        for(NestcoItems existOne1 : nestcoItemsPage.getContent()) {

            List<Category> categoryList = new ArrayList<>();
            List<ItemThumbnail> itemThumbnailList = new ArrayList<>();  // 경로를 모름


            categoryList.add(existOne1.getCategory());


            Category category = categoryList.get(0);

            category.setParent(null);   // 카테고리 양방향 참조 오류로 인해 세팅
            category.setChildren(null); // 카테고리 양방향 참조 오류로 인해 세팅


            existOne1.setCategory(category);
            existOne1.setThumbnails(null);  // 경로를 모름
        }


//        List<ItemThumbnail>
//        private Member uploader;  // 업로더정보
//        private Category category;    // 아이템과 카테고리 다대일

        return ResponseEntity.ok(nestcoItemsPage);
    }

    @PostMapping("/filterSearch")
    public ResponseEntity<Page<NestcoItems>> filterSearch(@PageableDefault(size = 5, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable,
                                                          @RequestBody SearchNestcoItemsDTO searchNestcoItemsDTO,
                                                          @RequestParam(value="page", required = false, defaultValue = "0") int page) {
        Page<NestcoItems> nestcoItemsPage = reactNestcoItemsRestService.createSearchNestcoItemsPage(pageable, searchNestcoItemsDTO);

        if(nestcoItemsPage == null) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        for(NestcoItems item : nestcoItemsPage.getContent()) {
            List<Category> categoryList = new ArrayList<>();
            List<ItemThumbnail> itemThumbnailList = new ArrayList<>();  // 경로를 모름


            categoryList.add(item.getCategory());


            Category category = categoryList.get(0);

            category.setParent(null);   // 카테고리 양방향 참조 오류로 인해 세팅
            category.setChildren(null); // 카테고리 양방향 참조 오류로 인해 세팅


            item.setCategory(category);
            item.setThumbnails(null);  // 경로를 모름
        }

        return ResponseEntity.ok(nestcoItemsPage);
    }
}
