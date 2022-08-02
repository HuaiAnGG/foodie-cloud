package wiki.laona.cloud.user.mapper;

import org.springframework.stereotype.Repository;
import wiki.laona.cloud.my.mapper.MyMapper;
import wiki.laona.cloud.user.pojo.Users;

@Repository(value = "usersMapper")
public interface UsersMapper extends MyMapper<Users> {
}