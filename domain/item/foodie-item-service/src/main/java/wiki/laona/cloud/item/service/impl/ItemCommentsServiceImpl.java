package wiki.laona.cloud.item.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wiki.laona.cloud.item.pojo.vo.MyCommentVO;
import wiki.laona.cloud.item.service.ItemCommentsService;
import wiki.laona.cloud.mapper.item.mapper.ItemsCommentsMapperCustom;
import wiki.laona.cloud.pojo.PagedGridResult;
import wiki.laona.cloud.services.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laona
 **/
@RestController
@Slf4j
public class ItemCommentsServiceImpl extends BaseService implements ItemCommentsService {

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Override
    public PagedGridResult queryMyComments(@RequestParam(value = "userId") String userId,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Map<String, Object> map= new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);

        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);
        return setterPageGrid(list, page);
    }

    @Override
    public void saveComments(Map<String, Object> map) {
        itemsCommentsMapperCustom.saveComments(map);
    }
}
