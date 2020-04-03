package cn.shiwensama.service;

import cn.shiwensama.eneity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询当前用户拥有的角色ID集合
     * @param id
     * @return
     */
    List<Integer> queryUserRoleIdsByUid(String id);

    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     * @param roleId
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    /**
     * 保存用户和角色的关系
     * @param uid
     * @param rid
     */
    void insertUserRole(String uid, Integer rid);

    /**
     * 根据用户ID 删除role_user表中的数据
     * @param id
     */
    void deleteRoleUserByUid(String id);

}
