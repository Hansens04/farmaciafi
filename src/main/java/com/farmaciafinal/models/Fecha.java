package com.farmaciafinal.models;
import com.farmaciafinal.models.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fecha {
    //Atributos
    private int dia;
    private int mes;
    private int anio;

    //Constructor
    public Fecha(int dia, int mes, int anio){
        this.dia=dia;
        this.mes=mes;
        this.anio=anio;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    //Metodo para la fecha actual, se le debe adjuntar su libreria
    public void darFechaActual(){
        GregorianCalendar gc = new GregorianCalendar();
        dia=gc.get(Calendar.DAY_OF_MONTH);
        mes=gc.get(Calendar.MONTH)+1;
        anio=gc.get(Calendar.YEAR);
    }

    @Override
    public String toString() {
        return dia+"/"+mes+"/"+anio;
    }
}