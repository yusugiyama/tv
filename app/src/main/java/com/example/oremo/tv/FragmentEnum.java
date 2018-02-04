package com.example.oremo.tv;

import android.support.v4.app.Fragment;

/**
 * Created by ysugiyama on 2018/01/31.
 */

public enum FragmentEnum {

    hyoushi(1, "表紙", new HyoushiFragment()),
    idea(2,"アイディア", new IdeaFragment()),
    omake(3, "おまけ", new OmakeFragment()),
    tv(5,"テレビ", new TvFragment()),
    jissen(6, "実践", new JissenFragment()),
    koudou(4, "行動", new KoudouFragment());


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
