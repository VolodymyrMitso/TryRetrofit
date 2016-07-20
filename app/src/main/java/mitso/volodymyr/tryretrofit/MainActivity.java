package mitso.volodymyr.tryretrofit;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.UserListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commitFragment(new UserListFragment(), null);
    }

    public void commitFragment(BaseFragment _baseFragment, Bundle _bundle) {

        _baseFragment.setArguments(_bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fl_container, _baseFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        final BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container);
        baseFragment.onBackPressed();
    }
}

//        final Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//                final IConnection connection = retrofit.create(IConnection.class);
//                final Call<List<User>> call = connection.getAllUsers();
//                Response<List<User>> response = null;
//                try {
//                    response = call.execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (response != null) {
//                    final List<User> userList = response.body();
//                    for (int i = 0; i < userList.size(); i++)
//                        Log.i(LOG_TAG, userList.get(i).toString());
//                }

//                final IConnection connection = retrofit.create(IConnection.class);
//                final Call<User> call = connection.getUserById(7);
//                Response<User> response = null;
//                try {
//                    response = call.execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (response != null) {
//                    final User user = response.body();
//                    Log.i(LOG_TAG, user.toString());
//                }