package wiki.laona.cloud.item.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wiki.laona.cloud.pojo.PagedGridResult;

import java.util.Map;

/**
 * Created by laona
 **/
@RequestMapping("item-comments-api")
public interface ItemCommentsService {

    /**
     * 查询我的评论列表
     *
     * @param userId   用户id
     * @param page     当前页码
     * @param pageSize 每页条数
     * @return 评论列表
     */
    @GetMapping("myComments")
    public PagedGridResult queryMyComments(@RequestParam(value = "userId") String userId,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize);

    /**
     * 保存评论信息
     *
     * @param map 入参
     */
    @PostMapping("saveComments")
    public void saveComments(Map<String, Object> map);
}
