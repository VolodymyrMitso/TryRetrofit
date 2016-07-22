package mitso.volodymyr.tryretrofit.fragments.infos;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mitso.volodymyr.tryretrofit.R;
import mitso.volodymyr.tryretrofit.api.tasks.GetObjectTask;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.FragmentUserInfoBinding;
import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.AlbumListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.PostListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.TodoListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.UserListFragment;
import mitso.volodymyr.tryretrofit.models.User;
import mitso.volodymyr.tryretrofit.support.Support;

public class UserInfoFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.USER_INFO_FRAGMENT_LOG_TAG;

    private Support                         mSupport;

    private FragmentUserInfoBinding         mBinding;

    private int                             mUserId;
    private int[]                           mIdArray;
    private boolean                         isIdArrayNull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_user_info, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "USER INFO FRAGMENT IS CREATED.");

        mSupport = new Support();

        iniActionBar();

        receiveIdArray();

        if (mSupport.checkNetworkConnection(mMainActivity))
            if (!isIdArrayNull)
                getUserById();
            else
                mSupport.showToastError(mMainActivity);
        else
            mSupport.showToastNoConnection(mMainActivity);

        return rootView;
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_user_info));
    }

    private void receiveIdArray() {

        try {
            mIdArray = getArguments().getIntArray(Constants.ID_ARRAY_BUNDLE_KEY);
            if (mIdArray == null)
                throw new NullPointerException();

            mUserId = mIdArray[0];

            isIdArrayNull = false;
            Log.i(LOG_TAG, "USER ID IS RECEIVED: " + String.valueOf(mUserId) + ".");

        } catch (NullPointerException _error) {

            isIdArrayNull = true;
            Log.e(LOG_TAG, "ID ARRAY IS NOT RECEIVED. ID ARRAY IS NULL.");
            _error.printStackTrace();
        }
    }

    public void getUserById() {

        final GetObjectTask getObjectTask = new GetObjectTask(Constants.OBJECT_TYPE_USER, mUserId);
        getObjectTask.setCallback(new GetObjectTask.Callback() {
            @Override
            public void onSuccess(Object _result) {

                Log.i(getObjectTask.LOG_TAG, "ON SUCCESS: USER.");

                final User user = (User) _result;
                mBinding.setUser(user);

                initButtons();

                getObjectTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.i(getObjectTask.LOG_TAG, "ON FAILURE: ERROR.");
                _error.printStackTrace();

                mSupport.showToastError(mMainActivity);

                getObjectTask.releaseCallback();
            }
        });
        getObjectTask.execute();
    }

    private void initButtons() {

        final Bundle bundle = new Bundle();
        bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, new int[] { mUserId });

        mBinding.setClickerTodos(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActivity.commitFragment(new TodoListFragment(), bundle);
            }
        });

        mBinding.setClickerAlbums(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActivity.commitFragment(new AlbumListFragment(), bundle);
            }
        });

        mBinding.setClickerPosts(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActivity.commitFragment(new PostListFragment(), bundle);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mMainActivity.commitFragment(new UserListFragment(), null);
    }

    @Override
    public void onOptionsItemSelected() {
        super.onOptionsItemSelected();

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.FRAGMENT_TYPE_BUNDLE_KEY, Constants.FRAGMENT_TYPE_USER_INFO);
        bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, mIdArray);
        mMainActivity.commit10thFragment(bundle);
    }
}
