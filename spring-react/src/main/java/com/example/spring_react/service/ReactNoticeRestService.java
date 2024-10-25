package com.example.spring_react.service;

import com.example.spring_react.dto.NoticeDTO;
import com.example.spring_react.entity.NoticeBoard;
import com.example.spring_react.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReactNoticeRestService {

    @Autowired
    private NoticeBoardRepository noticeRepository;

    public Page<NoticeBoard> createNoticePage(Pageable pageable) {
        Page<NoticeBoard> noticePage = noticeRepository.findAll(pageable);

        return noticePage;
    }

    public Page<NoticeBoard> createSearchNoticePage(Pageable pageable, String searchKey) {
        Page<NoticeBoard> noticePage = noticeRepository.findAllByQuery(pageable, searchKey);

        return noticePage;
    }

    public NoticeBoard deleteNotice(NoticeDTO noticeDTO) {
        NoticeBoard noticeBoard = noticeRepository.findById(noticeDTO.getId()).orElse(null);
        NoticeBoard target = noticeBoard;
        noticeRepository.delete(target);

        return noticeBoard;
    }

    public NoticeBoard addReadCount(NoticeDTO noticeDTO) {
        NoticeBoard noticeBoard = noticeRepository.findById(noticeDTO.getId()).orElse(null);

        NoticeDTO clone = new NoticeDTO();

        clone.setReadCount(noticeBoard.getReadCount() + 1);

        NoticeBoard target = clone.createNoticeBoard(clone);

        noticeBoard.update(target);

        noticeRepository.save(noticeBoard);

        return noticeBoard;
    }
}
