package com.example.nestco.controller.admin;

import com.example.nestco.dao.repository.BoardRepository;
import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.Board;
import com.example.nestco.services.BoardService;
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
@RequestMapping("/admin/boards")
public class AdminBoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    // 조회
    @GetMapping
    public String boards(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "searchKey", required = false) String searchKey) {
        SearchKeyDTO searchKeyDTO = new SearchKeyDTO();
        searchKeyDTO.setSearchKey(searchKey);

        Pageable newPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        Page<Board> boardList = boardService.createBoardList(newPageable, searchKeyDTO);

        int totalPages = boardList.getTotalPages();
        int currentPage = newPageable.getPageNumber() + 1;

        int start = Math.max(1, currentPage - 2);
        int end = Math.min(totalPages, currentPage + 2);

        List<Integer> pageNumbers = new ArrayList<>();
        for(int i = start; i <= end; i ++) {
            pageNumbers.add(i);
        }

        int firstPage = 1;
        int lastPage = totalPages;


        model.addAttribute("searchKey", searchKey);
        model.addAttribute("posts", boardList);
        model.addAttribute("prev", Math.max(1, currentPage-1));
        model.addAttribute("next", Math.min(totalPages, currentPage + 1));
        model.addAttribute("hasNext", boardList.hasNext());
        model.addAttribute("hasPrev", boardList.hasPrevious());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);


        return "admin/postsManagement";
    }

    // 삭제
    @PostMapping("/delete/{id}")
    public String boardsDelete(@PathVariable("id") Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        boardRepository.delete(board);

        return "redirect:/admin/boards";
    }
}
