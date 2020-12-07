package web.homework4.homework4_1;

import UDao.MessageDao;
import modal.Message;
import modal.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class MainController {



    @RequestMapping("/main")
    public String getMain(UserInfo user, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        int cpage = 1;//当前页
        int count = 5;//每一页需要的微博条数


        //获取用户指定的页面,没有的话就默认是1了，有的话其实就是url里面路径的参数，获取就完事了
        String cp = request.getParameter("cp");
        if(cp!=null)
        {
            //url里面的参数都是字符串形式，要转换成整数形式
            cpage = Integer.parseInt(cp);
        }
        //通过数据库获得arr[0],总共多少数据，arr[1],按照每页count条来算，有几页
        int arr[] = MessageDao.totalPage(count);

        //默认评论的排序方式为comment数量优先
        String method = "commentNum";

        //和上面的一样，如果有的改就改，（只有要ajax优化以下，也不是很重要了）
        String methodtmp = request.getParameter("method");
        if (methodtmp!=null)
        {
            method =methodtmp;
        }

        //获取数据库
        ArrayList<Message> msgList = MessageDao.selectAll(method,cpage,count,arr[0]);
        //将得到的数据集穿过去
        model.addAttribute("msgList",msgList);


        //写在model里面的数据可以通过前端html用js读取
        //当前页码传递过去
        model.addAttribute("cPage",cpage);

        //将每页多少条消息传递过去
        model.addAttribute("count",count);
        //将一共多少条消息传过去
        model.addAttribute("tsum",arr[0]);
        //将一共多少页传递过去
        model.addAttribute("tpage",arr[1]);


        //为th标签取数据作准备
        model.addAttribute("user",user);


//        System.out.println("PPPP");

        return "main";


    }

    //获取发布者



    @PostMapping("/del")
    public void del(HttpServletRequest request, @ModelAttribute(value="row")Integer index)
    {
        Table table = (Table)request.getSession().getAttribute("table");
        table.getTable().remove(index-1);
    }



    @RequestMapping("/add")
    public String add(Model model, Contact cperson)
    {
//        cperson.setAddress("中南海");
        System.out.println(12333);

        model.addAttribute("contact",cperson);
        return "add";
    }

    @RequestMapping("/checkadd")
    public String checkAdd(Model model, HttpServletRequest request, Contact cperson)
    {
        HttpSession session = request.getSession();
        Table table =  (Table)session.getAttribute("table");
        boolean flag = true;
        for (int i = 0; i < table.getTable().size() && flag == true; i++) {
            if (table.getTable().elementAt(i).getName().equals(cperson.getName()))
            {
                flag = false;
            }
        }

        if(flag == true)//如果联系人中没有重名
        {
            table.getTable().addElement(cperson);

            return "main";
        }
        else
        {
            cperson.setName("");
            model.addAttribute("Contact",cperson);

            return "main";
        }

    }

    @RequestMapping("/checkChange")
    public String checkChange(Model model, HttpServletRequest request, Contact cperson)
    {
        HttpSession session = request.getSession();
        Table table = (Table)session.getAttribute("table");
        for (int i = 0; i < table.getTable().size(); i++) {
            if (table.getTable().elementAt(i).getName().equals(cperson.getName()))
            {
                table.getTable().setElementAt(cperson,i);
                break;
            }
        }
        return "main";
    }

    @RequestMapping("/alter")
    public String chg(@ModelAttribute(value = "index")Integer index,Model model,HttpServletRequest request)
    {
       HttpSession session =  request.getSession();
       Table table = (Table) session.getAttribute("table");
       Contact contact = table.getTable().elementAt(index-1);
       model.addAttribute("contact",contact);


       return "chgOut";
    }
}
