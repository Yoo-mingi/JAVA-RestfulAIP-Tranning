package kr.co.yoomin.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserInfo")
public class AdminUser {
    private Integer id;

    @Size(min = 2,message = "Name >= 2")
    private String name;

    @Past(message = "Date <= now")
    private Date joinDate;

    private String password;
    private String ssn;
}
