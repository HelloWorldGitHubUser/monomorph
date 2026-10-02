package io.gulimall.controller.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.gulimall.vo.auth.UserLoginVo;
import io.gulimall.vo.auth.UserRegisterVo;
import io.gulimall.constant.AuthServerConstant;
import io.gulimall.exception.BizCodeEnum;
import io.gulimall.utils.R;
import io.gulimall.vo.MemberResponseVo;
import io.gulimall.entity.member.MemberEntity;
import io.gulimall.exception.PhoneNumExistException;
import io.gulimall.exception.UserExistException;
import io.gulimall.service.member.MemberService;
import io.gulimall.vo.member.MemberLoginVo;
import io.gulimall.vo.member.MemberRegisterVo;
import io.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SmsComponent smsComponent;


    @RequestMapping("/login.html")
    public String loginPage(HttpSession session) {
        if (session.getAttribute(AuthServerConstant.LOGIN_USER) != null) {
            return "redirect:/";
        }else {
            return "auth-server/templates/login";
        }
    }

    @RequestMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes attributes, HttpSession session){
        MemberLoginVo memberLoginVo = new MemberLoginVo();
        memberLoginVo.setLoginAccount(vo.getLoginacct());
        memberLoginVo.setPassword(vo.getPassword());
        MemberEntity entity = memberService.login(memberLoginVo);
        if (entity != null) {
            MemberResponseVo memberResponseVo = new MemberResponseVo();
            BeanUtils.copyProperties(entity, memberResponseVo);
            session.setAttribute(AuthServerConstant.LOGIN_USER, memberResponseVo);
            return "redirect:/";
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMsg());
            attributes.addFlashAttribute("errors", errors);
            return "redirect:/login.html";
        }
    }

    /**
     * 发送短信验证码 - 直接调用 SmsComponent（简化后，不再通过 ThirdPartFeignService）
     */
    @GetMapping("/sms/sendCode")
    @ResponseBody
    public R sendCode(@RequestParam("phone") String phone) {
        // 接口防刷，在redis中缓存phone-code
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String prePhone = AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone;
        String v = ops.get(prePhone);
        if (!StringUtils.isEmpty(v)) {
            long pre = Long.parseLong(v.split("_")[1]);
            // 如果存储的时间小于60s，说明60s内发送过验证码
            if (System.currentTimeMillis() - pre < 60000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        // 如果存在的话，删除之前的验证码
        redisTemplate.delete(prePhone);
        // 获取到6位数字的验证码
        String code = String.valueOf((int) ((Math.random() + 1) * 100000));
        // 在redis中进行存储并设置过期时间
        ops.set(prePhone, code + "_" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);
        // 直接调用 SmsComponent 发送短信（简化后）
        smsComponent.sendCode(phone, code);
        return R.ok();
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterVo registerVo, BindingResult result, RedirectAttributes attributes) {
        //1.判断校验是否通过
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()){
            //1.1 如果校验不通过，则封装校验结果
            result.getFieldErrors().forEach(item->{
                errors.put(item.getField(), item.getDefaultMessage());
                //1.2 将错误信息封装到session中
                attributes.addFlashAttribute("errors", errors);
            });
            //1.2 重定向到注册页
            return "redirect:/reg.html";
        }else {
            //2.若JSR303校验通过
            //判断验证码是否正确
            String code = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());
            //2.1 如果对应手机的验证码不为空且与提交上的相等-》验证码正确
            if (!StringUtils.isEmpty(code) && registerVo.getCode().equals(code.split("_")[0])) {
                //2.1.1 使得验证后的验证码失效
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());

                //2.1.2 调用会员服务注册
                try {
                    MemberRegisterVo memberRegisterVo = new MemberRegisterVo();
                    BeanUtils.copyProperties(registerVo, memberRegisterVo);
                    memberService.register(memberRegisterVo);
                    //调用成功，重定向登录页
                    return "redirect:/login.html";
                } catch (UserExistException e) {
                    errors.put("msg", BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
                    attributes.addFlashAttribute("errors", errors);
                    return "redirect:/reg.html";
                } catch (PhoneNumExistException e) {
                    errors.put("msg", BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
                    attributes.addFlashAttribute("errors", errors);
                    return "redirect:/reg.html";
                }
            }else {
                //2.2 验证码错误
                errors.put("code", "验证码错误");
                attributes.addFlashAttribute("errors", errors);
                return "redirect:/reg.html";
            }
        }
    }
}

