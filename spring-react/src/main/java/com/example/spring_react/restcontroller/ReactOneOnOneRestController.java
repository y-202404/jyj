package com.example.spring_react.restcontroller;
import com.example.spring_react.dto.OneOnOneRequestDTO;
import com.example.spring_react.entity.OneOnOne;
import com.example.spring_react.service.ReactOneOnOneRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactOneOnOneRestController {

    @Autowired
    private ReactOneOnOneRestService reactOneOnOneRestService;

    @PostMapping("/oneAdd")
    public ResponseEntity<String> oneAdd(@RequestBody OneOnOneRequestDTO oneOnOneRequestDTO) {

        System.out.println("id" + oneOnOneRequestDTO.getMemberDTO().getEmail());

        OneOnOne oneOnOne = reactOneOnOneRestService.addOne(oneOnOneRequestDTO);

        if(oneOnOne == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("문의 실패");
        }

        return ResponseEntity.ok("문의 성공");
    }
}
