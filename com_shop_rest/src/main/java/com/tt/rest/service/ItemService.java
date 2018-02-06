package com.tt.rest.service;

import com.tt.util.TaotaoResult;

public interface ItemService {

    TaotaoResult getItemBaseInfo(long id);

    TaotaoResult getItemDesc(long id);

    TaotaoResult getItemParam(long id);
}
