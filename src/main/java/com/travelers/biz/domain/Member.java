package com.travelers.biz.domain;

import com.travelers.biz.domain.base.BaseTime;
import com.travelers.biz.domain.image.MemberImage;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "birth")
    private String birth;

    @Column(name = "group_trip")
    private String groupTrip;

    @Column(name = "area")
    private String area;

    @Column(name = "theme")
    private String theme;

    @Column(name = "recommend")
    private String recommend;

    @Column(name = "recommend_code")
    private String recommendCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<MemberImage> images = new ArrayList<>();

    @Builder
    private Member(String username, String password, String email, String tel, String birth, String groupTrip, String area, String theme, Gender gender, String recommend, String recommendCode){
        this.username = username;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.groupTrip = groupTrip;
        this.area = area;
        this.theme = theme;
        this.gender = gender;
        this.authority = Authority.ROLE_USER;
        this.recommend = recommend;
        this.recommendCode = recommendCode == null ? createRecommendCode() : recommendCode;
    }

    public void changeAuthority(Authority authority){
        this.authority = authority;
    }

    // 추천인 코드 생성
    public String createRecommendCode(){
        Random rnd =new Random();
        StringBuffer buf =new StringBuffer();
        for(int i=0;i<8;i++) {
            if(rnd.nextBoolean()){
                buf.append((char)((int)(rnd.nextInt(26))+97));
            }
            else{
                buf.append((rnd.nextInt(10)));
            }
        }
        return buf.toString();
    }

    public void setId(Long id){
        this.id = id;
    }

    // 비밀번호 변경
    public void changePassword(String password, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void addImage(final MemberImage image) {
        images.add(image);
    }

}
