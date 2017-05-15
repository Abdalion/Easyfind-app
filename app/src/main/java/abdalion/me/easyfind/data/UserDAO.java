package abdalion.me.easyfind.data;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import java.io.Console;
import java.io.IOException;
import java.io.StringReader;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.model.User;
import abdalion.me.easyfind.services.ServiceFactory;
import abdalion.me.easyfind.services.UsersService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Egon on 30/4/2017.
 */

public class UserDAO {

    private static UsersService sUserService;

    public UserDAO() {
        sUserService = ServiceFactory.getUsersService();
    }

    public void getObservedUsersMail(final Listener<ArrayList<String>> listener) {

        //Buscar en archivo local los mails
        //get todos los usuarios de Api/User -- Done
        //New Arraylist de los mails que estan en local y api

        final ArrayList<String> observedUsersMail = new ArrayList<>();

//        sUserService.getUsers().enqueue(new Callback<ArrayList<User>>() {
//            @Override
//            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
//                for(User user: response.body()) {
//                    observedUsersMail.add(user.getMail());
//                }
//                listener.update(observedUsersMail);
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        observedUsersMail.add("egonbush@hotmail.com");
        observedUsersMail.add("testmail@hotmail.com");
        observedUsersMail.add("asddds");
        listener.update(observedUsersMail);
    }

    public void observeUser(final Listener<User> userListener, String userMail) {

        //Runnable cada 3 segundos.

        sUserService.getUser(userMail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new StringReader(result));
                reader.setLenient(true);
                List<LinkedTreeMap> users = gson.fromJson(reader, ArrayList.class);
                ArrayList<User> usersList = new ArrayList();
                JsonObject jsonObject = gson.toJsonTree(users.get(0)).getAsJsonObject();
                User myUser = gson.fromJson(jsonObject, User.class);
                Log.d("USERS", result);
                Log.d("USERS", usersList.toString());
                userListener.update(myUser);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });




//        User user = new User(userMail);
//        if(userMail.equals("asdasd")) {
//            user.setLocation("-34.638068, -58.491400");
//            user.setMail("TestUser1");
//        }
//        else if(userMail.equals("5a00")) {
//            user.setLocation("-34.633294, -58.494746");
//            user.setMail("TestUser2");
//        }
//        else if(userMail.equals("asddds")) {
//            user.setLocation("-34.634068, -58.499400");
//            user.setMail("TestUser3");
//        }
//
//        userListener.update(user);
    }

    public void updateMyPosition() {

        //PUT location

        //todo: call server
    }

    public void getUsersObservingMe() {
        //todo: call server
    }

    public void getClientImage() {
        //todo: call server
    }
}
