package com.tt.protal.service.com.tt.protal.service.impl;

import com.tt.pojo.TbItemDesc;
import com.tt.pojo.TbItemParamItem;
import com.tt.protal.pojo.ItemInfo;
import com.tt.protal.service.ItemService;
import com.tt.protal.util.HttpClientUtil;
import com.tt.util.JsonUtils;
import com.tt.util.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author sjg
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${rest_baseurl}")
    private String rest_baseurl;

    @Value("${item_base_url}")
    private String item_base_url;

    @Value("${item_desc_url}")
    private String item_desc_url;

    @Value("${item_param_url}")
    private String item_param_url;


    /**
     * 获取商品基本信息
     *
     * @param id
     * @return
     */
    @Override
    public ItemInfo getItemById(Long id) {

        try {
            //调用rest服务层
            String str = HttpClientUtil.doGet(rest_baseurl + item_base_url + id);
            if (!StringUtils.isBlank(str)) {
                //有值
                TaotaoResult result = TaotaoResult.formatToPojo(str, ItemInfo.class);
                if (result.getStatus() == 200) {
                    ItemInfo item = (ItemInfo) result.getData();
                    return item;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取商品描述信息
     *
     * @param itemId
     * @return
     */
    @Override
    public String getItemDesc(Long itemId) {
        try {
            //调用rest服务层
            String json = HttpClientUtil.doGet(rest_baseurl + item_desc_url + itemId);
            if (!StringUtils.isBlank(json)) {
                //有值
                TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
                if (result.getStatus() == 200) {
                    TbItemDesc tbItemDesc = (TbItemDesc) result.getData();
                    return tbItemDesc.getItemDesc();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取商品规格参数信息
     *
     * @param itemId
     * @return
     */
    @Override
    public String getItamParam(Long itemId) {

        try {
            //调用rest服务
            String json = HttpClientUtil.doGet(rest_baseurl + item_param_url + itemId);
            if (!StringUtils.isBlank(json)) {
                //将json转换为java对象
                TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
                if (result.getStatus() == 200) {
                    TbItemParamItem tbItemParamItem = (TbItemParamItem) result.getData();
                    String paramData = tbItemParamItem.getParamData();
                    //生成html
                    // 把规格参数json数据转换成java对象
                    List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for(Map m1:jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for(Map m2:list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                            sb.append("            <td>"+m2.get("v")+"</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //返回html片段
                    return sb.toString();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
