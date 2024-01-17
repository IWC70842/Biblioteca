/**
 * Clase donde se define el objeto Libro de la biblioteca
 * 
 * @author José Antonio Pozo González IWC70842@educastur.es
 *         Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */

public class Libro {
  private String isbn;
  private String titulo;
  private String autor;
  private int unidades;

  // Constructor Clase Libro

  public Libro(String isbn, String titulo, String autor, int unidades) {
    this.isbn = isbn;
    this.titulo = titulo;
    this.autor = autor;
    this.unidades = unidades;
  }

  // Métodos Getters y Setters de la Clase

  public String getIsbn() {
    return isbn;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getAutor() {
    return autor;
  }

  public int getUnidades() {
    return unidades;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public void setUnidades(int cantidad) {
    this.unidades = unidades + cantidad;
  }

  // Sobreescritura del método toString

  @Override
  public String toString() {
    return "Libro: \nISBN: " + getIsbn() + " Titulo: " + getTitulo() + " Autor: " + getAutor()
        + " Unidades disponibles: " + getUnidades();
  }
}
