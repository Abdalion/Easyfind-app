package abdalion.me.easyfind.services;

import java.util.ArrayList;
import java.util.List;

import abdalion.me.easyfind.model.ListadoUser;
import abdalion.me.easyfind.model.User;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static android.R.attr.path;


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
    @POST("users/")
    Call<User> createUser(@Body User user);

    @PUT("users/{id}")
    Call<User> updateLocation(@Path("id") String id, @Body User user);


}
