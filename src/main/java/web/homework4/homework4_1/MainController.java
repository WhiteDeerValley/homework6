package web.homework4.homework4_1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
public class MainController {

    @RequestMapping("/main")
    public String getMain(HttpServletRequest request)
    {
        HttpSession sess = request.getSession();
        if(sess.getAttribute("login") != null)
        {
            return "main";
        }
        else
        {
            return "redirect:/login";
        }

    }

    @PostMapping("/del")
    public void del(HttpServletRequest request, @ModelAttribute(value="row")Integer index)
    {
        Table table = (Table)request.getSession().getAttribute("table");
        table.getTable().remove(index-1);
    }

    @RequestMapping("/add")
    public String add(Model model, Contact cperson)
    {
        //cperson.setAddress("中南海");
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

            return "add";
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
