package mitso.volodymyr.tryretrofit.api.tasks;

import android.os.AsyncTask;

import java.util.List;

import mitso.volodymyr.tryretrofit.api.IConnection;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetObjectTask extends AsyncTask<Void, Void, Object> {

    public String               LOG_TAG = Constants.GET_OBJECT_TASK_LOG_TAG;

    public interface Callback{

        void onSuccess(Object _result);
        void onFailure(Throwable _error);
    }

    private int                 mObjectType;
    private int                 mObjectId;
    private Object              mObject;
    private Callback            mCallback;
    private Exception           mException;

    public GetObjectTask(int _objectType, Integer _objectId) {

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

            if (mObjectType == Constants.OBJECT_TYPE_USER) {

                final Call<User> call = connection.getUserById(mObjectId);
                final Response<User> response = call.execute();

                mObject = response.body();

            } else if (mObjectType == Constants.OBJECT_TYPE_POST) {

                final Call<Post> call = connection.getPostById(mObjectId);
                final Response<Post> response = call.execute();

                mObject = response.body();

        } else if (mObjectType == Constants.OBJECT_TYPE_COMMENT) {

                final Call<Comment> call = connection.getCommentById(mObjectId);
                final Response<Comment> response = call.execute();

                mObject = response.body();
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

        if (mCallback != null) {
            if (mException == null)
                mCallback.onSuccess(mObject);
            else
                mCallback.onFailure(mException);
        }
    }
}