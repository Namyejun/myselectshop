package com.sparta.myselectshop.entity;

import lombok.Getter;
import org.json.JSONObject;

@Getter
public class ItemDto {
    private String title;
    private String link;
    private String image;
    private int lprice;

    public ItemDto(JSONObject json) {
        this.title = json.getString("title");
        this.link = json.getString("link");
        this.image = json.getString("image");
        this.lprice = json.getInt("lprice");
    }
}
