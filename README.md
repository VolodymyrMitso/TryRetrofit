    @GET("/users")
    Call<List<User>> getAllUsers();

    -------------------------------------------------------------------------------------

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("/posts/{id}")
    Call<Post> getPostById(@Path("id") int id);

    @GET("/comments/{id}")
    Call<Comment> getCommentById(@Path("id") int id);

    -------------------------------------------------------------------------------------

    @GET("/users/{userId}/todos")
    Call<List<Todo>> getTodosByUserId(@Path("userId") int userId);

    @GET("/users/{userId}/albums")
    Call<List<Album>> getAlbumsByUserId(@Path("userId") int userId);

    @GET("/users/{userId}/posts")
    Call<List<Post>> getPostsByUserId(@Path("userId") int userId);

    -------------------------------------------------------------------------------------

    @GET("/photos") // photos?albumId=1
    Call<List<Photo>> getPhotosByAlbumId(@Query("albumId") int albumId);

    @GET("/comments") // comments?postId=1
    Call<List<Comment>> getCommentsByPostId(@Query("postId") int postId);
    
    -------------------------------------------------------------------------------------

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
