package wiki.laona.cloud.user.controller;

import org.springframework.beans.BeanUtils;
import wiki.laona.cloud.controller.BaseController;
import wiki.laona.cloud.user.pojo.Users;
import wiki.laona.cloud.user.pojo.vo.UsersVO;
import wiki.laona.cloud.utils.RedisOperator;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by laona
 **/
public class UserBaseController extends BaseController {

    @Resource
    protected RedisOperator redisOperator;

    /**
     * 将 Users 实体转换成 UsersVO
     *
     * @param user 用户信息
     * @return UsersVO
     */
    protected UsersVO conventUserVO(Users user) {
        // 实现用户的 redis 会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(), uniqueToken);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
