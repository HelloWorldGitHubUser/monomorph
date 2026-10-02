package io.gulimall.controller.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.gulimall.vo.SocialUser;
import io.gulimall.constant.AuthServerConstant;
import io.gulimall.utils.HttpUtils;
import io.gulimall.vo.MemberResponseVo;
import io.gulimall.entity.member.MemberEntity;
import io.gulimall.service.member.MemberService;
import org.springframework.beans.BeanUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OauthController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/oauth2.0/weibo/success")
    public String authorize(String code, HttpSession session) throws Exception {
        //1. 使用code换取token，换取成功则继续2，否则重定向至登录页
        Map<String, String> query = new HashMap<>();
        query.put("client_id", "2144471074");
        query.put("client_secret", "ff63a0d8d591a85a29a19492817316ab");
        query.put("grant_type", "authorization_code");
        query.put("redirect_uri", "http://localhost:8080/oauth2.0/weibo/success");
        query.put("code", code);
        //发送post请求换取token
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<String, String>(), query, new HashMap<String, String>());
        Map<String, String> errors = new HashMap<>();
        if (response.getStatusLine().getStatusCode() == 200) {
            //2. 调用会员服务进行oauth登录（简化后直接调用 MemberService）
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, new TypeReference<SocialUser>() {
            });
            MemberEntity entity = memberService.login(socialUser);
            //2.1 登录成功，返回首页并携带用户信息
            if (entity != null) {
                MemberResponseVo memberResponseVo = new MemberResponseVo();
                BeanUtils.copyProperties(entity, memberResponseVo);
                System.out.println("----------------"+memberResponseVo);
                session.setAttribute(AuthServerConstant.LOGIN_USER, memberResponseVo);
                return "redirect:/";
            } else {
                //2.2 否则返回登录页
                errors.put("msg", "登录失败，请重试");
                session.setAttribute("errors", errors);
                return "redirect:/login.html";
            }
        }else {
            errors.put("msg", "获得第三方授权失败，请重试");
            session.setAttribute("errors", errors);
            return "redirect:/login.html";
        }



    }
}

