package com.passjava.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.passjava.entity.TypeEntity;
import com.passjava.service.ITypeService;
import com.passjava.utils.PageUtils;
import com.passjava.utils.R;

/**
 * 题目类型控制器
 */
@RestController
@RequestMapping("question/type")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = typeService.queryPage(params);
        return R.ok().put("page", page);
    }
    
    /**
     * 获取所有类型列表（带缓存）
     */
    @GetMapping("/all")
    public R getAll() {
        List<TypeEntity> list = typeService.getTypeEntityList();
        return R.ok().put("data", list);
    }
    
    /**
     * 获取所有类型列表（带缓存和本地锁保护）
     * 
     * 单体应用：转换为本地锁（ReentrantLock）
     */
    @GetMapping("/all-with-lock")
    public R getAllWithLock() {
        List<TypeEntity> list = typeService.getTypeEntityListWithLock();
        return R.ok().put("data", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TypeEntity type = typeService.getById(id);
        return R.ok().put("type", type);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TypeEntity type) {
        typeService.save(type);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TypeEntity type) {
        typeService.updateById(type);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        typeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}

