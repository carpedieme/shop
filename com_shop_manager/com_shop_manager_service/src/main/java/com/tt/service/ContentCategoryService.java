package com.tt.service;

import com.tt.util.EasyTreeNode;
import com.tt.util.EasyUIDateGrid;
import com.tt.util.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    List<EasyTreeNode> getCategory(long parentId);

    TaotaoResult insertContentCategory(long parentId,String name);

    TaotaoResult deleteContentCategory(long parentId,long id);

    TaotaoResult updateContentCategory(long id,String name);

    EasyUIDateGrid getContentCatgroyList(int page, int rows, long categoryId);
}
