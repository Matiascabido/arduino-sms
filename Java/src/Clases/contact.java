/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Date;
import java.math.BigDecimal;

/**
 *
 * @author lfd
 */
public class contact {
   
    private String numeroTel;
    private String mensaje;
    private boolean estado;
    private int id;

    public contact() {
    }

    public contact(String numeroTel, String mensaje, boolean estado, int id) {

        this.numeroTel = numeroTel;
        this.mensaje = mensaje;
        this.estado = estado;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

        
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    
    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


   
    
}
