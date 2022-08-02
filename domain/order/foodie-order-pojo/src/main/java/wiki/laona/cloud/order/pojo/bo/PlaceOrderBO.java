package wiki.laona.cloud.order.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wiki.laona.cloud.pojo.ShopcartBO;

import java.util.List;

/**
 * Created by laona
 * 下单BO
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderBO {
    /**
     * 订单BO
     */
    private SubmitOrderBO order;
    /**
     * 购物车BO列表
     */
    private List<ShopcartBO> items;
}
