package wiki.laona.cloud.order.contorller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import wiki.laona.cloud.controller.BaseController;
import wiki.laona.cloud.enums.YesOrNo;
import wiki.laona.cloud.order.pojo.OrderItems;
import wiki.laona.cloud.order.pojo.Orders;
import wiki.laona.cloud.order.pojo.bo.center.OrderItemsCommentBO;
import wiki.laona.cloud.order.service.center.MyCommentsService;
import wiki.laona.cloud.order.service.center.MyOrdersService;
import wiki.laona.cloud.pojo.JsonResult;
import wiki.laona.cloud.pojo.PagedGridResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author laona
 * @description 我的评价
 * @since 2022-05-14 15:25
 **/
@Api(value = "用户中心-评价管理", tags = {"我的评价管理的相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;
    @Resource
    protected MyOrdersService myOrdersService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancerClient client;

    @ApiOperation(value = "查询评价信息", notes = "查询评价信息", httpMethod = "POST")
    @PostMapping("/pending")
    public JsonResult pending(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = false) @RequestParam String orderId) {

        // 判断用户和订单是否关联
        JsonResult checkResult = myOrdersService.checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }
        // 判断每笔订单是否已经评论过，评价过就不在评论
        Orders myOrders = (Orders) checkResult.getData();
        if (ObjectUtils.nullSafeEquals(myOrders.getIsComment(), YesOrNo.YES.type)){
            return JsonResult.errorMsg("当前商品已经评价!");
        }

        List<OrderItems> result = myCommentsService.queryPendingComment(orderId);

        return JsonResult.ok(result);
    }

    @ApiOperation(value = "保存评价列表", notes = "保存评价列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public JsonResult saveList(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = false) @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList ) {


        // 判断用户和订单是否关联
        JsonResult checkResult = myOrdersService.checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (CollectionUtils.isEmpty(commentList)) {
            return JsonResult.errorMsg("评论内容不能为空");
        }

        myCommentsService.saveComments(orderId, userId, commentList);

        return JsonResult.ok();
    }


    @ApiOperation(value = "查询我的评论", notes = "查询我的评论", httpMethod = "POST")
    @PostMapping("/query")
    public JsonResult query(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页是第几页", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam(defaultValue = "10") Integer pageSize) {

        if (userId == null) {
            return JsonResult.errorMsg(null);
        }
        // 没有设置每页条数，则设置默认条数
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        // FIXME 后续修改为Feign
        // PagedGridResult result = myCommentsService.queryMyComments(userId, page, pageSize);
        ServiceInstance itemInstance = client.choose("FOODIE-ITEM-SERVICE");
        String url = String.format("http://%s:%s/item-comments-api/myComments?userId=%s&page=%s&pageSize=%s",
                itemInstance.getHost(),
                itemInstance.getPort(),
                userId,
                page,
                pageSize);
        PagedGridResult result = restTemplate.getForObject(url, PagedGridResult.class);

        return JsonResult.ok(result);
    }
}
