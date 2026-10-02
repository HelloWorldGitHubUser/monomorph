package io.gulimall.dao.ware;

import io.gulimall.entity.ware.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
