package mitso.volodymyr.tryretrofit.api.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.api.IConnection;
import mitso.volodymyr.tryretrofit.models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAllUsersTask extends AsyncTask<Void, Void, List<User>> {

    public String       LOG_TAG = Constants.GET_ALL_USERS_TASK_LOG_TAG;

    public interface Callback{
        void onSuccess(List<User> _result);
        void onFailure(Throwable _error);
    }

    private List<User>          mUserList;
    private Callback            mCallback;
    private Exception           mException;

    public GetAllUsersTask() {
        this.mUserList = new ArrayList<>();
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
    protected List<User> doInBackground(Void... _voids) {

        try {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final IConnection connection = retrofit.create(IConnection.class);
            final Call<List<User>> call = connection.getAllUsers();
            final Response<List<User>> response = call.execute();

            mUserList = response.body();

        } catch (Exception _error) {

            _error.printStackTrace();
            mException = _error;
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<User> _userList) {
        super.onPostExecute(_userList);

        if (mCallback != null) {
            if (mException == null)
                mCallback.onSuccess(mUserList);
            else
                mCallback.onFailure(mException);
        }
    }
}