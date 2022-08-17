package wiki.laona.cloud.cart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.cart.service.CartService;
import wiki.laona.cloud.pojo.JsonResult;
import wiki.laona.cloud.pojo.ShopcartBO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author laona
 * @description 购物车接口
 * @since 2022-05-10 15:40
 **/
@Api(value = "购物车相关接口", tags = {"购物车相关接口"})
@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    private static final Logger logger = LoggerFactory.getLogger(ShopcartController.class);

    @Resource
    private CartService cartService;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(@RequestParam String userId,
                          @RequestBody ShopcartBO shopcartBO,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("");
        }

        // logger.info("购物车信息：{}", shopcartBO);
        cartService.addItemToCart(userId, shopcartBO);

        return JsonResult.ok();
    }


    @ApiOperation(value = "删除购物车的商品", notes = "删除购物车的商品", httpMethod = "POST")
    @PostMapping("/del")
    public JsonResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JsonResult.errorMsg("参数不能为空");
        }

        cartService.removeItemFromCart(userId, itemSpecId);

        return JsonResult.ok();
    }

    @ApiOperation(value = "清空购物车的商品", notes = "清空购物车的商品", httpMethod = "POST")
    @PostMapping("/del")
    public JsonResult del(@RequestParam String userId,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId) ) {
            return JsonResult.errorMsg("参数不能为空");
        }

        cartService.clearCart(userId);

        return JsonResult.ok();
    }
}
