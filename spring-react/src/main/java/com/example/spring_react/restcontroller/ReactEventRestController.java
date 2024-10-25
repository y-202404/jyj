package com.example.spring_react.restcontroller;

import com.example.spring_react.dto.EventRequestDTO;
import com.example.spring_react.entity.UserEvent;
import com.example.spring_react.service.ReactEventRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactEventRestController {

    @Autowired
    private ReactEventRestService reactEventRestService;

    @PostMapping("/event/join")
    public ResponseEntity<String> eventJoin(@RequestBody EventRequestDTO eventRequestDTO) {

        String result = reactEventRestService.checkUser(eventRequestDTO);

        return ResponseEntity.ok(result);
    }

}
