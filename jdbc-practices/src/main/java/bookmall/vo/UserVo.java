package bookmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class UserVo {
    private Long no;
    private String name;
    private String phoneNum;
    private String email;
    private String password;



    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }
/////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserVo(String name, String phoneNum, String email, String password) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.password = password;
    }
}
