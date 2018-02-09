package com.tt.rest.service;

import com.tt.pojo.TbContent;
import java.util.List;

public interface ContentService {

    List<TbContent> getContentList(long contentCid);
}
