package com.tt.service.tt.serviceImpl;

import com.tt.mapper.TbItemParamItemMapper;
import com.tt.mapper.TbItemParamMapper;
import com.tt.pojo.TbItemParam;
import com.tt.pojo.TbItemParamExample;
import com.tt.pojo.TbItemParamItem;
import com.tt.service.TbItemParamService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 添加商品规格参数
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {


    @Autowired
    private TbItemParamMapper paramMapper;




    @Override
    public TaotaoResult getTbItemParam(long cid) {

        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria cr = example.createCriteria();
        cr.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = paramMapper.selectByExampleWithBLOBs(example);
//判断是否查询到结果
        if (list != null && list.size() > 0) {
            return TaotaoResult.ok(list.get(0));


        }
        return TaotaoResult.ok();

    }

    @Override
    public TaotaoResult insertTbItemParam(TbItemParam param) {
        //补全pojo
        param.setCreated(new Date());
        param.setUpdated(new Date());
        paramMapper.insert(param);
        return TaotaoResult.ok();
    }


}
