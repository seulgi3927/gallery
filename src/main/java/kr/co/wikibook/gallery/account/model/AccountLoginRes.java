package kr.co.wikibook.gallery.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class AccountLoginRes {
    private int id;
    @JsonIgnore //JSON 으로 만들어질 때 아래것은 빠진다.
    private String loginPw;

}
