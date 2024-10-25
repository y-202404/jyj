package com.example.nestco.services;

import com.example.nestco.dao.repository.BoardRepository;
import com.example.nestco.models.dto.BoardDTO;
import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.Board;
import com.example.nestco.models.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FreeBoardService {

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

    public Board newBoard(BoardDTO boardDTO, Member member) {
        boardDTO.setReadCount(0);
        boardDTO.setMember(member);

        Board board = boardDTO.createBoard(boardDTO);
        boardRepository.save(board);

        return board;
    }

    public Board showBoard(Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setReadCount(board.getReadCount() + 1);

        Board boardUpdateClone = boardDTO.createBoard(boardDTO);

        board.update(boardUpdateClone);

        boardRepository.save(board);

        return boardRepository.findById(id).orElse(null);
    }

    public Board editBoard(Long boardId) {
        return boardRepository.findById(boardId).orElse(null);
    }

    public Board updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id).orElse(null);

        Board boardUpdateClone = boardDTO.createBoard(boardDTO);

        board.update(boardUpdateClone);

        boardRepository.save(board);

        return board;
    }
}
