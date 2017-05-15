package abdalion.me.easyfind.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Egon on 14/5/2017.
 */

public class ServiceFactory {
    public static UsersService getUsersService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsersService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(UsersService.class);
    }
}
