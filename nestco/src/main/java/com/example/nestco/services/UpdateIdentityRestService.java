package com.example.nestco.services;



import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

@Service
public class UpdateIdentityRestService {
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<String> update(MemberDTO memberDTO, MultipartFile multipartFile){
        String userId = memberDTO.getUserId();
        Member member = memberRepository.findByUserId(userId);
        MultipartFile profileImage = multipartFile;

        String create_time = member.getCreateDate();
        String provider = member.getProvider();
        String password = member.getUserPassword();
        String role = member.getRole();
        String imagePath = member.getProfileImagePath();

        memberDTO.setCreateDate(create_time);
        memberDTO.setProvider(provider);
        memberDTO.setUserPassword(password);
        memberDTO.setRole(role);


        if (memberRepository.findByNickname(memberDTO.getNickname()) != null && !memberRepository.findByNickname(memberDTO.getNickname()).getUserId().equals(userId)) {
//            System.out.println("동일 닉네임");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일한 닉네임의 계정이 존재합니다. 닉네임을 확인해 주세요.");
        } else if (memberRepository.findByPhoneNumber(memberDTO.getPhoneNumber()) != null && !memberRepository.findByPhoneNumber(memberDTO.getPhoneNumber()).getUserId().equals(userId)) {
//            System.out.println("동일 폰번호");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일한 휴대폰번호로 가입된 계정이 존재합니다. 아이디 또는 비밀번호찾기를 이용하여 계정을 찾을 수 있씁니다.");
        } else if (memberRepository.findByEmail(memberDTO.getEmail()) != null && !memberRepository.findByEmail(memberDTO.getEmail()).getUserId().equals(userId)) {
//            System.out.println("동일 이메일");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일한 이메일로 가입된 계정이 존재합니다. 아이디 또는 비밀번호찾기를 이용하여 계정을 찾을 수 있습니다.");
        }

        // 프로필 이미지가 존재하는지 확인
        if (profileImage != null && !profileImage.isEmpty()) {
            // 파일 저장 로직
            try {
                String fileName = profileImage.getOriginalFilename();
                String fileExtension = "";

                if (fileName != null && fileName.lastIndexOf('.') > 0) {
                    // 파일 확장자 추출
                    fileExtension = fileName.substring(fileName.lastIndexOf('.'));
//                    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                }
                // 현재 시간으로 고유한 이름 생성
                String uniqueFileName = userId + "_" + System.currentTimeMillis() + fileExtension;
                String sanitizedFileName = uniqueFileName.replace(" ", "_");
                System.out.println(uniqueFileName);
                System.out.println(sanitizedFileName);

//                String uploadDir = "/Users/victersmac/Desktop/NestCo/finalproject_pr/nestco/src/main/resources/static/images/profileImage"; // 업로드할 디렉토리 경로
                String uploadDir = "src/main/resources/static/images/profileImage"; // 상대 경로
                Path path = Paths.get(uploadDir, sanitizedFileName);
                System.out.println(path);
                Files.copy(profileImage.getInputStream(), path);

                // 프로필 이미지 경로를 DTO에 설정
                memberDTO.setProfileImagePath("/static/images/profileImage/" + sanitizedFileName);

                // 성공적으로 저장됨
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
            }
        } else {
            memberDTO.setProfileImagePath(imagePath);
        }

        Member memberEntity = memberDTO.createMemberEntity(memberDTO);

        if(memberRepository.save(memberEntity) == null){
            ResponseEntity.status(HttpStatus.CONFLICT).body("변경된 정보를 저장중 문제가 발생 하였습니다.");
        }
        return ResponseEntity.ok(null);
    }
}
