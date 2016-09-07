package mitso.volodymyr.tryretrofit.api;

import java.util.List;

import mitso.volodymyr.tryretrofit.models.Album;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.models.Photo;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.models.Todo;
import mitso.volodymyr.tryretrofit.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/users")
    Call<List<User>> getAllUsers();

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("/posts/{id}")
    Call<Post> getPostById(@Path("id") int id);

    @GET("/comments/{id}")
    Call<Comment> getCommentById(@Path("id") int id);

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("/users/{userId}/todos")
    Call<List<Todo>> getTodosByUserId(@Path("userId") int userId);

    @GET("/users/{userId}/albums")
    Call<List<Album>> getAlbumsByUserId(@Path("userId") int userId);

    @GET("/users/{userId}/posts")
    Call<List<Post>> getPostsByUserId(@Path("userId") int userId);

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("/photos") // photos?albumId=1
    Call<List<Photo>> getPhotosByAlbumId(@Query("albumId") int albumId);

    @GET("/comments") // comments?postId=1
    Call<List<Comment>> getCommentsByPostId(@Query("postId") int postId);

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @POST("/users")
    Call<User> postUser(@Body User user);

    @POST("/todos")
    Call<Todo> postTodo(@Body Todo todo);

    @POST("/albums")
    Call<Album> postAlbum(@Body Album album);

    @POST("/posts")
    Call<Post> postPost(@Body Post post);

    @POST("/photos")
    Call<Photo> postPhoto(@Body Photo photo);

    @POST("/comments")
    Call<Comment> postComment(@Body Comment comment);
}