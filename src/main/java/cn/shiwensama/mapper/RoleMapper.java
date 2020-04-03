package cn.shiwensama.mapper;

import cn.shiwensama.eneity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色ID 删除sys_role_permission表中的数据
     * @param id
     */
    void deleteRolePermissionByRid(Serializable id);

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

}
