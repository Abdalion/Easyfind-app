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

        sUserService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for(User user: response.body()) {
                    observedUsersMail.add(user.getMail());
                }
                listener.update(observedUsersMail);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

//        observedUsersMail.add("egonbush@hotmail.com");
//        observedUsersMail.add("testmail@hotmail.com");
//        observedUsersMail.add("asddds");
    }

    public void observeUser(final Listener<User> userListener, String userMail) {


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
                t.printStackTrace();
            }
        });
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
