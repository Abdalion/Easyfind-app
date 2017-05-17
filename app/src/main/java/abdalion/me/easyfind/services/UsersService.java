package abdalion.me.easyfind.services;

import java.util.ArrayList;
import java.util.List;

import abdalion.me.easyfind.model.ListadoUser;
import abdalion.me.easyfind.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Egon on 14/5/2017.
 */

public interface UsersService {

    String BASE_URL = "http://easyfind-server.herokuapp.com/api/";

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users")
    Call<ResponseBody> getUser(@Query("mail") String mail);

    @Headers("Content-Type: application/json")
    @POST("users")
    Call<User> createUser(@Body User user);

    @PUT("users")
    Call<String> updateLocation(@Query("mail") String mail);


}
