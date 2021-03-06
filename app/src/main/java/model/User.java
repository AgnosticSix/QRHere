package model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mateo on 20/03/2018.
 */

public class User {
    private int id;
    private String name;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("username", getName());
            jsonObject.put("password", getPassword());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }
}
