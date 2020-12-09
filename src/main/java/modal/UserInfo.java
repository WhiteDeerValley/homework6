package modal;

import lombok.Data;


@Data
public class UserInfo {

    private Integer id;
    private String userName;
    private String passWord;
    private String passWordAgain;
    private String message;

    public UserInfo(Integer id, String s, String s1, String s2 ) {
        this.id = id;
        this.userName = s;
        this.passWord = s1;
        this.passWordAgain = s2;
        this.message = null;
    }
}
