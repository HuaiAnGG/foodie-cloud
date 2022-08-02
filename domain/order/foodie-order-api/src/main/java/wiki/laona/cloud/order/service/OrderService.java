package wiki.laona.cloud.order.service;

import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.order.pojo.OrderStatus;
import wiki.laona.cloud.order.pojo.bo.PlaceOrderBO;
import wiki.laona.cloud.order.pojo.vo.OrderVO;

/**
 * @author laona
 * @description 订单service
 * @since 2022-05-11 15:21
 **/
@RequestMapping("order-api")
public interface OrderService {

    /**
     * 创建订单信息
     *
     * @param orderBO 下单BO
     * @return {@link OrderVO} 订单VO
     */
    @PostMapping("placeOrder")
    public OrderVO createOrder(@RequestBody PlaceOrderBO orderBO);

    /**
     * 根据订单id修改订单状态
     *
     * @param orderId     订单id
     * @param orderStatus 订单状态
     */
    @PostMapping("updateStatus")
    public void updateOrderStatus(@RequestParam("orderId") String orderId,
                                  @RequestParam("orderStatus") Integer orderStatus);


    /**
     * 根据订单id查询订单状态
     *
     * @param orderId 订单id
     * @return 订单状态
     */
    @GetMapping("order/{orderId}")
    public OrderStatus queryOrderStatusInfo(@PathVariable("orderId") String orderId);

    /**
     * 关闭支付超时未支付订单
     */
    @PostMapping("closePendingOrder")
    public void closeOrder();

}
