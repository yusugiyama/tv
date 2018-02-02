package com.example.oremo.tv;

import android.support.v4.app.Fragment;

/**
 * Created by ysugiyama on 2018/01/31.
 */

public enum FragmentEnum {

    hyoushi(1, "表紙", new HyoushiFragment()),
    idea(2,"アイディア", new Fragment2()),
    omake(3, "おまけ", new Fragment3());

    private int page;
    private String name;
    private Fragment fragment;

    FragmentEnum(int page, String name, Fragment fragment){
        this.page = page;
        this.name = name;
        this.fragment = fragment;
    }

    public int getPage(){
        return this.page;
    }

    public String getName(){
        return this.name;
    }

    public Fragment getFragment(){
        return this.fragment;
    }





}
