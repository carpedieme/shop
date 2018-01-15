package com.tt.service;

import com.tt.util.EasyTreeNode;

import java.util.List;

public interface TbItemCatService {

    List<EasyTreeNode> getTbitemList(long parentId);
}
