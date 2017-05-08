package abdalion.me.easyfind.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.model.User;

/**
 * Created by Egon on 30/4/2017.
 */

public class UserDAO {

    public void getObservedUsersID(Listener<ArrayList<String>> listener) {
        ArrayList<String> followedUsersId = new ArrayList<>();
        followedUsersId.add("150");
        followedUsersId.add("500");
        followedUsersId.add("123");

        listener.update(followedUsersId);
    }

    public void observeUser(Listener<User> userListener, String userID) {
        User user = new User(userID);
        if(userID.equals("150")) {
            user.setLocation(new LatLng(-34.638068, -58.491400));
            user.setName("TestUser1");
        }
        else if(userID.equals("500")) {
            user.setLocation(new LatLng(-34.633294, -58.494746));
            user.setName("TestUser2");
        }
        else if(userID.equals("123")) {
            user.setLocation(new LatLng(-34.634068, -58.499400));
            user.setName("TestUser3");
        }

        userListener.update(user);
    }

    public void updateMyPosition() {
        //todo: call server
    }

    public void getUsersObservingMe() {
        //todo: call server
    }

    public void getClientImage() {
        //todo: call server
    }
}
