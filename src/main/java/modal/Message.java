package modal;

import lombok.Data;

import java.util.Date;

@Data
public class Message {

    //信息自身的id
    private int id;
    //发布人id
    private String fromId;
    //
    private String toId;

    //消息内容
    private String content;

    private Date createDate;

    //已被读
    private int hasRead;

    private int aggreeNum;
    private int commentNum;

    public Message(int id,
                   String content,
                   String fromId,
                   String toId,
                   int hasRead,
                   Date creatDate,
                   int commentNum,
                   int aggreeNum
                   ){
        this.id = id;
        this.content = content;
        this.fromId = fromId;
        this.toId = toId;
        this.createDate = creatDate;
        this.hasRead = hasRead;
        this.aggreeNum = aggreeNum;
        this.commentNum = commentNum;

    }


    public Message() {

    }
}
