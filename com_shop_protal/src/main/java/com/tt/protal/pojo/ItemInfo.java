package com.tt.protal.pojo;

import com.tt.pojo.TbItem;

public class ItemInfo  extends TbItem{

    public String[] getImages() {
        String image=getImage();
        if (image != null) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
