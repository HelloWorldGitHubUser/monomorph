package com.youlai.mall.controller;
import com.youlai.mall.model.ums.dto.MemberAddressDTO;
import com.youlai.mall.model.ums.dto.MemberAuthDTO;
import com.youlai.mall.model.ums.dto.MemberRegisterDto;
import com.youlai.mall.model.ums.vo.MemberVO;
import com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO;
import com.youlai.mall.result.Result;
import com.youlai.mall.security.util.SecurityUtils;
import com.youlai.mall.service.ums.UmsMemberService;
import java.util.List;
import java.util.Set;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@Tag(name = "App-会员管理")
@RestController
@RequestMapping("/app-api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final UmsMemberService memberService;

    @Operation(summary = "根据会员ID获取openid")
    @GetMapping("/{memberId}/openid")
    public Result<String> getMemberById(@Parameter(description = "会员ID")
    @PathVariable
    Long memberId) {
        String openid = memberService.getMemberOpenId(memberId);// 调用Service方法

        return Result.success(openid);
    }

    @Operation(summary = "新增会员")
    @PostMapping
    public Result<Long> addMember(@RequestBody
    MemberRegisterDto member) {
        Long memberId = memberService.addMember(member);
        return Result.success(memberId);
    }

    @Operation(summary = "获取登录会员信息")
    @GetMapping("/me")
    public Result<MemberVO> getCurrMemberInfo() {
        MemberVO memberVO = memberService.getCurrMemberInfo();
        return Result.success(memberVO);
    }

    @Operation(summary = "扣减会员余额")
    @PutMapping("/{memberId}/balances/_deduct")
    public Result deductBalance(@PathVariable
    Long memberId, @RequestParam
    Long amount) {
        memberService.deductBalance(memberId, amount);// 调用Service方法

        return Result.success();
    }

    @Operation(summary = "添加浏览历史")
    @PostMapping("/view/history")
    public <T> Result<T> addProductViewHistory(@RequestBody
    ProductHistoryVO product) {
        Long memberId = SecurityUtils.getMemberId();
        memberService.addProductViewHistory(product, memberId);
        return Result.success();
    }

    @Operation(summary = "获取浏览历史")
    @GetMapping("/view/history")
    public Result<Set<ProductHistoryVO>> getProductViewHistory() {
        Long memberId = SecurityUtils.getMemberId();
        Set<ProductHistoryVO> historyList = memberService.getProductViewHistory(memberId);
        return Result.success(historyList);
    }

    @Operation(summary = "根据 openid 获取会员认证信息")
    @GetMapping("/openid/{openid}")
    public Result<MemberAuthDTO> getMemberByOpenid(@Parameter(description = "微信唯一身份标识")
    @PathVariable
    String openid) {
        MemberAuthDTO memberAuthInfo = memberService.getMemberByOpenid(openid);
        return Result.success(memberAuthInfo);
    }

    @Operation(summary = "根据手机号获取会员认证信息", hidden = true)
    @GetMapping("/mobile/{mobile}")
    public Result<MemberAuthDTO> getMemberByMobile(@Parameter(description = "手机号码")
    @PathVariable
    String mobile) {
        MemberAuthDTO memberAuthInfo = memberService.getMemberByMobile(mobile);
        return Result.success(memberAuthInfo);
    }

    @Operation(summary = "获取会员地址列表")
    @GetMapping("/{memberId}/addresses")
    public Result<List<MemberAddressDTO>> listMemberAddress(@Parameter(description = "会员ID")
    @PathVariable
    Long memberId) {
        List<MemberAddressDTO> addresses = memberService.listMemberAddress(memberId);
        return Result.success(addresses);
    }
}