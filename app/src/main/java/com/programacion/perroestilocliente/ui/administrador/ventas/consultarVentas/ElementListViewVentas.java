package com.programacion.perroestilocliente.ui.administrador.ventas.consultarVentas;

public class ElementListViewVentas {
    private String fecha, noVentas,totalVentas;

    public ElementListViewVentas() {
    }

    public ElementListViewVentas(String fecha, String noVentas, String totalVentas) {
        this.fecha = fecha;
        this.noVentas = noVentas;
        this.totalVentas = totalVentas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNoVentas() {
        return noVentas;
    }

    public void setNoVentas(String noVentas) {
        this.noVentas = noVentas;
    }

    public String getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(String totalVentas) {
        this.totalVentas = totalVentas;
    }
}
