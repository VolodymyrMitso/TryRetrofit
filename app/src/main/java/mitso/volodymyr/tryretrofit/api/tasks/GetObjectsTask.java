package mitso.volodymyr.tryretrofit.api.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import mitso.volodymyr.tryretrofit.api.IConnection;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.models.Album;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.models.Photo;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.models.Todo;
import mitso.volodymyr.tryretrofit.models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetObjectsTask extends AsyncTask<Void, Void, List<Object>> {

    public String               LOG_TAG = Constants.GET_OBJECTS_TASK_LOG_TAG;

    public interface Callback{

        void onSuccess(List<Object> _result);
        void onFailure(Throwable _error);
    }

    private List<Object>        mObjectList;
    private int                 mObjectType;
    private Integer             mObjectId;
    private Callback            mCallback;
    private Exception           mException;

    public GetObjectsTask(int _objectType, Integer _objectId) {

        this.mObjectList = new ArrayList<>();
        this.mObjectType = _objectType;
        this.mObjectId = _objectId;
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
    protected List<Object> doInBackground(Void... _voids) {

        try {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final IConnection connection = retrofit.create(IConnection.class);

            if (mObjectType == Constants.OBJECT_TYPE_USER && mObjectId == null) {

                final Call<List<User>> call = connection.getAllUsers();
                final Response<List<User>> response = call.execute();

                mObjectList.addAll(response.body());

            } else if (mObjectType == Constants.OBJECT_TYPE_TODO && mObjectId != null) {

                final Call<List<Todo>> call = connection.getTodosByUserId(mObjectId);
                final Response<List<Todo>> response = call.execute();

                mObjectList.addAll(response.body());

            } else if (mObjectType == Constants.OBJECT_TYPE_ALBUM && mObjectId != null) {

                final Call<List<Album>> call = connection.getAlbumsByUserId(mObjectId);
                final Response<List<Album>> response = call.execute();

                mObjectList.addAll(response.body());

            } else if (mObjectType == Constants.OBJECT_TYPE_POST && mObjectId != null) {

                final Call<List<Post>> call = connection.getPostsByUserId(mObjectId);
                final Response<List<Post>> response = call.execute();

                mObjectList.addAll(response.body());

            } else if (mObjectType == Constants.OBJECT_TYPE_PHOTO && mObjectId != null) {

                final Call<List<Photo>> call = connection.getPhotosByAlbumId(mObjectId);
                final Response<List<Photo>> response = call.execute();

                mObjectList.addAll(response.body());

            } else if (mObjectType == Constants.OBJECT_TYPE_COMMENT && mObjectId != null) {

                final Call<List<Comment>> call = connection.getCommentsByPostId(mObjectId);
                final Response<List<Comment>> response = call.execute();

                mObjectList.addAll(response.body());
            }

        } catch (Exception _error) {

            _error.printStackTrace();
            mException = _error;
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Object> _objectList) {
        super.onPostExecute(_objectList);

        if (mCallback != null) {
            if (mException == null)
                mCallback.onSuccess(mObjectList);
            else
                mCallback.onFailure(mException);
        }
    }
}