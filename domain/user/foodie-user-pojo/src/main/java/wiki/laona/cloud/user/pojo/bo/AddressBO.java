package wiki.laona.cloud.user.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author laona
 * @description 用户地址BO
 * @create 2022-05-10 21:29
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressBO {
    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    /**
     * 区域/区
     */
    private String district;
    private String detail;
}
