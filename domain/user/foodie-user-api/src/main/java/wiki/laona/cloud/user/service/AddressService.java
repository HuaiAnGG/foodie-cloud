package wiki.laona.cloud.user.service;

import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.user.pojo.UserAddress;
import wiki.laona.cloud.user.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author laona
 * @description 地址service
 * @create 2022-05-10 21:24
 **/
@RequestMapping("address-api")
public interface AddressService {

    /**
     * 根据用户id查询用户的收货地址列表
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("addressList")
    public List<UserAddress> queryAll(@RequestParam("userId") String userId);

    /**
     * 用户新增地址
     *
     * @param addressBO 地址BO
     */
    @PostMapping("address")
    public void addNewUserAddress(@RequestBody AddressBO addressBO);

    /**
     * 用户修改地址
     *
     * @param addressBO 地址BO
     */
    @PutMapping("address")
    public void updateUserAddress(@RequestBody AddressBO addressBO);

    /**
     * 根据用户id和地址id，删除对应的用户地址信息
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    @DeleteMapping("delete")
    public void deleteUserAddress(@RequestParam("userId") String userId,
                                  @RequestParam("addressId") String addressId);

    /**
     * 修改默认地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    @PostMapping("serDefaultAddress")
    public void updateUserAddressToBeDefault(@RequestParam("userId") String userId,
                                             @RequestParam("addressId") String addressId);

    /**
     * 根据用户id和地址id，查询具体的用户地址对象信息
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @return {@linkplain  UserAddress 用户地址}
     */
    @GetMapping("userAddress")
    public UserAddress queryUserAddress(@RequestParam("userId") String userId,
                                        @RequestParam(value = "addressId", required = false) String addressId);
}
