package wiki.laona.cloud.order.service.center;

import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.order.pojo.OrderItems;
import wiki.laona.cloud.order.pojo.bo.center.OrderItemsCommentBO;

import java.util.List;

/**
 * @author laona
 * @description 用户中心 - 我的订单信息service
 * @since 2022-05-14
 **/
@RequestMapping("order-comments-api")
public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品评论
     *
     * @param orderId 订单id
     * @return 我的订单
     */
    @GetMapping("orderItems")
    public List<OrderItems> queryPendingComment(@RequestParam("orderId") String orderId);

    /**
     * 保存评论列表
     *
     * @param orderId     订单id
     * @param userId      用户id
     * @param commentList 评论列表
     */
    @PostMapping("saveOrderComments")
    public void saveComments(@RequestParam("orderId") String orderId,
                             @RequestParam("userId") String userId,
                             @RequestBody List<OrderItemsCommentBO> commentList);

    /**
     * 查询我的评论列表
     *
     * @param userId   用户id
     * @param page     当前页码
     * @param pageSize 每页条数
     * @return 评论列表
     */
    // TODO 移到了商品中心 itemCommentsService
    // public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
