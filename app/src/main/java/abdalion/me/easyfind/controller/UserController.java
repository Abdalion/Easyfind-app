package abdalion.me.easyfind.controller;

import java.util.ArrayList;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.data.UserDAO;
import abdalion.me.easyfind.model.User;

/**
 * Created by Egon on 30/4/2017.
 */

public class UserController {

    public void getObservedUsersID(final Listener<ArrayList<String>> followedUsersID) {
        UserDAO userDAO = new UserDAO();
        userDAO.getObservedUsersID(new Listener<ArrayList<String>>() {
            @Override
            public void update(ArrayList<String> followedUsersIDList) {
                followedUsersID.update(followedUsersIDList);
            }
        });
    }

    public void observeUser(final Listener<User> userListener, String userID) {
        final UserDAO userDAO = new UserDAO();
        userDAO.observeUser(new Listener<User>() {
            @Override
            public void update(User user) {
                userListener.update(user);
            }
        }, userID);
    }

    public void updateMyPosition() {
        final UserDAO userDAO = new UserDAO();
        userDAO.updateMyPosition();
    }

    public void getUsersObservingMe() {
        final UserDAO userDAO = new UserDAO();
        userDAO.getUsersObservingMe();
    }

    public void getClientImage() {
        final UserDAO userDAO = new UserDAO();
        userDAO.getClientImage();
    }
}
