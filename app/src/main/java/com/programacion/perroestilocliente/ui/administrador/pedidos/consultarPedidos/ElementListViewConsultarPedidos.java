package com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos;

public class ElementListViewConsultarPedidos {


    private String ordenPedido;
    private String statusPedido;
    private String totalPedido;


    public ElementListViewConsultarPedidos() {
    }

    public ElementListViewConsultarPedidos(String ordenPedido, String statusPedido, String totalPedido) {
        this.ordenPedido = ordenPedido;
        this.statusPedido = statusPedido;
        this.totalPedido = totalPedido;
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
}
