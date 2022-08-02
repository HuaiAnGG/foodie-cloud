package wiki.laona.cloud.user.service.center;

import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.user.pojo.Users;
import wiki.laona.cloud.user.pojo.bo.center.CenterUsersBO;

/**
 * @author laona
 * @description 用户中心相关服务
 * @since 2022-05-12 16:58
 **/
@RequestMapping("center-user-api")
public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @GetMapping("profile")
    public Users queryUserInfo(@RequestParam("userId") String userId);

    /**
     * 修改用户信息
     *
     * @param userId        userId
     * @param centerUsersBO centerUsersBO
     * @return 用户信息
     */
    @PutMapping("profile/{userId}")
    public Users updateUserInfo(@PathVariable("userId") String userId,
                                @RequestBody CenterUsersBO centerUsersBO);

    /**
     * 更新用户头像
     *
     * @param userId  用户id
     * @param faceUrl 用户头像
     * @return 用户头像
     */
    @PostMapping("updatePhoto")
    public Users updateUserFace(@RequestParam("userId") String userId,
                                @RequestParam("faceUrl") String faceUrl);
}
