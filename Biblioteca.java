/**
 *  Clase Principal de la Biblioteca
 * 
 *  @author José Antonio Pozo González IWC70842@educastur.es
 *          Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {

  Scanner input = new Scanner(System.in);

  private ArrayList<Libro> libros;
  private ArrayList<Usuario> usuarios;
  private ArrayList<Prestamo> prestamos;
  private ArrayList<Prestamo> historicos;

  // Constructor de la clase Biblioteca

  public Biblioteca() {
    libros = new ArrayList<>();
    usuarios = new ArrayList<>();
    prestamos = new ArrayList<>();
    historicos = new ArrayList<>();
  }

  // Método para crear usuarios
  public void altaUsuario() {
    System.out.println("Introduce el DNI del usuario: ");
    String dni = input.next();
    System.out.println("Introduce el nombre del usuario: ");
    String nombre = input.next();
    System.out.println("Escribe el numero de teléfono del usuario: ");
    String telefono = input.next();

    usuarios.add(new Usuario(dni, nombre, telefono));
  }

  // Método para eliminar usuarios del Arraylist
  public void bajaUsuario() {
    System.out.println("Introduce el DNI del usuario que deseas dar de baja: ");
    String dni = input.next();
    int s = buscarDni(dni);
    if (s < 0) {
      System.out.println("El usuario con DNI: " + dni + " no existe en los usuarios de la biblioteca.");
    } else {
      usuarios.remove(s);
      System.out.println("El usuario con dni: " + dni + " ha sido eliminado de la biblioteca.");
    }
  }

  // Método para obtener un listado de los usuarios de la biblioteca
  public void listadoUsuarios() {
    for (Usuario u : usuarios) {
      System.out.println(u.toString());
    }
  }

  // Método para añadir libros a la biblioteca
  public void altaLibro() {
    System.out.println("Introduce el ISBN del libro a añadir: ");
    String isbn = input.next();
    System.out.println("Introduce el Titulo del libro a añadir: ");
    String titulo = input.next();
    System.out.println("Introduce el Autor del libro a añadir: ");
    String autor = input.next();
    System.out.println("Introduce el numero de unidades añadir del ejemplar:");
    int unidades = input.nextInt();
    libros.add(new Libro(isbn, titulo, autor, unidades));
  }

  // Método para eliminar unidades de la bibilioteca
  public void bajaLibro() {
    System.out.println("Introduce el ISBN del libro a dar de baja en la biblioteca:");
    String isbn = input.next();
    int s = buscarLibro(isbn);
    if (s < 0) {
      System.out.println("El libro con ISBN " + isbn + " no existe en la biblioteca.");
    } else {
      libros.remove(s);
      System.out.println("El libro con el ISBN " + isbn + " ha sido eliminado de la biblioteca.");
    }
  }

  // Método para modificar el numero de ejemplares de un libro en la biblioteca
  public void modificarLibro() {
    System.out.println("Introduce el ISBN del libro a modificar el número de unidades en la biblioteca:");
    String isbn = input.next();
    int s = buscarLibro(isbn);
    if (s < 0) {
      System.out.println("\nEl ISBN no es correcto o no existe\n");
    } else {
      System.out.println("\nActualmente hay " + libros.get(s).getUnidades() + " ejemplares de este libro.");
      System.out.println("\nIntroduce el número de ejemplares precedido de + para añadir y - para eliminar: ");
      int cantidad = input.nextInt();
      if (libros.get(s).getUnidades() + cantidad < 0) {
        System.out.println("\nNo se pueden eliminar esa cantidad de ejemplares porque es mayor a los que hay en stock");
      } else {
        libros.get(s).setUnidades(cantidad);
        System.out.println("\nSe ha modificado el número de unidades de " + libros.get(s).getTitulo() + " ahora hay "
            + libros.get(s).getUnidades() + " ejemplares");
      }
    }
  }

  // Método para generar un listado de los libros disponibles
  public void librosDisponibles() {
    for (Libro l : libros) {
      System.out.println(l.toString());
    }
  }

  // Método para a partir de un ISBN obtener el índice de un objeto Libro en el Arraylist
  public int buscarLibro(String isbn) {
    int buscarLibro = -1; // fijamos a -1 para empeazar fuera de los límites del Arraylist y devolver -1
                          // en caso de no haber coincidencia
    int i = 0;
    for (Libro l : libros) {
      if (l.getIsbn().equalsIgnoreCase(isbn)) {
        buscarLibro = i;
        break;
      }
      i++;
    }
    return buscarLibro;
  }

  // Método para a partir de un DNI obtener el índice de un objeto Usuario en el Arraylist
  public int buscarDni(String dni) {
    int buscarDni = -1;// fijamos a -1 para empeazar fuera de los límites del Arraylist y devolver -1
                       // en caso de no haber coincidencia
    int i = 0;
    for (Usuario u : usuarios) {
      if (u.getDni().equalsIgnoreCase(dni)) {
        buscarDni = i;
        break;
      }
      i++;
    }
    return buscarDni;
  }

  // Método para generar un nunevo préstamo
  public void prestarLibro() {
    System.out.println("\nPor favor introduzca el DNI del usuario de la biblioteca: ");
    String dni = input.next();
    int s = buscarDni(dni);
    if (s < 0) {
      System.out.println("\nEl usuario o DNI " + dni + " no existe o es erróneo.");
      prestarLibro();
    } else {
      System.out.println("\nPor favor introduce el ISBN del libro a prestar de la biblioteca:");
      String isbn = input.next();
      int b = buscarLibro(isbn);
      {
        if (b < 0) {
          System.out.println("\nEl ISBN " + isbn + " no existe o es erróneo");
          prestarLibro();
        } else {
          boolean libre = consultaDisponibilidad(libros.get(b)); // Si el libro no está disponible no podemos generar el
                                                                 // préstamo
          boolean duplicado = consultaDuplicado(libros.get(b), dni);// Si el usuario ya tiene este libro no podemos
                                                                    // prestar otro ejemplar

          if (libre && !duplicado) {
            libros.get(b).setUnidades(-1); // Quitamos un ejemplar de los disponibles en la bibilioteca al generar el
                                           // préstamo
            prestamos.add(new Prestamo(usuarios.get(s), libros.get(b)));           
          } else {
            System.out.println(
                "\nNo se puede realizar el prestamo por no haber unidades disponibles del libro o tener el usuario ya un ejemplar del mismo.");
          }

        }
      }
    }
  }

  // Método para consultar la disponibilidad de un libro
  public boolean consultaDisponibilidad(Libro l) {
    if (l.getUnidades() > 0) {
      return true;
    } else {
      return false;
    }
  }

  // Método para consultar si un usuario ya posee un ejemplar de ese libro en préstamo
  public boolean consultaDuplicado(Libro l, String dni) {
    for (Prestamo p : prestamos) {
      if (p.getUsuarioPrest().getDni().equalsIgnoreCase(dni) && p.getLibroPrest().equals(l)) {
        return true;
      }
    }
    return false;
  }

  // Método para saber el índice en el Arraylist de un préstamo en concreto
  public int consultaPrestamo(String dni, String isbn) {
    int buscarPrestamo = -1; // Iniciamos fuera de los límites del Arraylist para la búsqueda
    int i = 0;
    for (Prestamo p : prestamos) {
      if (p.getUsuarioPrest().getDni().equalsIgnoreCase(dni) && p.getLibroPrest().getIsbn().equalsIgnoreCase(isbn)) {
        buscarPrestamo = i;
      }
      i++;
    }
    return buscarPrestamo;
  }

  // Método para devolver un libro prestado
  public void devolverLibro() {
    System.out.println("\nPor favor introduce el el DNI del usuario de la biblioteca: ");
    String dni = input.next();
    System.out.println("\nPor favor introduce el el ISBN del libro a devolver: ");
    String isbn = input.next();
    int s = consultaPrestamo(dni, isbn);
    if (s < 0) {
      System.out.println("\nEl préstamo con DNI " + dni + " e isbn " + isbn + " no existe en los prestamos actuales.");
    } else {
      int b = buscarLibro(isbn);
      libros.get(b).setUnidades(+1);// AÑADIMOS UN LIBRO A LA CANTIDAD DISPONIBLE
      // Indicamos los ejemplares disponibles del libro tras la devolución
      System.out.println("\nTras la devolución hay disponibles " + libros.get(b).getUnidades() + " unidades de "
          + libros.get(b).getTitulo() + " para prestar.");
      // Indicamos el tiempo transcurrido desde que se realizó el préstamos hasta su
      // devolución
      System.out
          .println("\nHan transcurrido " + prestamos.get(s).tiempoTranscurrido() + " días desde la fecha del prestamo");
      System.out.println("\nSe ha sobrepasado el límite de fecha para la devolucion en "
          + prestamos.get(s).devolucionEnTiempo() + " dias.");
      // Añadimos el prestamo al histórico y lo eliminamos de los préstamos activos
      historicos.add(new Prestamo(usuarios.get(s), libros.get(b)));
      prestamos.remove(s);
    }
  }

  // Método para prorrogar un préstamo
  public void prorrogaLibro() {
    System.out.println("\nPor favor introduce el el DNI del usuario de la biblioteca: ");
    String dni = input.next();
    System.out.println("\nPor favor introduce el el ISBN del libro a prorrogar: ");
    String isbn = input.next();
    int s = consultaPrestamo(dni, isbn);
    if (s < 0) {
      System.out.println("\nEl prestamo con dni de usuario " + dni + " o isbn de libro " + isbn + " no existe.");
    } else {
      prestamos.get(s).prorrogaFechaDev();
      System.out.println("\nLa nueva fecha de devolución es: " + prestamos.get(s).getFechaDev());
    }
  }

  // Método para listar todos los prestamos
  public void listadoPrestamos() {
    for (Prestamo p : prestamos) {
      System.out.println(p.toString());
    }
  }

  // Método para listar los libros prestados a un usuario concreto
  public void prestamosUsuario() {
    System.out.println("\nPor favor introduce el el DNI del usuario de la biblioteca: ");
    String dni = input.next();
    int s = buscarDni(dni);
    if (s < 0) {
      System.out.println("\nEl usuario o DNI " + dni + " no existe o es erróneo.");      
    } else {
      System.out.println("\nEl usuario tiene en prestamo los siguientes libros:");
      for (Prestamo p : prestamos) {
        if (p.getUsuarioPrest().getDni().equalsIgnoreCase(dni)) {
        System.out.println(p.getLibroPrest().toString());
        }
      }
    }
  }

  // Método para listar los prestamos activos de un libro en concreto
  public void prestamosLibro() {
    System.out.println("\nPor favor introduce el el ISBN del libro a consultar: ");
    String isbn = input.next();
    int s = buscarLibro(isbn);
    if (s < 0) {
      System.out.println("\nEl ISBN no es correcto o no existe\n");
    } else {
      System.out.println("\nEste libro actualmente esta prestado a:");
      for (Prestamo p : prestamos) {
        if (p.getLibroPrest().getIsbn().equalsIgnoreCase(isbn)) {
        System.out.println(p.getUsuarioPrest().toString());
        }
      }  
    }
  }

  // Método para obtener el historico de prestamos para un libro en concreto.
  public void historicoLibro() {
    System.out.println("\nPor favor introduce el el ISBN del libro a consultar: ");
    String isbn = input.next();
    int s = buscarLibro(isbn);
    if (s < 0) {
      System.out.println("\nEl ISBN no es correcto o no existe\n");
    } else {
      System.out.println("\nEl historico usuarios que han tenido en prestamo este libro es el siguiente:");
      for (Prestamo p : historicos) {
        if (p.getLibroPrest().getIsbn().equalsIgnoreCase(isbn)) {        
          System.out.println(p.getFechaPrest().toString());
          System.out.println(p.getUsuarioPrest().toString());
        }
      }
    }
  }

  // Métodos para la interface de los Menús de usuarios

  // MENÚ PRINCIPAL

  public void menu() {
    int seleccion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\tMENU DE OPCIONES BIBLIOTECA\n");
      System.out.println("\t\t\t\t1.GESTION DE USUARIOS/AS\n");
      System.out.println("\t\t\t\t2.GESTION DE LIBROS\n");
      System.out.println("\t\t\t\t3.GESTION DE PRESTAMOS / DEVOLUCIONES\n");
      System.out.println("\t\t\t\t9.SALIR\n");
      seleccion = input.nextInt();
      switch (seleccion) {
        case 1: {
          menuUsuarios();
          break;
        }
        case 2: {
          menuLibros();
          break;
        }
        case 3: {
          menuPrestamos();
          break;
        }
      }
    } while (seleccion != 9);
  }

  // MENÚ USUARIOS

  public void menuUsuarios() {
    int seleccion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\tGESTION DE USUARIOS/AS\n");
      System.out.println("\t\t\t\t1.ALTA NUEVO USUARIOS/AS\n");
      System.out.println("\t\t\t\t2.BAJA USUARIOS/AS\n");
      System.out.println("\t\t\t\t3.LISTADO DE USUARIOS/AS\n");
      System.out.println("\t\t\t\t9.VOLVER\n");
      seleccion = input.nextInt();
      switch (seleccion) {
        case 1: {
          altaUsuario();
          break;
        }
        case 2: {
          bajaUsuario();
          break;
        }
        case 3: {
          listadoUsuarios();
          break;
        }
      }
    } while (seleccion != 9);
  }

  // MENÚ LIBROS

  public void menuLibros() {
    int seleccion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\tGESTION DE LIBROS\n");
      System.out.println("\t\t\t\t1.ALTA NUEVO LIBRO\n");
      System.out.println("\t\t\t\t2.BAJA LIBRO\n");
      System.out.println("\t\t\t\t3.MODIFICACION DATOS LIBRO\n");
      System.out.println("\t\t\t\t4.LISTADO DE LIBROS DISPONIBLES\n");
      System.out.println("\t\t\t\t9.VOLVER\n");
      seleccion = input.nextInt();
      switch (seleccion) {
        case 1: {
          altaLibro();
          break;
        }
        case 2: {
          bajaLibro();
          break;
        }
        case 3: {
          modificarLibro();
          break;
        }
        case 4: {
          librosDisponibles();
          break;
        }
      }
    } while (seleccion != 9);
  }

  // MENÚ PRESTAMOS

  public void menuPrestamos() {
    int seleccion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\tGESTION DE PRESTAMOS\n");
      System.out.println("\t\t\t\t1.PRESTAMOS\n");
      System.out.println("\t\t\t\t2.DEVOLUCIONES\n");
      System.out.println("\t\t\t\t3.PRORROGAS\n");
      System.out.println("\t\t\t\t4.LISTADO DE PRESTAMOS (TODOS)\n");
      System.out.println("\t\t\t\t5.LISTADO DE PRESTAMOS DE UN USUARIO ACTUALMENTE\n");
      System.out.println("\t\t\t\t6.LISTADO DE PRESTAMOS ACTIVOS PARA UN LIBRO\n");
      System.out.println("\t\t\t\t7.LISTADO DE PRESTAMOS HISTORICOS DE UN LIBRO\n");
      System.out.println("\t\t\t\t9.VOLVER\n");
      seleccion = input.nextInt();
      switch (seleccion) {
        case 1: {
          prestarLibro();
          break;
        }
        case 2: {
          devolverLibro();
          break;
        }
        case 3: {
          prorrogaLibro();
          break;
        }
        case 4: {
          listadoPrestamos();
          break;
        }
        case 5: {
          prestamosUsuario();
          break;
        }
        case 6: {
          prestamosLibro();
          break;
        }
        case 7: {
          historicoLibro();//En la sección devolución libro añadimos al histórico
          break;
        }
      }
    } while (seleccion != 9);
  }

  // Método principal del progama
  public static void main(String[] args) {
    Biblioteca b = new Biblioteca();
    b.cargaDatos();
    b.menu();
  }

  /*
   * Metodo para cargar datos y realizar test de la tarea.
   * Se debería eliminar para seguir las prácticas de código limpio
   * al concluir los test.
   */

  public void cargaDatos() {
    libros.add(new Libro("1-11", "El Hobbit", "JRR Tolkien", 3));
    libros.add(new Libro("1-22", "El Silmarillon", "JRR Tolkien", 3));
    libros.add(new Libro("1-33", "El Médico", "N. Gordon", 4));
    libros.add(new Libro("1-44", "Chamán", "N. Gordon", 3));
    libros.add(new Libro("1-55", "Momo", "M. Ende", 2));
    libros.add(new Libro("1-66", "Paraíso inhabitado", "A.M.Matute", 2));
    libros.add(new Libro("1-77", "Olvidado Rey Gudú", "A.M.Matute", 2));
    libros.add(new Libro("1-88", "El último barco", "D.Villar", 3));
    libros.add(new Libro("1-99", "Ojos de agua", "D.Villar", 2));
    usuarios.add(new Usuario("11", "Ana", "621111111"));
    usuarios.add(new Usuario("22", "David", "622222222"));
    usuarios.add(new Usuario("33", "Bea", "623333333"));
    usuarios.add(new Usuario("44", "Lucas", "624444444"));
    usuarios.add(new Usuario("55", "Carlota", "625555555"));
    usuarios.add(new Usuario("66", "Juan", "626666666"));
  }
}
