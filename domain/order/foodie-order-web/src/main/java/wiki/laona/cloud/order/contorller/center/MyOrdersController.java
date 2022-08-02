package wiki.laona.cloud.order.contorller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.controller.BaseController;
import wiki.laona.cloud.order.pojo.vo.OrderStatusCountsVO;
import wiki.laona.cloud.order.service.center.MyOrdersService;
import wiki.laona.cloud.pojo.JsonResult;
import wiki.laona.cloud.pojo.PagedGridResult;

import javax.annotation.Resource;

/**
 * @author laona
 * @description 用户中心 - 我的订单
 * @since 2022-05-13 15:52
 **/
@Api(value = "用户中心-我的订单信息", tags = {"我的订单信息的相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

    @Resource
    protected MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public JsonResult query(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false) @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页是第几页", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Integer pageSize) {

        if (userId == null) {
            return JsonResult.errorMsg(null);
        }
        // 没有设置每页条数，则设置默认条数
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult result = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);

        return JsonResult.ok(result);
    }


    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public JsonResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId) {

        if (orderId == null) {
            return JsonResult.errorMsg(null);
        }

        myOrdersService.updateDeliverOrderStatus(orderId);

        return JsonResult.ok();
    }


    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JsonResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) {

        JsonResult checkResult = myOrdersService.checkUserOrder(userId, orderId);

        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean success = myOrdersService.updateReceiveOrderStatus(orderId);

        return success ? JsonResult.ok() : JsonResult.errorMsg("用户确认收货失败!");
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public JsonResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) {

        JsonResult checkResult = myOrdersService.checkUserOrder(userId, orderId);

        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean success = myOrdersService.delete(userId, orderId);

        return success ? JsonResult.ok() : JsonResult.errorMsg("删除订单失败!");
    }

    @ApiOperation(value = "订单状态统计", notes = "订单状态统计", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public JsonResult statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("参数错误!");
        }

        OrderStatusCountsVO statusCounts = myOrdersService.getOrderStatusCounts(userId);

        return JsonResult.ok(statusCounts);
    }


    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public JsonResult trend(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页是第几页", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Integer pageSize) {

        if (userId == null) {
            return JsonResult.errorMsg(null);
        }
        // 没有设置每页条数，则设置默认条数
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult result = myOrdersService.getMyOrdersTrend(userId, page, pageSize);

        return JsonResult.ok(result);
    }
}
