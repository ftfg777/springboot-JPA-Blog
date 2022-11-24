package com.jcw.blog.test;

import lombok.*;

@Data
@NoArgsConstructor
public class Member {
    // 데이터 베이스에서 가져오는 값을 넣어주기 때문에 데이터가 변경되지 않게 final로 잡아줌(불변성 유지)
    private int id;
    private String username;
    private String password;
    private String emailAddress;

    @Builder
    public Member(int id, String username, String password, String emailAddress) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
