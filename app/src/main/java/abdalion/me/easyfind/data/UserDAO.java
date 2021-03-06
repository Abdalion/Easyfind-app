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
                t.printStackTrace();
            }
        });
    }

    public void observeUser(String userMail, final Listener<User> userListener) {

        sUserService.getUser(userMail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    result = null;
                    e.printStackTrace();
                }

                userListener.update(parseUser(result));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateMyPosition(User user) throws IOException {

        //PUT location
        sUserService.updateLocation(user.get_id(), user).execute().body();

        Log.d("REACHED", user.get_id() + user.getLocation());

        //todo: call server
    }

    public void getUsersObservingMe() {
        //todo: call server
    }

    public void getClientImage() {
        //todo: call server
    }

    private User parseUser(String json) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        List<LinkedTreeMap> users = gson.fromJson(reader, ArrayList.class);
        JsonObject jsonObject = gson.toJsonTree(users.get(0)).getAsJsonObject();
        User myUser = gson.fromJson(jsonObject, User.class);
        return myUser;
    }
}
