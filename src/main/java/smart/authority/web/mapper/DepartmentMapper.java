package smart.authority.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import smart.authority.web.model.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import smart.authority.web.model.resp.DepartmentResp;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    @Select("<script>" +
            "select dept.id as id, dept.name as name, dept.parent_id as parentId, tenant.name as tenantName, " +
            "dept.description as description, dept.create_time as createTime, dept.update_time as updateTime, " +
            "dept.create_by as createBy, dept.update_by as updateBy, dept.tenant_id as tenantId," +
            "parentDept.name as parentName " +
            "from department dept " +
            "LEFT JOIN tenant on dept.tenant_id = tenant.id " +
            "LEFT JOIN department parentDept on dept.parent_id = parentDept.id " +
            "where 1=1 " +
            "<if test='name != null and name !=\"\" '> " +
            "and dept.name like concat('%', #{name}, '%')" +
            "</if>" +
            "<if test='tenantName != null and tenantName !=\"\" '> " +
            "and tenant.name like concat('%', #{tenantName}, '%')" +
            "</if>" +
            "</script>")
    IPage<DepartmentResp> selectPageDepartments(@Param("page") IPage<DepartmentResp> page,
                                                @Param("name") String name,
                                                @Param("tenantName") String tenantName);
}