/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programacion.perroestilocliente.modelo;

import java.util.List;

/**
 *
 * @author por_s
 */
public class Kits {

    private String idKit;
    private String nombreKit;
    private String imgKit;
    private String preciKit;
    private String descuento;
    private String idUsuario;
    private List<DetallesKit> lstPruductos;
    private List<Galerias> lstGalerias;

    public Kits() {
    }

    public Kits(String idKit, String nombreKit, String imgKit, String preciKit, String descuento, String idUsuario, List<DetallesKit> lstPruductos, List<Galerias> lstGalerias) {
        this.idKit = idKit;
        this.nombreKit = nombreKit;
        this.imgKit = imgKit;
        this.preciKit = preciKit;
        this.descuento = descuento;
        this.idUsuario = idUsuario;
        this.lstPruductos = lstPruductos;
        this.lstGalerias = lstGalerias;
    }

    public List<DetallesKit> getLstPruductos() {
        return lstPruductos;
    }

    public void setLstPruductos(List<DetallesKit> lstPruductos) {
        this.lstPruductos = lstPruductos;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Galerias> getLstGalerias() {
        return lstGalerias;
    }

    public void setLstGalerias(List<Galerias> lstGalerias) {
        this.lstGalerias = lstGalerias;
    }

    public String getIdKit() {
        return idKit;
    }

    public void setIdKit(String idKit) {
        this.idKit = idKit;
    }

    public String getNombreKit() {
        return nombreKit;
    }

    public void setNombreKit(String nombreKit) {
        this.nombreKit = nombreKit;
    }

    public String getImgKit() {
        return imgKit;
    }

    public void setImgKit(String imgKit) {
        this.imgKit = imgKit;
    }

    public String getPreciKit() {
        return preciKit;
    }

    public void setPreciKit(String preciKit) {
        this.preciKit = preciKit;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

}
