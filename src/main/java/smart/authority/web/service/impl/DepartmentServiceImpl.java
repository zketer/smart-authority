package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import smart.authority.common.exception.BusinessException;
import smart.authority.common.exception.ErrorCode;
import smart.authority.web.model.entity.Department;
import smart.authority.web.mapper.DepartmentMapper;
import smart.authority.web.model.req.department.DepartmentCreateReq;
import smart.authority.web.model.req.department.DepartmentQueryReq;
import smart.authority.web.model.req.department.DepartmentUpdateReq;
import smart.authority.web.model.resp.DepartmentResp;
import smart.authority.web.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author lynn
 */
@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    private void checkDepartmentName(String name, Integer tenantId) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getName, name)
                .eq(Department::getTenantId, tenantId);
        if (count(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
        }
    }

    private DepartmentResp setParentDept(Department department) {
        DepartmentResp resp = new DepartmentResp();
        BeanUtils.copyProperties(department, resp);

        // 获取父部门名称
        if (department.getParentId() != null && department.getParentId() > 0) {
            Department parent = getById(department.getParentId());
            if (parent != null) {
                resp.setParentName(parent.getName());
            }
        }
        return resp;
    }

    private Department getDepartment(Integer id) {
        Department department = getById(id);
        if (Objects.isNull(department)) {
            throw new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND);
        }
        return department;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDepartment(DepartmentCreateReq req) {
        log.info("创建部门: {}", req);
        // 1. 检查部门名称是否重复
        checkDepartmentName(req.getName(), req.getTenantId());
        // 2. 创建部门
        Department department = new Department();
        BeanUtils.copyProperties(req, department);
        save(department);
    }

    @Override
    public DepartmentResp getDepartmentById(Integer id) {
        log.info("获取部门: {}", id);
        Department department = getDepartment(id);
        return setParentDept(department);
    }

    @Override
    public Page<DepartmentResp> pageDepartments(DepartmentQueryReq req) {
        log.info("获取部门列表: {}", req);
        // 1. 构建查询条件
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(req.getName()), Department::getName, req.getName())
                .eq(req.getParentId() != null, Department::getParentId, req.getParentId());

        // 2. 执行分页查询
        Page<Department> page = new Page<>(req.getCurrent(), req.getSize());
        Page<Department> departmentPage = page(page, wrapper);

        // 3. 转换结果
        Page<DepartmentResp> respPage = new Page<>(departmentPage.getCurrent(), departmentPage.getSize(), departmentPage.getTotal());
        respPage.setRecords(departmentPage.getRecords().stream().map(this::setParentDept).collect(java.util.stream.Collectors.toList()));

        return respPage;
    }

    @Override
    @Transactional
    public void deleteDepartment(Integer id) {
        // 1. 检查部门是否存在
        getDepartment(id);
        // 2. 检查是否有子部门
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getParentId, id);
        if (count(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DEPARTMENT_HAS_CHILDREN);
        }
        // 3. 删除部门
        removeById(id);
    }

    @Override
    @Transactional
    public void updateDepartment(DepartmentUpdateReq req) {
        // 1. 检查部门是否存在
        Department existingDepartment =  getDepartment(req.getId());

        // 2. 检查部门名称是否重复
        if (!existingDepartment.getName().equals(req.getName())) {
            LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Department::getName, req.getName())
                    .eq(Department::getTenantId, existingDepartment.getTenantId())
                    .ne(Department::getId, req.getId());
            if (count(wrapper) > 0) {
                throw new BusinessException(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
            }
        }

        // 3. 检查父部门是否存在
        if (req.getParentId() != null && req.getParentId() > 0) {
            Department parent = getById(req.getParentId());
            if (parent == null) {
                throw new BusinessException(ErrorCode.DEPARTMENT_PARENT_NOT_FOUND);
            }
            // 检查是否形成循环引用
            if (req.getParentId().equals(req.getId())) {
                throw new BusinessException(ErrorCode.DEPARTMENT_CIRCULAR_REFERENCE);
            }
        }
        // 4. 更新部门信息
        Department department = new Department();
        BeanUtils.copyProperties(req, department);
        department.setTenantId(existingDepartment.getTenantId());
        updateById(department);
    }
} 