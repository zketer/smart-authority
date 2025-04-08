package smart.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import smart.authority.entity.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}