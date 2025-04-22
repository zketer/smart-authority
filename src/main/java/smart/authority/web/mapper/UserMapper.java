package smart.authority.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import smart.authority.web.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT p.code FROM permission p " +
            "JOIN role_permission rp ON p.id = rp.permission_id " +
            "JOIN user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> getUserPermissions(Integer userId);
}