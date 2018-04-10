package model;

public class AlumnoEvento {
    private int id;
    private String matricula;
    private String nombre;
    private int idevento;
    private String carrera;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdEvento() {
        return idevento;
    }

    public void setIdEvento(int idevento) {
        this.idevento = idevento;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
