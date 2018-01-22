package com.tt.service.tt.serviceImpl;

import com.tt.mapper.TbItemParamItemMapper;
import com.tt.pojo.TbItemParamItem;
import com.tt.pojo.TbItemParamItemExample;
import com.tt.service.TbItemParamItemService;
import com.tt.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Autowired
    private TbItemParamItemMapper paramItemMapper;

    @Override
    public String GetTbItemParamItemByitemId(Long itemId) {

        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria cr = example.createCriteria();
        cr.andItemIdEqualTo(itemId);

        List<TbItemParamItem> list = paramItemMapper.selectByExample(example);

        if (list == null || list.size() == 0) {
            return "";

        }
        TbItemParamItem par = list.get(0);
        String data = par.getParamData();
        List<Map> list1 = JsonUtils.jsonToList(data, Map.class);
        //生成html
        StringBuffer sb = new StringBuffer();
        sb.append("<table width=\"100%\" border=\"1\">\n");
        sb.append("      <tbody>\n");
        for (Map m1 : list1) {
            sb.append("        <tr>\n");
            sb.append("        <th class=\"param-name\">" + m1.get("group") + "</th>\n");
            sb.append("       </tr>\n");
            List<Map> list2 = (List<Map>) m1.get("params");
            for (Map m2 : list2) {
                sb.append("        <tr>\n");
                sb.append("        <th class=\"param-name\">" + m2.get("k") + "</th>\n");
                sb.append("        <td class=\"param-value\" title=\"" + m2.get("v") + "\">" + m2.get("v") + "</td>\n");
                sb.append("       </tr>\n");
            }
        }
        sb.append("      </tbody>\n");
        sb.append("    </table>");
        return sb.toString();
    }
}
