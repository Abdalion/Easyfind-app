package abdalion.me.easyfind.controller;

import java.util.ArrayList;
import java.util.List;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.data.UserDAO;
import abdalion.me.easyfind.model.User;

import static abdalion.me.easyfind.utils.Utils.isNull;

/**
 * Created by Egon on 30/4/2017.
 */

public class UserController {

    //Buscar en archivo local los mails
    //get todos los usuarios de Api/User -- Done
    //New Arraylist de los mails que estan en local y api

    public void getObservedUsersMail(final Listener<List<String>> followedUsersMail) {
        UserDAO userDAO = new UserDAO();
        userDAO.getObservedUsersMail(new Listener<ArrayList<String>>() {
            @Override
            public void update(ArrayList<String> followedUsersMailList) {
                followedUsersMail.update(followedUsersMailList);
            }
        });
    }

    public void loadUser(String mail, final Listener<User> userListener) {
        //Runnable cada 3 sec

        final UserDAO userDAO = new UserDAO();
        if(!isNull(mail)) {
            userDAO.observeUser(mail, new Listener<User>() {
                @Override
                public void update(User obj) {
                    userListener.update(obj);
                }
            });
        }
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
