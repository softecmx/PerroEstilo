package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

public class ElementListViewPagosPendientes {
    private String statusPago;
    private String orden;
    private String total;

    public ElementListViewPagosPendientes() {
    }

    public ElementListViewPagosPendientes(String statusPago, String orden, String total) {
        this.statusPago = statusPago;
        this.orden = orden;
        this.total = total;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
