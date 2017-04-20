package mitso.volodymyr.tryretrofit.api.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import mitso.volodymyr.tryretrofit.api.Api;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.models.Album;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.models.Photo;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.models.Todo;
import mitso.volodymyr.tryretrofit.models.User;
import mitso.volodymyr.tryretrofit.support.Support;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostObjectTask extends AsyncTask<Void, Void, Object> {

    public String               LOG_TAG = Constants.POST_OBJECT_TASK_LOG_TAG;

    public interface Callback{

        void onSuccess(Object _result);
        void onFailure(Throwable _error);
    }

    private int                 mFragmentType;
    private Object              mObjectPost;
    private Object              mObjectResponse;
    private Callback            mCallback;
    private Exception           mException;
    private Support             mSupport;
    private ProgressDialog      mProgressDialog;

    public PostObjectTask(Context _context, int _fragmentType, Object _objectPost) {

        this.mFragmentType = _fragmentType;
        this.mObjectPost = _objectPost;
        this.mSupport = new Support();
        this.mProgressDialog = new ProgressDialog(_context);
    }

    public void setCallback(Callback _callback) {

        if (mCallback == null)
            mCallback = _callback;
    }

    public void releaseCallback() {

        if (mCallback != null)
            mCallback = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mSupport.initProgressDialog(mProgressDialog);
    }

    @Override
    protected List<Object> doInBackground(Void... _voids) {

        try {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final Api api = retrofit.create(Api.class);

            if (mFragmentType == Constants.FRAGMENT_TYPE_USER_LIST || mFragmentType == Constants.FRAGMENT_TYPE_USER_INFO) {

                final User user = (User) mObjectPost;

                final Call<User> call = api.postUser(user);
                final Response<User> response = call.execute();

                mObjectResponse = response.body();
                return null;
            }

            if (mFragmentType == Constants.FRAGMENT_TYPE_TODO_LIST) {

                final Todo todo = (Todo) mObjectPost;

                final Call<Todo> call = api.postTodo(todo);
                final Response<Todo> response = call.execute();

                mObjectResponse = response.body();
                return null;
            }

            if (mFragmentType == Constants.FRAGMENT_TYPE_ALBUM_LIST) {

                final Album album = (Album) mObjectPost;

                final Call<Album> call = api.postAlbum(album);
                final Response<Album> response = call.execute();

                mObjectResponse = response.body();
                return null;
            }

            if (mFragmentType == Constants.FRAGMENT_TYPE_POST_LIST || mFragmentType == Constants.FRAGMENT_TYPE_POST_INFO) {

                final Post post = (Post) mObjectPost;

                final Call<Post> call = api.postPost(post);
                final Response<Post> response = call.execute();

                mObjectResponse = response.body();
                return null;
            }

            if (mFragmentType == Constants.FRAGMENT_TYPE_PHOTO_LIST) {

                final Photo photo = (Photo) mObjectPost;

                final Call<Photo> call = api.postPhoto(photo);
                final Response<Photo> response = call.execute();

                mObjectResponse = response.body();
                return null;
            }

            if (mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_LIST || mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_INFO) {

                final Comment comment = (Comment) mObjectPost;

                final Call<Comment> call = api.postComment(comment);
                final Response<Comment> response = call.execute();

                mObjectResponse = response.body();
                return null;
            }

        } catch (Exception _error) {

            _error.printStackTrace();
            mException = _error;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object _object) {
        super.onPostExecute(_object);

        mSupport.dismissProgressDialog(mProgressDialog);

        if (mCallback != null) {
            if (mException == null)
                mCallback.onSuccess(mObjectResponse);
            else
                mCallback.onFailure(mException);
        }
    }
}