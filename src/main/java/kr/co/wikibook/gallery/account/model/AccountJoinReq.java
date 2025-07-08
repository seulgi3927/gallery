package kr.co.wikibook.gallery.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 기본 생성자 만들어주는 에노테이션
@AllArgsConstructor
public class AccountJoinReq {
    private String name;
    private String loginId;
    private String loginPw;
}
