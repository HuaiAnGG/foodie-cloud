package wiki.laona.cloud.order.service.center;

import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.order.pojo.Orders;
import wiki.laona.cloud.order.pojo.vo.OrderStatusCountsVO;
import wiki.laona.cloud.pojo.JsonResult;
import wiki.laona.cloud.pojo.PagedGridResult;

/**
 * @author laona
 * @description 用户中心 - 我的订单信息service
 * @since 2022-05-13 15:25
 **/
@RequestMapping("myorder-api")
public interface MyOrdersService {

    /**
     * 查询我的订单（分页）
     *
     * @param userId      用户id
     * @param orderStatus 订单状态
     * @param page        当前页码
     * @param pageSize    每页多少条
     * @return 我的订单
     */
    @GetMapping("order/query")
    public PagedGridResult queryMyOrders(@RequestParam("userId") String userId,
                                         @RequestParam("orderStatus") Integer orderStatus,
                                         @RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    /**
     * 更改订单id的订单为发货状态
     *
     * @param orderId 订单id
     */
    @PostMapping("order/delivered")
    public void updateDeliverOrderStatus(@RequestParam("userId") String orderId);


    /**
     * 查询我的订单
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return orders
     */
    @GetMapping("order/details")
    public Orders queryMyOrder(@RequestParam("userId") String userId,
                               @RequestParam("orderId") String orderId);

    /**
     * 更改订单id的订单为收货状态
     *
     * @param orderId 订单id
     * @return 更改成功
     */
    @PostMapping("order/received")
    public boolean updateReceiveOrderStatus(@RequestParam("orderId") String orderId);

    /**
     * 删除用户订单
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return 删除成功
     */
    @DeleteMapping("delete")
    public boolean delete(@RequestParam("userId") String userId,
                          @RequestParam("orderId") String orderId);

    /**
     * 根据用户id查询订单状态数量
     *
     * @param userId 用户id
     * @return 订单状态统计VO
     */
    @GetMapping("order/counts")
    public OrderStatusCountsVO getOrderStatusCounts(@RequestParam("userId") String userId);

    /**
     * 查询用户订单动向
     *
     * @param userId   用户id
     * @param page     当前页码
     * @param pageSize 每页多少条
     * @return 订单动向
     */
    @GetMapping("order/trend")
    PagedGridResult getMyOrdersTrend(@RequestParam("userId") String userId,
                                     @RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize);

    /**
     * 检查用户订单
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 结果
     */
    @GetMapping("checkUserOrder")
    public JsonResult checkUserOrder(@RequestParam("userId") String userId,
                                     @RequestParam("orderId") String orderId);
}
