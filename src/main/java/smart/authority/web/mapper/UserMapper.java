package smart.authority.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import smart.authority.web.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}