package com.glodon.seckilladmin.controller;

import com.glodon.seckilladmin.domain.UserAdmin;
import com.glodon.seckilladmin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Random;

/**
 * @author lic-s
 */
@RequestMapping("/user_admin")
@Controller
public class AdminLoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "admin";
    }


    private String code;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param checkcode
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(String username, String password, String checkcode, HttpSession session, Map<String, Object> map) throws Exception {
        if (!checkcode.equals(code)) {
            map.put("msg", "验证码错误");
            return "admin";
        }
        UserAdmin userAdmin = loginService.selectByRootName(username);
        if (userAdmin == null) {
            map.put("msg", "用户不存在");
            return "admin";
        }
        String uName = userAdmin.getRootName();
        String uPassword = userAdmin.getRootPassword();
        if (uName == null || "".equals(uName) || "".equals(password) || uPassword == null || !username.equals(uName) || !password.equals(uPassword)) {
            map.put("msg", "用户名或密码错误");
            return "admin";
        }
        return "redirect:/admin/add";
    }

    /**
     * 把验证码生成图片
     *
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping("/createImage")
    public void createImage(HttpServletResponse response, HttpSession session) throws IOException {
        BufferedImage image = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random r = new Random();
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        g.fillRect(0, 0, 80, 20);
        //获取生成的验证码
         code = getNumber();
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        g.drawString(code, 5, 25);
        //设置消息头
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "jpeg", os);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getNumber() {
        String str = "1234567890abcdefghijklmnopqrstuvwxyz";
        String code = "";
        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * str.length());
            code += str.charAt(index);
        }
        return code;
    }

}
