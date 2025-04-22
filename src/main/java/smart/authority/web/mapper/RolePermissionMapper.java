package smart.authority.web.mapper;

import smart.authority.web.model.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 批量插入角色权限关联
     *
     * @param rolePermissions 角色权限关联列表
     * @return 插入成功的数量
     */
    int batchInsert(@Param("list") List<RolePermission> rolePermissions);

    /**
     * 根据角色ID删除所有相关的角色权限关联
     *
     * @param roleId 角色ID
     * @return 删除的数量
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);
}