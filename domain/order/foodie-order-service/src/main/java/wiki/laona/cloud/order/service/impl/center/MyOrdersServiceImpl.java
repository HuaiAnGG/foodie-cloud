package wiki.laona.cloud.order.service.impl.center;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import wiki.laona.cloud.enums.OrderStatusEnum;
import wiki.laona.cloud.enums.YesOrNo;
import wiki.laona.cloud.order.mapper.OrderStatusMapper;
import wiki.laona.cloud.order.mapper.OrdersMapper;
import wiki.laona.cloud.order.mapper.OrdersMapperCustom;
import wiki.laona.cloud.order.pojo.OrderStatus;
import wiki.laona.cloud.order.pojo.Orders;
import wiki.laona.cloud.order.pojo.vo.MyOrdersVO;
import wiki.laona.cloud.order.pojo.vo.OrderStatusCountsVO;
import wiki.laona.cloud.order.service.center.MyOrdersService;
import wiki.laona.cloud.pojo.JsonResult;
import wiki.laona.cloud.pojo.PagedGridResult;
import wiki.laona.cloud.services.BaseService;

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
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {

        Map<String, Object> paramsMap = new HashMap<>(1 << 4);
        paramsMap.put("userId", userId);
        if (orderStatus != null) {
            paramsMap.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);

        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(paramsMap);

        return setterPageGrid(list, page);
    }

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


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus waitReceiveOrderStatus = new OrderStatus();
        waitReceiveOrderStatus.setOrderId(orderId);
        waitReceiveOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        waitReceiveOrderStatus.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(waitReceiveOrderStatus, example);

    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(orders);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        OrderStatus updateOrdersStatus = new OrderStatus();
        updateOrdersStatus.setOrderId(orderId);
        updateOrdersStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrdersStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        int effectRows = orderStatusMapper.updateByExampleSelective(updateOrdersStatus, example);

        return effectRows > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean delete(String userId, String orderId) {

        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.YES.type);
        orders.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        int result = ordersMapper.updateByExampleSelective(orders, example);

        return result > 0;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        Map<String, Object> paramsMap = new HashMap<>(1 << 4);
        paramsMap.put("userId", userId);

        paramsMap.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCount(paramsMap);

        paramsMap.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCount(paramsMap);

        paramsMap.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCount(paramsMap);

        paramsMap.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        paramsMap.put("isComment", YesOrNo.YES.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCount(paramsMap);

        return new OrderStatusCountsVO(waitPayCounts, waitDeliverCounts, waitReceiveCounts, waitCommentCounts);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PagedGridResult getMyOrdersTrend(String userId, Integer page, Integer pageSize) {

        Map<String, Object> paramsMap = new HashMap<>(1 << 4);
        paramsMap.put("userId", userId);

        PageHelper.startPage(page, pageSize);

        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(paramsMap);

        return setterPageGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public JsonResult checkUserOrder(String userId, String orderId) {
        Orders checkResult = queryMyOrder(userId, orderId);

        if (checkResult == null) {
            return JsonResult.errorMsg("订单不存在!");
        }

        return JsonResult.ok(checkResult);
    }
}
