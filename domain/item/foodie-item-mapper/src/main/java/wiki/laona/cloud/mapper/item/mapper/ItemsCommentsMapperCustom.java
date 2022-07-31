package wiki.laona.cloud.mapper.item.mapper;

import org.apache.ibatis.annotations.Param;
import wiki.laona.cloud.item.pojo.ItemsComments;
import wiki.laona.cloud.item.pojo.vo.MyCommentVO;
import wiki.laona.cloud.my.mapper.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * @author huaian
 */
public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    /**
     * 保存评论
     *
     * @param map 参数
     */
    public void saveComments(@Param("paramsMap") Map<String, Object> map);

    /**
     * 查询用户的评论
     *
     * @param map 参数
     * @return 评论列表
     */
    public List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);
}