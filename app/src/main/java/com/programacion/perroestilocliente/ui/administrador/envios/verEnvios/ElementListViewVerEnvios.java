package com.programacion.perroestilocliente.ui.administrador.envios.verEnvios;

public class ElementListViewVerEnvios {


    private String ordenPedido;
    private String statusPedido;
    private String totalPedido;
    private String fecha;


    public ElementListViewVerEnvios() {
    }

    public ElementListViewVerEnvios(String ordenPedido, String statusPedido, String totalPedido, String fecha) {
        this.ordenPedido = ordenPedido;
        this.statusPedido = statusPedido;
        this.totalPedido = totalPedido;
        this.fecha = fecha;
    }

    public String getOrdenPedido() {
        return ordenPedido;
    }

    public void setOrdenPedido(String ordenPedido) {
        this.ordenPedido = ordenPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(String totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
