package wiki.laona.cloud.item.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wiki.laona.cloud.item.pojo.Items;
import wiki.laona.cloud.item.pojo.ItemsImg;
import wiki.laona.cloud.item.pojo.ItemsParam;
import wiki.laona.cloud.item.pojo.ItemsSpec;

import java.util.List;

/**
 * @author laona
 * @description 商品详情 VO
 * @date 2022-05-09 16:23
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoVO {
    Items item;
    List<ItemsImg> itemImgList;
    List<ItemsSpec> itemSpecList;
    ItemsParam itemParams;
}
