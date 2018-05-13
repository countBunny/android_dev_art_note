package com.example.countbunny.launchmodetest1.bitmapdecode;

import com.example.countbunny.launchmodetest1.touchconflict.ListViewEx;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoRootBean {

    //api is {@link "http://image.baidu.com/data/imgs?col=&tag=&sort=&pn=&rn=&p=channel&from=1"}

    @SerializedName("imgs")
    public List<PhotoBean> photos;
}
