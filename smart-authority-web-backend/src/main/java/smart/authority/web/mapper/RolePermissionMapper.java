package smart.authority.web.mapper;

import smart.authority.web.model.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lynn
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 批量插入角色权限关联
     *
     * @param rolePermissions 角色权限关联列表
     */
    void batchInsert(@Param("list") List<RolePermission> rolePermissions);

    /**
     * 根据角色ID删除所有相关的角色权限关联
     *
     * @param roleId 角色ID
     */
    void deleteByRoleId(@Param("roleId") Integer roleId);
}