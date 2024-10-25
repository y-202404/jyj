package com.example.nestco.controller.api;



import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.services.UpdateIdentityRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UpdateIdentityRestController {
    @Autowired
    private UpdateIdentityRestService updateIdentityRestService;

    @PostMapping("/updateIdentity")
    public ResponseEntity<String> updateIdentity(@ModelAttribute MemberDTO memberDTO, @RequestParam(value = "profileImage", required = false) MultipartFile profileImage){
        // 업데이트 결과를 받아옵니다.
        ResponseEntity<String> response = updateIdentityRestService.update(memberDTO, profileImage);

        // 응답이 null일 경우에는 성공을 의미하므로, 적절한 메시지와 함께 OK 상태 반환
        if (response.getBody() != null) {
            return response;
        }
        return ResponseEntity.ok(null);
    }
}
