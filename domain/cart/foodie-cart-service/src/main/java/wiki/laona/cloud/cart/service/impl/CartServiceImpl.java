package wiki.laona.cloud.cart.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import wiki.laona.cloud.cart.service.CartService;
import wiki.laona.cloud.enums.KeyEnum;
import wiki.laona.cloud.pojo.ShopcartBO;
import wiki.laona.cloud.utils.JsonUtils;
import wiki.laona.cloud.utils.RedisOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laona
 **/
@RestController
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisOperator redisOperator;

    @Override
    public boolean addItemToCart(String userId, ShopcartBO shopcartBO) {

        // 前端用户在登录的情况下，添加商品到购物车，会同时同步商品到 Redis 缓存
        final String shopcartKey = KeyEnum.FOODIE_SHOPCART.getKey() + ":" + userId;
        List<ShopcartBO> shopcartList = new ArrayList<>();

        String shopcartJson = redisOperator.get(shopcartKey);
        if (org.springframework.util.StringUtils.hasText(shopcartJson)) {
            // redis中已经有购物车
            shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);
            // 判断购物车种是否存在已有商品，如果有的话counts累加
            boolean isHaving = false;
            for (ShopcartBO sc : shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (ObjectUtils.nullSafeEquals(tmpSpecId, shopcartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopcartList.add(shopcartBO);
            }
        } else {
            // redis中没有购物车
            shopcartList = new ArrayList<>();
            // 直接添加到购物车中
            shopcartList.add(shopcartBO);
        }

        // 直接添加到 redis
        redisOperator.set(shopcartKey, JsonUtils.objectToJson(shopcartList));

        return true;
    }

    @Override
    public boolean removeItemFromCart(String userId, String itemSpecId) {

        final String shopcartKey = KeyEnum.FOODIE_SHOPCART.getKey() + ":" + userId;
        // 前端用户在登录的情况下，删除购物车的商品，同时删除 Redis 缓存中的商品
        List<ShopcartBO> shopcartList = new ArrayList<>();
        String shopcartJson = redisOperator.get(shopcartKey);
        // redis中已有购物车
        if (org.springframework.util.StringUtils.hasText(shopcartJson)) {
            shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);
            // 删除redis中商品
            for (ShopcartBO sc : shopcartList) {
                if (ObjectUtils.nullSafeEquals(sc.getSpecId(), itemSpecId)) {
                    shopcartList.remove(sc);
                    break;
                }
            }
            // 覆盖现有的redis中的购物车
            redisOperator.set(shopcartKey, JsonUtils.objectToJson(shopcartList));
        }

        return true;
    }

    @Override
    public boolean clearCart(String userId) {

        final String shopcartKey = KeyEnum.FOODIE_SHOPCART.getKey() + ":" + userId;
        redisOperator.del(shopcartKey);
        return true;
    }
}
