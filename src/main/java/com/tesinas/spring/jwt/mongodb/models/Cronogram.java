package com.tesinas.spring.jwt.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cronograma")
public class Cronogram {

    @Id
    private String  id;

    private String year;

    private String period;

    private String portadaFecha;

    private String seccionIntroduccionFecha;

    private String capitulo1Fecha;

    private String capitulo2Fecha;

    private String capitulo3Fecha;

    private String conclusionFecha;

    private Boolean isCustom;


    public Cronogram(){

    }
    public Cronogram(String id, String year, String period, String portadaFecha, String seccionIntroduccionFecha, String capitulo1Fecha, String capitulo2Fecha, String capitulo3Fecha, String conclusionFecha, Boolean isCustom) {
        this.id = id;
        this.year = year;
        this.period = period;
        this.portadaFecha = portadaFecha;
        this.seccionIntroduccionFecha = seccionIntroduccionFecha;
        this.capitulo1Fecha = capitulo1Fecha;
        this.capitulo2Fecha = capitulo2Fecha;
        this.capitulo3Fecha = capitulo3Fecha;
        this.conclusionFecha = conclusionFecha;
        this.isCustom = isCustom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPortadaFecha() {
        return portadaFecha;
    }

    public void setPortadaFecha(String portadaFecha) {
        this.portadaFecha = portadaFecha;
    }

    public String getSeccionIntroduccionFecha() {
        return seccionIntroduccionFecha;
    }

    public void setSeccionIntroduccionFecha(String seccionIntroduccionFecha) {
        this.seccionIntroduccionFecha = seccionIntroduccionFecha;
    }

    public String getCapitulo1Fecha() {
        return capitulo1Fecha;
    }

    public void setCapitulo1Fecha(String capitulo1Fecha) {
        this.capitulo1Fecha = capitulo1Fecha;
    }

    public String getCapitulo2Fecha() {
        return capitulo2Fecha;
    }

    public void setCapitulo2Fecha(String capitulo2Fecha) {
        this.capitulo2Fecha = capitulo2Fecha;
    }

    public String getCapitulo3Fecha() {
        return capitulo3Fecha;
    }

    public void setCapitulo3Fecha(String capitulo3Fecha) {
        this.capitulo3Fecha = capitulo3Fecha;
    }

    public String getConclusionFecha() {
        return conclusionFecha;
    }

    public void setConclusionFecha(String conclusionFecha) {
        this.conclusionFecha = conclusionFecha;
    }

    public Boolean getCustom() {
        return isCustom;
    }

    public void setCustom(Boolean custom) {
        isCustom = custom;
    }
}
