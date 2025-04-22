package smart.authority.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import smart.authority.web.model.entity.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}