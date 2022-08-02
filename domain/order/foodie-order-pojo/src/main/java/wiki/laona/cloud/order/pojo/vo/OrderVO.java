package wiki.laona.cloud.order.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wiki.laona.cloud.pojo.ShopcartBO;

import java.util.List;


/**
 * @author laona
 * @description 订单VO
 * @since 2022-05-11 23:44
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    /**
     * 待删除购物车列表
     */
    private List<ShopcartBO> shopcartList;
}
