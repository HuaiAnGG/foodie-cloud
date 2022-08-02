package wiki.laona.cloud.user.service.impl.center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import wiki.laona.cloud.user.mapper.UsersMapper;
import wiki.laona.cloud.user.pojo.Users;
import wiki.laona.cloud.user.pojo.bo.center.CenterUsersBO;
import wiki.laona.cloud.user.service.center.CenterUserService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author laona
 * @description 用户中心服务实现类
 * @since 2022-05-12 17:00
 **/
@RestController
@Slf4j
public class CenterUserServiceImpl implements CenterUserService {

    @Resource
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Users queryUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Users updateUserInfo(String userId, CenterUsersBO centerUsersBO) {

        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUsersBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(updateUser);

        return queryUserInfo(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {

        Users updateUser = new Users();
        updateUser.setFace(faceUrl);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(updateUser);

        return queryUserInfo(userId);
    }
}
