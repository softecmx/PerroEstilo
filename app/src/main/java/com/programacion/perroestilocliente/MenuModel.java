package com.programacion.perroestilocliente;

public class MenuModel {
    public String menuName, url;
    public  int img;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, String url, int img) {
        this.img=img;
        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
