package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

/**
 * @author lynn
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    @Transactional
    public DepartmentResp createDepartment(DepartmentCreateReq req) {
        // 1. 检查部门名称是否重复
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getName, req.getName())
                .eq(Department::getTenantId, req.getTenantId());
        if (count(wrapper) > 0) {
            throw new RuntimeException("部门名称已存在");
        }

        // 2. 创建部门
        Department department = new Department();
        BeanUtils.copyProperties(req, department);
        save(department);
        return getDepartmentById(department.getId());
    }

    @Override
    public DepartmentResp getDepartmentById(Integer id) {
        Department department = getById(id);
        if (department == null) {
            return null;
        }
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

    @Override
    public Page<DepartmentResp> pageDepartments(DepartmentQueryReq req) {
        // 1. 构建查询条件
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(req.getName()), Department::getName, req.getName())
                .eq(req.getParentId() != null, Department::getParentId, req.getParentId());

        // 2. 执行分页查询
        Page<Department> page = new Page<>(req.getCurrent(), req.getSize());
        Page<Department> departmentPage = page(page, wrapper);

        // 3. 转换结果
        Page<DepartmentResp> respPage = new Page<>(departmentPage.getCurrent(), departmentPage.getSize(), departmentPage.getTotal());
        respPage.setRecords(departmentPage.getRecords().stream().map(department -> {
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
        }).collect(java.util.stream.Collectors.toList()));

        return respPage;
    }

    @Override
    @Transactional
    public void deleteDepartment(Integer id) {
        // 1. 检查部门是否存在
        Department department = getById(id);
        if (department == null) {
            throw new RuntimeException("部门不存在");
        }

        // 2. 检查是否有子部门
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getParentId, id);
        if (count(wrapper) > 0) {
            throw new RuntimeException("该部门下存在子部门，无法删除");
        }

        // 3. 删除部门
        removeById(id);
    }

    @Override
    @Transactional
    public void updateDepartment(DepartmentUpdateReq req) {
        // 1. 检查部门是否存在
        Department existingDepartment = getById(req.getId());
        if (existingDepartment == null) {
            throw new RuntimeException("部门不存在");
        }

        // 2. 检查部门名称是否重复
        if (!existingDepartment.getName().equals(req.getName())) {
            LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Department::getName, req.getName())
                    .eq(Department::getTenantId, existingDepartment.getTenantId())
                    .ne(Department::getId, req.getId());
            if (count(wrapper) > 0) {
                throw new RuntimeException("部门名称已存在");
            }
        }

        // 3. 检查父部门是否存在
        if (req.getParentId() != null && req.getParentId() > 0) {
            Department parent = getById(req.getParentId());
            if (parent == null) {
                throw new RuntimeException("父部门不存在");
            }
            // 检查是否形成循环引用
            if (req.getParentId().equals(req.getId())) {
                throw new RuntimeException("不能将自己设为父部门");
            }
        }

        // 4. 更新部门信息
        Department department = new Department();
        BeanUtils.copyProperties(req, department);
        department.setTenantId(existingDepartment.getTenantId());
        updateById(department);
    }
} 