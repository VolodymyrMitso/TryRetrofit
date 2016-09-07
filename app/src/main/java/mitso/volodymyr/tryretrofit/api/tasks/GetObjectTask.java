package mitso.volodymyr.tryretrofit.api.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import mitso.volodymyr.tryretrofit.api.Api;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.models.User;
import mitso.volodymyr.tryretrofit.support.Support;
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
    private Support             mSupport;
    private ProgressDialog      mProgressDialog;

    public GetObjectTask(Context _context, int _objectType, Integer _objectId) {

        this.mObjectType = _objectType;
        this.mObjectId = _objectId;
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

            if (mObjectType == Constants.OBJECT_TYPE_USER) {

                final Call<User> call = api.getUserById(mObjectId);
                final Response<User> response = call.execute();

                mObject = response.body();

            } else if (mObjectType == Constants.OBJECT_TYPE_POST) {

                final Call<Post> call = api.getPostById(mObjectId);
                final Response<Post> response = call.execute();

                mObject = response.body();

        } else if (mObjectType == Constants.OBJECT_TYPE_COMMENT) {

                final Call<Comment> call = api.getCommentById(mObjectId);
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

        mSupport.dismissProgressDialog(mProgressDialog);

        if (mCallback != null) {
            if (mException == null)
                mCallback.onSuccess(mObject);
            else
                mCallback.onFailure(mException);
        }
    }
}