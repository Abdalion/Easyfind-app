package abdalion.me.easyfind.controller;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.data.UserDAO;
import abdalion.me.easyfind.model.User;

import static abdalion.me.easyfind.utils.Utils.isNull;

/**
 * Created by Egon on 30/4/2017.
 */

public class UserController{

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

    public void listenUser(final String mail, final Listener<User> userListener) {
        final UserDAO userDAO = new UserDAO();
        //Runnable cada 3 sec

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                userDAO.observeUser(mail, new Listener<User>() {
                    @Override
                    public void update(User obj) {
                        userListener.update(obj);
                    }
                });
            }
        }, 0, 4000);
    }

    public void updateMyPosition() {
        final UserDAO userDAO = new UserDAO();
        final User user = new User("egonbush@hotmail.com");
        user.set_id("5918e1ec83ee3c00046bfd91");
        user.setLocation("-34.343257, -58.775284");
        //Runnable cada 3 sec preguntar al dispositivo posicion.
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    userDAO.updateMyPosition(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("REACHED", "Controller");
            }
        },0,4000);

    }

//    public void getUsersObservingMe() {
//        final UserDAO userDAO = new UserDAO();
//        userDAO.getUsersObservingMe();
//    }
//
//    public void getClientImage() {
//        final UserDAO userDAO = new UserDAO();
//        userDAO.getClientImage();
//    }
}
