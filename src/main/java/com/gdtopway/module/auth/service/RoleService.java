package com.gdtopway.module.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdtopway.core.dao.jpa.BaseDao;
import com.gdtopway.core.service.BaseService;
import com.gdtopway.module.auth.dao.PrivilegeDao;
import com.gdtopway.module.auth.dao.RoleDao;
import com.gdtopway.module.auth.dao.RoleR2PrivilegeDao;
import com.gdtopway.module.auth.dao.UserR2RoleDao;
import com.gdtopway.module.auth.entity.Role;
import com.gdtopway.module.auth.entity.User;
import com.gdtopway.module.auth.entity.UserR2Role;
import com.google.common.collect.Lists;

@Service
@Transactional
public class RoleService extends BaseService<Role, Long> {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private UserR2RoleDao userR2RoleDao;

    @Autowired
    private RoleR2PrivilegeDao roleR2PrivilegeDao;

    @Override
    protected BaseDao<Role, Long> getEntityDao() {
        return roleDao;
    }

    @Transactional(readOnly = true)
    public List<Role> findAllCached() {
        return roleDao.findAllCached();
    }

    public void updateRelatedPrivilegeR2s(Role entity, Long[] privielgeIds) {
        super.updateRelatedR2s(entity, privielgeIds, "roleR2Privileges", "privilege");
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByRole(String roleCode) {
        Role role = roleDao.findByCode(roleCode);
        List<User> users = Lists.newArrayList();
        List<UserR2Role> roleR2Users = role.getRoleR2Users();
        for (UserR2Role userR2Role : roleR2Users) {
            users.add(userR2Role.getUser());
        }
        return users;
    }
}
