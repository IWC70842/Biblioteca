/**
 * Clase donde se define el objeto Préstamo  por parte de un Usuario de un Libro de la Biblioteca
 * 
 * @author José Antonio Pozo González IWC70842@educastur.es
 *         Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class Prestamo {
  private Usuario usuarioPrest;
  private Libro libroPrest;
  private LocalDate fechaPrest;
  private LocalDate fechaDev;

  // Constructor de la clase Prestamo

  public Prestamo(Usuario usuarioPrest, Libro libroPrest) {
    this.usuarioPrest = usuarioPrest;
    this.libroPrest = libroPrest;
    this.fechaPrest = LocalDate.now();
    this.fechaDev = fechaPrest.plusDays(15);
  }

  // Métodos Getters y Setters de la clase

  public Usuario getUsuarioPrest() {
    return usuarioPrest;
  }

  public Libro getLibroPrest() {
    return libroPrest;
  }

  public LocalDate getFechaPrest() {
    return fechaPrest;
  }

  public LocalDate getFechaDev() {
    return fechaDev;
  }

  public void setUsuarioPrest(Usuario usuarioPrest) {
    this.usuarioPrest = usuarioPrest;
  }

  public void setLibroPrest(Libro libroPrest) {
    this.libroPrest = libroPrest;
  }

  public void setFechaPrest(LocalDate fechaPrest) {
    this.fechaPrest = fechaPrest;
  }

  public void setFechaDev(LocalDate fechaDev) {
    this.fechaDev = fechaDev;
  }

  // Métodos Auxiliares

  // Prorrogar fecha devolución

  public void prorrogaFechaDev() {
    this.fechaDev = fechaDev.plusDays(15);
  }

  // Tiempo transcurrido desde el prestamo

  public long tiempoTranscurrido() {
    long dias = DAYS.between(fechaPrest, LocalDate.now());
    return dias;
  }

  // Tiempo hasta la fecha de devolución

  public long devolucionEnTiempo() {
    long dias = DAYS.between(fechaDev, LocalDate.now());
    return dias;
  }

  // Sobreescritura del método toString
  @Override
  public String toString() {
    return "\n" + getUsuarioPrest() + "\n" + getLibroPrest() + "\nFecha de préstamo: "
        + getFechaPrest() + "\nFecha en la que se debe realizar la devoulición: " + getFechaDev();

  }

}
