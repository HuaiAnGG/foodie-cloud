package wiki.laona.cloud.order.service.impl.center;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import wiki.laona.cloud.enums.YesOrNo;
import wiki.laona.cloud.order.mapper.OrderItemsMapper;
import wiki.laona.cloud.order.mapper.OrderStatusMapper;
import wiki.laona.cloud.order.mapper.OrdersMapper;
import wiki.laona.cloud.order.pojo.OrderItems;
import wiki.laona.cloud.order.pojo.OrderStatus;
import wiki.laona.cloud.order.pojo.Orders;
import wiki.laona.cloud.order.pojo.bo.center.OrderItemsCommentBO;
import wiki.laona.cloud.order.service.center.MyCommentsService;
import wiki.laona.cloud.services.BaseService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laona
 * @description 用户中心 - 我的订单信息 service
 * @since 2022-05-13 15:26
 **/
@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    private Sid sid;

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    // @Autowired
    // private ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    // TODO 临时解决方案,后续改成feign远程调用
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancerClient client;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);

        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价
        for (OrderItemsCommentBO commentBO : commentList) {
            commentBO.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>(1 << 4);
        map.put("userId", userId);
        map.put("commentList", commentList);
        // itemsCommentsMapperCustom.saveComments(map);

        ServiceInstance instance = client.choose("FOODIE-ITEM-SERVICE");
        String url = String.format("http://%s:%s/item-comments-api/saveComments",
                instance.getHost(),
                instance.getPort());
        restTemplate.postForLocation(url, map);

        // 2. 修改订单评价
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态的留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    // TODO 已经移到itemCommentsService中
    // @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    // @Override
    // public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
    //
    //     Map<String, Object> paramsMap = new HashMap<>(1 << 4);
    //     paramsMap.put("userId", userId);
    //
    //     PageHelper.startPage(page, pageSize);
    //     List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(paramsMap);
    //
    //     return setterPageGrid(list, page);
    // }

    // /**
    //  * 分页操作
    //  *
    //  * @param list 需要分页的列表
    //  * @param page 当前页码
    //  * @return 分页查询结果
    //  */
    // private PagedGridResult setterPageGrid(List<?> list, Integer page) {
    //     PageInfo<?> pageInfo = new PageInfo<>(list);
    //     PagedGridResult grid = new PagedGridResult();
    //     grid.setPage(page);
    //     grid.setRows(list);
    //     grid.setTotal(pageInfo.getPages());
    //     grid.setRecords(pageInfo.getTotal());
    //     return grid;
    // }
}
