package abdalion.me.easyfind.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

/**
 * Created by Egon on 27/4/2017.
 */

public class User {
    @Expose
    private String _id;
    @Expose
    private String mail;
    @Expose
    private String location;
    @Expose
    private String imageUrl;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
