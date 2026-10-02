package com.central.service;

import com.central.model.PageResult;
import com.central.entity.Client;

import java.util.Map;

/**
 * 应用管理服务接口
 * @author zlt
 */
public interface IClientService extends ISuperService<Client> {
    /**
     * 保存/更新应用
     */
    void saveClient(Client client) throws Exception;

    /**
     * 查询应用列表
     * @param params 查询参数
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

    /**
     * 删除应用
     */
    void delClient(long id);

    /**
     * 根据 clientId 加载应用
     */
    Client loadClientByClientId(String clientId);
}





