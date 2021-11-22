package com.programacion.perroestilocliente.ui.ajustes;

public class ElementListView {


    private String ajuste, descripcion;
    private boolean switchItem;

    public ElementListView(String ajuste, String descripcion, boolean switchItem) {
        this.ajuste = ajuste;
        this.descripcion = descripcion;
        this.switchItem = switchItem;
    }

    public boolean isSwitchItem() {
        return switchItem;
    }

    public void setSwitchItem(boolean switchItem) {
        this.switchItem = switchItem;
    }

    public String getAjuste() {
        return ajuste;
    }

    public void setAjuste(String ajuste) {
        this.ajuste = ajuste;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
