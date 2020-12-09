package web.homework4.homework4_1;

import UDao.AgreeDao;
import UDao.CommentsDao;
import UDao.MessageDao;
import modal.Agree;
import modal.Comments;
import modal.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.sql.SQLException;
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

            String id = (String) session.getAttribute("user");
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

    /*
    对信息的点赞函数，
    如果用户未登录，不给点赞，返回0
    如果用户已经点赞过，取消点赞，返回-1
    如果用户未点赞过，进行点赞，返回1
     */
    @GetMapping("/ajax/aggreet")
    public static int makeagree(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException {
        int action = 0;
        Integer message_id = Integer.parseInt(request.getParameter("id"));
        Object tmp = session.getAttribute("id");
        if (tmp == null)
        {
            System.out.println("用户未登录");
            return 0;
        }

        //获取点赞表里面的点赞事件
        ArrayList<Agree> agrees = AgreeDao.select_MId_PId(message_id,(Integer)tmp);
        //点过赞了，删除点赞记录
        System.out.println(agrees.size());
        if (agrees.size() > 0)
        {
            for (Agree agree : agrees) {
                AgreeDao.delete(agree.getId());
                action--;
            }

            MessageDao.update_agree(message_id,action);
        }
        //没点过赞，增加一条点赞记录
        else {
            Agree agree = new Agree(0,message_id,(Integer)tmp);
            AgreeDao.insert(agree);
            System.out.println(message_id);
            System.out.println((Integer)tmp);
            action = 1;
            MessageDao.update_agree(message_id,action);

        }
        return action;
    }

}
