package com.tt.service;

import com.tt.pojo.TbItemParam;
import com.tt.util.TaotaoResult;

public interface TbItemParamService {

    /**
     * 添加商品规格参数
     *
     * @param cid
     * @return
     */
    TaotaoResult getTbItemParam(long cid);

    /**
     * 提交商品规格参数
     *
     * @param param
     * @return
     */
    TaotaoResult insertTbItemParam(TbItemParam param);


}
