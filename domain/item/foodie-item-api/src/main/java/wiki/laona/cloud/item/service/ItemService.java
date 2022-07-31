package wiki.laona.cloud.item.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wiki.laona.cloud.item.pojo.Items;
import wiki.laona.cloud.item.pojo.ItemsImg;
import wiki.laona.cloud.item.pojo.ItemsParam;
import wiki.laona.cloud.item.pojo.ItemsSpec;
import wiki.laona.cloud.item.pojo.vo.CommentLevelCountsVO;
import wiki.laona.cloud.item.pojo.vo.ShopcartVO;
import wiki.laona.cloud.pojo.PagedGridResult;

import java.util.List;

/**
 * @author laona
 * @description 商品信息接口
 * @date 2022-05-09 15:50
 **/
@RequestMapping("item-api")
public interface ItemService {

    /**
     * 根据商品 id 查询商品详情
     *
     * @param itemId 商品 id
     * @return 商品详情
     */
    @GetMapping("item")
    public Items queryItemById(@RequestParam("itemId") String itemId);

    /**
     * 根据商品 id 查询商品图片列表
     *
     * @param itemId 商品 id
     * @return 商品图片列表
     */
    @GetMapping("itemImages")
    public List<ItemsImg> queryItemImgList(@RequestParam("itemId") String itemId);

    /**
     * 根据商品 id 查询商品规格
     *
     * @param itemId 商品 id
     * @return 商品规格
     */
    @GetMapping("itemSpecs")
    public List<ItemsSpec> queryItemSpecList(@RequestParam("itemId") String itemId);

    /**
     * 根据商品 id 查询商品参数
     *
     * @param itemId 商品 id
     * @return 商品参数
     */
    @GetMapping("itemParam")
    public ItemsParam queryItemParam(@RequestParam("itemId") String itemId);

    /**
     * 查询当前商品的评论等级数量
     *
     * @param itemId 商品 id
     * @return 评论数量
     */
    @GetMapping("countComments")
    public CommentLevelCountsVO queryCommentCounts(@RequestParam("itemId") String itemId);

    /**
     * 根据商品id 查询商品的评价（分页）
     *
     * @param itemId   商品 id
     * @param level    评论等级
     * @param page     当前页码
     * @param pageSize 每页条数
     * @return 评价
     */
    @GetMapping("pagedComments")
    public PagedGridResult queryPagedComments(@RequestParam("itemId") String itemId,
                                              @RequestParam(value = "level", required = false) Integer level,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize);

    /**
     * 根据规格ids查询最新购物车中商品数据（用于渲染刷新购物车中的商品数据）
     *
     * @param specIds 拼接的规格ids
     * @return 购物车中的商品数据
     */
    @GetMapping("getCartBySpecIds")
    public List<ShopcartVO> queryItemsBySpecIds(@RequestParam("specIds") String specIds);

    /**
     * 根据规格id查询商品规格
     *
     * @param itemSpecId 规格id
     * @return 商品规格
     */
    @GetMapping("itemSpec")
    public ItemsSpec queryItemSpecById(@RequestParam("specId") String itemSpecId);

    /**
     * 根据商品id查询商品图片-主图url
     *
     * @param itemId 商品id
     * @return 商品图片url
     */
    @GetMapping("primaryImage")
    public String queryItemMainImgById(@RequestParam("itemId") String itemId);

    /**
     * 减少库存
     *
     * @param specId    规格id
     * @param buyCounts 购买数量
     */
    @PostMapping("decreaseStock")
    public void decreaseItemSpecStock(@RequestParam("specId") String specId,
                                      @RequestParam("buyCounts") int buyCounts);
}
