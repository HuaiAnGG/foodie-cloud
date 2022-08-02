package wiki.laona.cloud.user.service;


import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.user.pojo.Users;
import wiki.laona.cloud.user.pojo.bo.UserBo;

/**
 * @author laona
 * @description 用户接口
 * @date 2022-04-27 22:23
 **/
@RequestMapping("user-api")
public interface UserService {


    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     */
    @GetMapping("user/exists")
    public boolean queryUsernameIsExists(@RequestParam("username") String username);

    /**
     * 新建用户
     *
     * @param userBo userBo
     */
    @PostMapping("user")
    public Users createUser(@RequestBody UserBo userBo);

    /**
     * 检索用户名和密码是否匹配，用于登录；
     *
     * @param username 用户名
     * @param password 密码
     */
    @GetMapping("verify")
    public Users queryUserForLogin(@RequestParam("username") String username,
                                   @RequestParam("password") String password);
}
