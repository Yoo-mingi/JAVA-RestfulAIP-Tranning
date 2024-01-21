package kr.co.yoomin.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password","ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
@Table(name = "users")
public class User {
    @Schema(title = "사용자ID", description = "사용자의 ID는 자동생성")
    @Id
    @GeneratedValue
    private Integer id;

    @Schema(title = "사용자 이름", description = "필수입력")
    @Size(min = 2,message = "Name >= 2")
    private String name;

    @Schema(title = "사용자 이름", description = "사용자 등록일은 자동생성")
    @Past(message = "Date <= now")
    private Date joinDate;

    @Schema(title = "사용자 비밀번호", description = "입력하시오 장군.")
    //@JsonIgnore
    private String password;

    @Schema(title = "유사 주민번호", description = "자릿수 안맞췄음 ㅋ_ㅋ.")
    //@JsonIgnore
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(Integer id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
