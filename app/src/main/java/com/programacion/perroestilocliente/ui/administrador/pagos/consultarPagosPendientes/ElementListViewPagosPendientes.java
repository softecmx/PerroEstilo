package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

public class ElementListViewPagosPendientes {
    private String statusPago;
    private float Total;
    private  String orden;

    public ElementListViewPagosPendientes() {
    }

    public ElementListViewPagosPendientes(String statusPago, String orden, float Total) {
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

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }
}
