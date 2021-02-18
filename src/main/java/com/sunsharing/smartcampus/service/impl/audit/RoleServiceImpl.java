package com.sunsharing.smartcampus.service.impl.audit;

import com.sunsharing.smartcampus.model.pojo.audit.Role;
import com.sunsharing.smartcampus.mapper.audit.RoleMapper;
import com.sunsharing.smartcampus.service.audit.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
