package com.example.oremo.tv;

import android.support.v4.app.Fragment;

/**
 * Created by ysugiyama on 2018/01/31.
 */

public enum FragmentEnum {

    hyoushi(0, "表紙", new HyoushiFragment()),
    koudou(1, "行動", new KoudouFragment()),

    ajairu(2, "アジャイル", new AjairuFragment()),
    apuri(3, "アプリ", new ApuriFragment()),

    kadai(4,"課題", new KadaiFragment()),
    idea(5,"アイディア", new IdeaFragment()),
    tvtitle(6,"テレビ", new TvTitleFragment()),
    tv(7,"テレビの説明", new TvFragment()),
    jissen(8, "行動する", new JissenFragment()),
    sekkei(9, "設計", new SekkeiFragment()),
    omake(10, "おまけ", new OmakeFragment());



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
