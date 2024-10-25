package com.example.nestco.services;

import com.example.nestco.dao.repository.NoticeBoardRepository;
import com.example.nestco.models.entity.NoticeBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    public Page<NoticeBoard> findAll(Pageable newPageable, String searchKey) {
        if(searchKey == null) {
            return noticeBoardRepository.findAll(newPageable);
        }
        return noticeBoardRepository.findAllByQuery(newPageable, searchKey);
    }
}
