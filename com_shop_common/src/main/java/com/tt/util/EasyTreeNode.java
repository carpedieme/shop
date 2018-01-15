package com.tt.util;
/**
 * easyUI树形控件节点格式
 */
public class EasyTreeNode {

    public long id;
    public String text;
    public String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
