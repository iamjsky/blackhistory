package com.blackhistory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MainBannerModel {



    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("imgurl")
    @Expose
    public String imgurl;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("contents")
    @Expose
    public String contents;



}
