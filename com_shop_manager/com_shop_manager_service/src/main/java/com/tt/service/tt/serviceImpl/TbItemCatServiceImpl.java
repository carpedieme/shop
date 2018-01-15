package com.tt.service.tt.serviceImpl;

import com.tt.mapper.TbItemCatMapper;
import com.tt.pojo.TbItemCat;
import com.tt.pojo.TbItemCatExample;
import com.tt.service.TbItemCatService;
import com.tt.util.EasyTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyTreeNode> getTbitemList(long parentId) {

        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria cr = example.createCriteria();
        cr.andParentIdEqualTo(parentId);
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        List<EasyTreeNode> easyTreeNodeList = new ArrayList<>();
        for (TbItemCat item : list
                ) {
            EasyTreeNode etn = new EasyTreeNode();
            etn.setId(item.getId());
            etn.setText(item.getName());
            etn.setState(item.getIsParent() ? "closed" : "open");
            easyTreeNodeList.add(etn);
        }
        return easyTreeNodeList;
    }
}
