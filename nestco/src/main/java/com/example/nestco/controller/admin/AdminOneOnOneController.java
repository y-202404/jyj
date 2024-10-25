package com.example.nestco.controller.admin;

import com.example.nestco.dao.repository.OneOnOneRepository;
import com.example.nestco.models.entity.OneOnOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin/oneonone")
public class AdminOneOnOneController {

//
    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    // 조회
    @GetMapping
    public String listOneOnOnes(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(value = "page", defaultValue = "1") int page) {

        Pageable newPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        Page<OneOnOne> oneOnOnesList = oneOnOneRepository.findAll(newPageable);

        int totalPages = oneOnOnesList.getTotalPages();
        int currentPage = newPageable.getPageNumber() + 1;

        int start = Math.max(1, currentPage - 2);
        int end = Math.min(totalPages, currentPage + 2);

        List<Integer> pageNumbers = new ArrayList<>();
        for(int i = start; i <= end; i ++) {
            pageNumbers.add(i);
        }

        int firstPage = 1;
        int lastPage = totalPages;

        model.addAttribute("oneOnOnes", oneOnOnesList);
        model.addAttribute("prev", Math.max(1, currentPage-1));
        model.addAttribute("next", Math.min(totalPages, currentPage + 1));
        model.addAttribute("hasNext", oneOnOnesList.hasNext());
        model.addAttribute("hasPrev", oneOnOnesList.hasPrevious());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);


        return "admin/oneOnOneManagement";
    }


    // 삭제
    @PostMapping("/delete/{id}")
    public String deleteOneOnOne(@PathVariable("id") Long id) {
        OneOnOne oneOnOne = oneOnOneRepository.findById(id).orElse(null);

        oneOnOneRepository.delete(oneOnOne);

        return "redirect:/admin/oneonone";
    }

    // 등록 폼
    @GetMapping("/create")
    public  String createOneOnOneForm(Model model) {
        model.addAttribute("oneOnOne" , new OneOnOne());
        return "admin/createOneOnOne";
    }


}



