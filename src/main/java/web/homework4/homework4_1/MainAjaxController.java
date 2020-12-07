package web.homework4.homework4_1;

import UDao.MessageDao;
import modal.Comments;
import modal.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
public class MainAjaxController {

    @RequestMapping("/ajax/main")
    public static ArrayList<Message> getMainTable(Model model, HttpServletRequest request, HttpServletResponse response)
    {
        //默认当前页面为1
        int cpage = 1;//当前页
        int count = 5;//每一页需要的微博条数
        //这里要重新获取，有好处也有坏处，就这样吧
        //通过数据库获得arr[0],总共多少数据，arr[1],按照每页count条来算，有几页
        int arr[] = MessageDao.totalPage(count);


        String method= request.getParameter("method");

        ArrayList<Message> msgList = MessageDao.selectAll(method,cpage,count,arr[0]);

//        //写在model里面的数据可以通过前端html用js读取
//        model.addAttribute("cPage",cpage);
//
//        model.addAttribute("count",count);
//        model.addAttribute("msgList",msgList);
//
//
//        model.addAttribute("tsum",arr[0]);
////        session.setAttribute("tsum",arr[0]);
//
//        model.addAttribute("tpage",arr[1]);




//        System.out.println("PPPP");

        return msgList;

    }

    @GetMapping("/ajax/getcomments")
    public static void getComment(Model model, HttpServletResponse response, HttpServletRequest request)
    {
//        ArrayList<Comments> result = new ArrayList<>(Comments);
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("user"));
    }
}
