package kr.co.wikibook.gallery.cart.model;

import lombok.Getter;

@Getter
public class CartGetRes {
    private int id;
    private int price;
    private String name;
    private String imgPath;
    private int discountPer;
}
