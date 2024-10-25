package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.OneOnOne;
import lombok.Data;

@Data // 생성자, Getter, Setter, toString, equals, hashCode 등을 포함하는 일종의 종합 패키지
public class OneOnOneForm {

    private Long id;
    private String type;
    private Member memberId;
    private String phoneNumber;
    private String content;

    public OneOnOne toEntity(OneOnOneForm oneOnOneForm) {
        return new OneOnOne(id, type, memberId, phoneNumber, content);
    }
}
