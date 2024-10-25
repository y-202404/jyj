package com.example.nestco.services;

import com.example.nestco.dao.repository.BoardRepository;
import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> createBoardList(Pageable pageable, SearchKeyDTO searchKeyDTO) {
        String searchKey = searchKeyDTO.getSearchKey();

        if(searchKey == null) {
            Page<Board> boardList = boardRepository.findAll(pageable);
            return boardList;
        } else {
            Page<Board> boardList = boardRepository.findAllByQuery(pageable, searchKey);
            return boardList;
        }
    }
}
