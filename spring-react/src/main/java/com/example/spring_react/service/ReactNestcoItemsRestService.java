package com.example.spring_react.service;

import com.example.spring_react.dto.SearchNestcoItemsDTO;
import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.NestcoItems;
import com.example.spring_react.repository.NestcoItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReactNestcoItemsRestService {

    @Autowired
    private NestcoItemsRepository nestcoItemsRepository;

    public Page<NestcoItems> nestcoItemsPage(Pageable pageable) {
        Page<NestcoItems> nestcoItemsPage = nestcoItemsRepository.findAll(pageable);

        return nestcoItemsPage;
    }

    public Page<NestcoItems> createSearchNestcoItemsPage(Pageable pageable, SearchNestcoItemsDTO searchNestcoItemsDTO) {
        String sortOrder = searchNestcoItemsDTO.getSortOrder();
        String status = searchNestcoItemsDTO.getStatus();
        String location = searchNestcoItemsDTO.getLocation();
        Boolean logic = false;

        if(sortOrder.equals("oldest")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDate").ascending());
        }

        if(status.equals("all")) {
            Page<NestcoItems> nestcoItemsPage = nestcoItemsRepository.findAll(pageable);
            return nestcoItemsPage;
        }

        if(status.equals("available")) {
            Page<NestcoItems> nestcoItemsPage = nestcoItemsRepository.findAllByQuery(pageable, logic);
            return nestcoItemsPage;
        } else {
            logic = true;
        }
        Page<NestcoItems> nestcoItemsPage = nestcoItemsRepository.findAllByQuery(pageable, logic);

        return nestcoItemsPage;
    }
}
