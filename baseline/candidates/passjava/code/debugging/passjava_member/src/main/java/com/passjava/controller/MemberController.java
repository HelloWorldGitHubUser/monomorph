package com.passjava.controller;
import com.passjava.entity.MemberEntity;
import com.passjava.monomorph.id.generated.client.StudyTimeService;
import com.passjava.service.MemberService;
import com.passjava.utils.PageUtils;
import com.passjava.utils.R;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * 会员控制器
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private StudyTimeService studyTimeService;

    /**
     * 获取用户信息
     */
    @RequestMapping("/userinfo")
    public R info(@RequestParam("userId")
    String userId) {
        MemberEntity member = memberService.getMemberByUserId(userId);
        return R.ok().put("member", member);
    }

    /**
     * 获取会员学习时长
     */
    @RequestMapping("/studytime/list/test/{id}")
    public R getMemberStudyTimeListTest(@PathVariable("id")
    Long id) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(id);
        memberEntity.setNickname("悟空聊架构");
        R memberStudyTimeList = studyTimeService.getMemberStudyTimeListTest(id);
        return R.ok().put("member", memberEntity).put("studytime", memberStudyTimeList.get("studytime"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam
    Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id")
    Long id) {
        MemberEntity member = memberService.getById(id);
        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody
    MemberEntity member) {
        memberService.save(member);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody
    MemberEntity member) {
        memberService.updateById(member);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody
    Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 创建会员
     */
    @RequestMapping("/createMember")
    public R createMember(@RequestBody
    MemberEntity member) throws Exception {
        memberService.sendCoupon(1);
        return R.ok();
    }
}