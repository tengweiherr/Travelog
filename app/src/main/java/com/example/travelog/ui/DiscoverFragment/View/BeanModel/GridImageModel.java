package com.example.travelog.ui.DiscoverFragment.View.BeanModel;

import java.util.List;

/**
 * Created by dong.he on 2017/2/6.
 */

public class GridImageModel {
    private String imgUrl;
    private List<com.example.travelog.ui.DiscoverFragment.View.BeanModel.ListItemModel> list;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<com.example.travelog.ui.DiscoverFragment.View.BeanModel.ListItemModel> getList() {
        return list;
    }

    public void setList(List<com.example.travelog.ui.DiscoverFragment.View.BeanModel.ListItemModel> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GridImageModle{" +
                "imgUrl='" + imgUrl + '\'' +
                ", list=" + list +
                '}';
    }
}
