package smart.authority.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.Department;
import smart.authority.web.model.req.department.DepartmentCreateReq;
import smart.authority.web.model.req.department.DepartmentQueryReq;
import smart.authority.web.model.req.department.DepartmentUpdateReq;
import smart.authority.web.model.resp.DepartmentResp;

import java.util.List;

/**
 * @author lynn
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 创建部门
     *
     * @param req 创建部门请求
     */
    void createDepartment(DepartmentCreateReq req);

    /**
     * 更新部门
     *
     * @param req 更新部门请求
     */
    void updateDepartment(DepartmentUpdateReq req);

    /**
     * 删除部门
     *
     * @param id 部门ID
     */
    void deleteDepartment(Integer id);

    /**
     * 分页查询部门
     *
     * @param req 查询请求
     * @return 分页结果
     */
    Page<DepartmentResp> pageDepartments(DepartmentQueryReq req);

    /**
     * 获取部门详情
     *
     * @param id 部门ID
     * @return 部门响应
     */
    DepartmentResp getDepartmentById(Integer id);

} 