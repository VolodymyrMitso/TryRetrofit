package mitso.volodymyr.tryretrofit.api;

import java.util.List;

import mitso.volodymyr.tryretrofit.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IConnection {

    @GET("users/")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);
}
