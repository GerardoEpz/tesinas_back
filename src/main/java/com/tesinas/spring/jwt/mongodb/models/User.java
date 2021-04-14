package com.tesinas.spring.jwt.mongodb.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class User {
  @Id
  private String id;

  @NotBlank
  @Size(max = 20)
  private String username; //username = matricula

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private String grupo;

  @NotBlank
  @Size(max = 120)
  private String password;

  @NotBlank
  private String name;

  private String period;

  private String year;

  @DBRef
  private Cronogram cronograma;

  @DBRef
  private Tesinas tesina;

  @DBRef
  private User asesor;

  private String statusPago;

  private String aproboCurso;

  private String empresa;

  private String calificacionTesina;

  private String calificacionPresentacion;

  private String calificacionEmpresa;

  private String locacionEmpresa;

  private String observaciones;

  private String conTrabajo;



  @DBRef
  private Set<Role> roles = new HashSet<>();

  public User() {

  }

  public User(String username, String email, String name, String grupo, String password) {
    this.username = username;
    this.email = email;
    this.name = name;
    this.grupo = grupo;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPeriod() { return period; }

  public void setPeriod(String period) {
    this.period = period;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public Cronogram getCronograma() {
    return cronograma;
  }

  public void setCronograma(Cronogram cronograma) {
    this.cronograma = cronograma;
  }

  public Tesinas getTesina() {
    return tesina;
  }

  public void setTesina(Tesinas tesina) {
    this.tesina = tesina;
  }

  public User getAsesor() {
    return asesor;
  }

  public void setAsesor(User asesor) {
    this.asesor = asesor;
  }

  public String getStatusPago() {
    return statusPago;
  }

  public void setStatusPago(String statusPago) {
    this.statusPago = statusPago;
  }

  public String getAproboCurso() {
    return aproboCurso;
  }

  public void setAproboCurso(String aproboCurso) {
    this.aproboCurso = aproboCurso;
  }

  public String getEmpresa() {
    return empresa;
  }

  public void setEmpresa(String empresa) {
    this.empresa = empresa;
  }

  public String getLocacionEmpresa() {
    return locacionEmpresa;
  }

  public void setLocacionEmpresa(String locacionEmpresa) {
    this.locacionEmpresa = locacionEmpresa;
  }

  public String getConTrabajo() {
    return conTrabajo;
  }

  public void setConTrabajo(String conTrabajo) {
    this.conTrabajo = conTrabajo;
  }

  public String getGrupo() { return grupo; }

  public void setGrupo(String grupo) { this.grupo = grupo; }

  public String getCalificacionTesina() { return calificacionTesina; }

  public void setCalificacionTesina(String calificacionTesina) { this.calificacionTesina = calificacionTesina; }

  public String getCalificacionPresentacion() { return calificacionPresentacion; }

  public void setCalificacionPresentacion(String calificacionPresentacion) { this.calificacionPresentacion = calificacionPresentacion; }

  public String getCalificacionEmpresa() { return calificacionEmpresa; }

  public void setCalificacionEmpresa(String calificacionEmpresa) { this.calificacionEmpresa = calificacionEmpresa; }

  public String getObservaciones() { return observaciones; }

  public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
