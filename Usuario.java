/**
 * Clase donde se define el objeto Usuario de la biblioteca
 * 
 * @author José Antonio Pozo González IWC70842@educastur.es
 *         Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */

public class Usuario {
  private String dni;
  private String nombre;
  private String telefono;

  // Constructor de la clase

  public Usuario(String dni, String nombre, String telefono) {
    this.dni = dni;
    this.nombre = nombre;
    this.telefono = telefono;
  }

  // Metodos Getters y Setter de la clase

  public String getDni() {
    return dni;
  }

  public String getNombre() {
    return nombre;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  // Sobrescritura del método toString
  @Override
  public String toString() {
    return "Usuario: \nDNI: " + getDni() + " Nombre: " + getNombre() + " Teléfono: " + getTelefono();
  }

}
