package web.homework4.homework4_1;

import lombok.Data;

@Data
public class Contact {
    private String name;
    private String telephone;
    private String email;
    private String address;
    private String qq;
    private String nickName;

    public Contact(String name,String telephone,String email,String address,String qq,String nickName)
    {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
        this.qq = qq;
        this.nickName = nickName;
    }
}
