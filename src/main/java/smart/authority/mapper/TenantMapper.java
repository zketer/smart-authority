package smart.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import smart.authority.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {
}