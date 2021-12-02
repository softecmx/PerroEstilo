package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

public class ElementListViewPagosPendientes {
    private String statusPago;
    private Integer orden,Total;

    public ElementListViewPagosPendientes() {
    }

    public ElementListViewPagosPendientes(String statusPago, Integer orden, Integer Total) {
        this.statusPago = statusPago;
        this.orden = orden;
        this.Total = Total;
    }

    public String getStatusPago() {
        return statusPago;
    }

    public void setStatusPago(String statusPago) {
        this.statusPago = statusPago;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getTotal() {
        return Total;
    }

    public void setTotal(Integer Total) {
        this.Total = Total;
    }
}
