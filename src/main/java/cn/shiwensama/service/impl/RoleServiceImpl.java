package cn.shiwensama.service.impl;

import cn.shiwensama.eneity.Role;
import cn.shiwensama.mapper.RoleMapper;
import cn.shiwensama.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    /**
     * 查询当前用户拥有的角色ID集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> queryUserRoleIdsByUid(String id) {
        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }

    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }

    /**
     * 保存用户和角色的关系
     * @param uid
     * @param rid
     */
    @Override
    public void insertUserRole(String uid, Integer rid) {

        this.getBaseMapper().insertUserRole(uid,rid);
    }

    /**
     * 根据用户ID 删除role_user表中的数据
     * @param id
     */
    @Override
    public void deleteRoleUserByUid(String id) {
        this.getBaseMapper().deleteRoleUserByUid(id);
    }


}
