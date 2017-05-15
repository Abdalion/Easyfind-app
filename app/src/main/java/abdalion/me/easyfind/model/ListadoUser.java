package abdalion.me.easyfind.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Egon on 14/5/2017.
 */

public class ListadoUser {

    @SerializedName("")
    private List<User> usuarios;

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }
}
