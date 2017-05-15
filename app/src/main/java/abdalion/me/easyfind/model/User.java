package abdalion.me.easyfind.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Egon on 27/4/2017.
 */

public class User {

    private String mail;
    private String location;
    private String imageUrl;

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
