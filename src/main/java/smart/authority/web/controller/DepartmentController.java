package smart.authority.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.req.department.DepartmentCreateReq;
import smart.authority.web.model.req.department.DepartmentQueryReq;
import smart.authority.web.model.req.department.DepartmentUpdateReq;
import smart.authority.web.model.resp.DepartmentResp;
import smart.authority.web.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lynn
 */
@Tag(name = "DepartmentController", description = "部门相关接口")
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @Operation(summary = "创建部门")
    @PostMapping
    public ApiResponse<Void> createDepartment(@Validated @RequestBody DepartmentCreateReq req) {
        departmentService.createDepartment(req);
        return ApiResponse.success(null);
    }

    @Operation(summary = "获取部门详情")
    @GetMapping("/{id}")
    public ApiResponse<DepartmentResp> getDepartmentById(@PathVariable Integer id) {
        return ApiResponse.success(departmentService.getDepartmentById(id));
    }

    @Operation(summary = "分页查询部门")
    @GetMapping
    public ApiResponse<Page<DepartmentResp>> pageDepartments(@Valid DepartmentQueryReq req) {
        return ApiResponse.success(departmentService.pageDepartments(req));
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDepartment(@PathVariable Integer id) {
        departmentService.deleteDepartment(id);
        return ApiResponse.success(null);
    }

    @Operation(summary = "更新部门")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateDepartment(@PathVariable Integer id, @Validated @RequestBody DepartmentUpdateReq req) {
        req.setId(id);
        departmentService.updateDepartment(req);
        return ApiResponse.success(null);
    }
} 