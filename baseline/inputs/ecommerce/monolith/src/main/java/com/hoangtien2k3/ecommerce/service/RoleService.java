package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.model.user.Role;
import com.hoangtien2k3.ecommerce.model.user.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleName name);
    boolean assignRole(Long id, String roleName);
    boolean revokeRole(Long id, String roleName);
    List<String> getUserRoles(Long id);
}
