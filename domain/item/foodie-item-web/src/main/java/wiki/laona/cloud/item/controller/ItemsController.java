package wiki.laona.cloud.item.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.laona.cloud.controller.BaseController;
import wiki.laona.cloud.item.pojo.Items;
import wiki.laona.cloud.item.pojo.ItemsImg;
import wiki.laona.cloud.item.pojo.ItemsParam;
import wiki.laona.cloud.item.pojo.ItemsSpec;
import wiki.laona.cloud.item.pojo.vo.CommentLevelCountsVO;
import wiki.laona.cloud.item.pojo.vo.ItemInfoVO;
import wiki.laona.cloud.item.pojo.vo.ShopcartVO;
import wiki.laona.cloud.item.service.ItemService;
import wiki.laona.cloud.pojo.JsonResult;
import wiki.laona.cloud.pojo.PagedGridResult;

import java.util.List;

/**
 * @author laona
 * @description 商品 controller
 * @date 2022-05-09 16:12
 **/
@Api(value = "商品接口", tags = {"商品信息展示相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("info/{itemId}")
    public JsonResult info(@ApiParam(name = "itemId", value = "商品id", required = false) @PathVariable String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return JsonResult.errorMsg(null);
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfo = new ItemInfoVO();
        itemInfo.setItem(item);
        itemInfo.setItemImgList(itemImgList);
        itemInfo.setItemSpecList(itemSpecList);
        itemInfo.setItemParams(itemParam);

        return JsonResult.ok(itemInfo);
    }

    @ApiOperation(value = "查询商品评论等级", notes = "查询商品评论等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JsonResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = false) @RequestParam String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return JsonResult.errorMsg(null);
        }

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return JsonResult.ok(countsVO);
    }


    @ApiOperation(value = "查询商品评论详情", notes = "查询商品评论详情", httpMethod = "GET")
    @GetMapping("/comments")
    public JsonResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true) @RequestParam String itemId,
            @ApiParam(name = "level", value = "评论等级", required = false) @RequestParam(defaultValue = "0") Integer level,
            @ApiParam(name = "page", value = "查询下一页是第几页", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(itemId)) {
            return JsonResult.errorMsg(null);
        }
        // 没有设置每页条数，则设置默认条数
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        /*  bug fix: 解决查看全部评论时，level为空的命令 */
        if (level == 0) {
            level = null;
        }
        PagedGridResult result = itemService.queryPagedComments(itemId, level, page, pageSize);

        return JsonResult.ok(result);
    }

    /**
     * 用户用户长时间未登录网站，刷新购物车的数据（主要是商品价格），类似于京东淘宝
     */
    @ApiOperation(value = "根据商品ids查找最新的商品数据", notes = "根据商品ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public JsonResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的商品规格ids", required = true, example = "1001,1003,1005")
            @RequestParam String itemSpecIds) {

        if (StringUtils.isBlank(itemSpecIds)) {
            // 没有正确内容就是啥都没有
            return JsonResult.ok();
        }

        List<ShopcartVO> shopcartList = itemService.queryItemsBySpecIds(itemSpecIds);

        return JsonResult.ok(shopcartList);
    }
}
