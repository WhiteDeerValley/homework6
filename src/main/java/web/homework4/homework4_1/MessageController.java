package web.homework4.homework4_1;

import UDao.MessageDao;
import modal.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class MessageController {

    @PostMapping("/ajax/main/publish")
    public static String getUserMessage( HttpServletRequest request, HttpSession session)
    {
        String result ="passenger";

        String userName = "passenger";


        String content = (String) request.getParameter("contentText");
//        System.out.println(content);


        try {

            String id = (String) session.getAttribute("id");
            if (id != null && id != "passenger")
            {
                userName = id;

            }
            else
                userName = "passenger";


            Message msg = new Message();

            Date date = new Date();

            msg.setCreateDate(date);
            msg.setContent(content);
            msg.setHasRead(0);
            msg.setFromId(userName);
            msg.setToId(null);
            msg.setAggreeNum(0);
            msg.setCommentNum(0);

            //插入数据库
            int count = MessageDao.insert(msg);
//            System.out.println(result);
            //
            if (count > 0)
            {
                result = "success";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

//    @GetMapping("/ajax/getcomments")
//    public


}
