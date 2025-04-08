package smart.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import smart.authority.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}