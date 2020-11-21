package web.homework4.homework4_1;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String words(LoginInfo user, Model model)
    {
        model.addAttribute("user",user);
        return "login";
    }

    @RequestMapping("/checklogin")
    public String check(LoginInfo user, Model model, HttpServletRequest request)
    {
        System.out.println(user.getUserName());
        System.out.println(user.getPassWord());

        
        if ("123".equals(user.getPassWord()) && "admin".equals(user.getUserName()) ) {

            HttpSession sssn = request.getSession();
            sssn.setAttribute("login", "Yes");
            if(sssn.getAttribute("table") == null)
            {
                Table table = new Table();
                sssn.setAttribute("table",table);
            }

            return "redirect:/main";
        }
        else {

            user.setMessage("ERROR in PASSWORD");
            model.addAttribute("user", user);

            return "login";
        }
    }
}
