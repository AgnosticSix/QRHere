package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Events {
    private int id;
    private String evento;
    private String descripcion;
    private String cupo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCupo() { return cupo; }

    public void setCupo(String cupo) { this.cupo = cupo; }

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("evento", getEvento());
            jsonObject.put("descripcion", getDescripcion());
            jsonObject.put("cupo", getCupo());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }
}
