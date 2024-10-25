package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.BoardDTO;
import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.Board;
import com.example.nestco.models.entity.Member;
import com.example.nestco.services.BaseService;
import com.example.nestco.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FreeBoardController {

    @Autowired
    private FreeBoardService freeBoardService;

    @Autowired
    private BaseService baseService;

    @GetMapping("/nestco/board")
    public String boards(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "searchKey", required = false) String searchKey) {

        baseService.headerAttributes(model);

        SearchKeyDTO searchKeyDTO = new SearchKeyDTO();
        searchKeyDTO.setSearchKey(searchKey);

        Pageable newPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        Page<Board> boardList = freeBoardService.createBoardList(newPageable, searchKeyDTO);

        int totalPages = boardList.getTotalPages();
        int currentPage = newPageable.getPageNumber() + 1;

        int start = Math.max(1, currentPage - 2);
        int end = Math.min(totalPages, currentPage + 2);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            pageNumbers.add(i);
        }

        int firstPage = 1;
        int lastPage = totalPages;

        model.addAttribute("searchKey", searchKey);
        model.addAttribute("boardList", boardList);
        model.addAttribute("prev", Math.max(1, currentPage - 1));
        model.addAttribute("next", Math.min(totalPages, currentPage + 1));
        model.addAttribute("hasNext", boardList.hasNext());
        model.addAttribute("hasPrev", boardList.hasPrevious());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);


        return "freeboard/boardIndex";
    }

    @GetMapping("/nestco/newBoard")
    public String newBoardView(Model model) {
        baseService.headerAttributes(model);

        return "freeboard/boardForm";
    }

    @PostMapping("/nestco/newBoard/create")
    public String newBoard(@AuthenticationPrincipal LocalUserDetails localUserDetails, BoardDTO boardDTO) {
        Member member = localUserDetails.getMemberEntity();
        Board board = freeBoardService.newBoard(boardDTO, member);
        return "redirect:/nestco/board";
    }

    @GetMapping("/nestco/board/{id}")
    public String nestcoId(@PathVariable("id") Long id, Model model) {
        baseService.headerAttributes(model);

        Board board = freeBoardService.showBoard(id);

        model.addAttribute("board", board);

        return "freeboard/boardShow";
    }

    @GetMapping("/nestco/board/edit")
    public String nestcoEdit(@RequestParam("boardId") Long boardId, Model model) {
        Board board = freeBoardService.editBoard(boardId);
        model.addAttribute("board", board);
        baseService.headerAttributes(model);

        return "freeboard/boardEdit";
    }

    @PostMapping("/nestco/edit/{id}")
    public String edit(@PathVariable("id") Long id,  BoardDTO boardDTO) {System.out.println("안녕");

        Board board = freeBoardService.updateBoard(id, boardDTO);

        return "redirect:/nestco/board/" + id;
    }

}
