package wiki.laona.cloud.cart.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wiki.laona.cloud.pojo.ShopcartBO;

/**
 * Created by laona
 **/
@RequestMapping("cart-api")
public interface CartService {

    @PostMapping("addItem")
    public boolean addItemToCart(@RequestParam("userId") String userId,
                                 @RequestBody ShopcartBO shopcartBO);

    @PostMapping("removeItem")
    public boolean removeItemFromCart(@RequestParam("userId") String userId,
                                      @RequestParam("itemSpecId") String itemSpecId);

    @PostMapping("removeItem")
    public boolean clearCart(@RequestParam("userId") String userId);
}
