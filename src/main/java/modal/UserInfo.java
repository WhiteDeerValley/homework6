package modal;

import lombok.Data;


@Data
public class UserInfo {

    private String userName;
    private String passWord;
    private String passWordAgain;
    private String message;

    public UserInfo(String s, String s1, String s2) {
        this.userName = s;
        this.passWord = s1;
        this.passWordAgain = s2;
    }
}
