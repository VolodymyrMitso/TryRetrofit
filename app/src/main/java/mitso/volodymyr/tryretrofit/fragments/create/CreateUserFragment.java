package mitso.volodymyr.tryretrofit.fragments.create;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import mitso.volodymyr.tryretrofit.R;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.FragmentCreateUserBinding;
import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.infos.CommentInfoFragment;
import mitso.volodymyr.tryretrofit.fragments.infos.PostInfoFragment;
import mitso.volodymyr.tryretrofit.fragments.infos.UserInfoFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.AlbumListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.CommentListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.PhotoListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.PostListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.TodoListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.UserListFragment;

public class CreateUserFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.CREATE_USER_FRAGMENT_LOG_TAG;

    private FragmentCreateUserBinding       mBinding;

    private int                             mFragmentType;
    private int[]                           mIdArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_create_user, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "CREATE USER FRAGMENT IS CREATED.");

        iniActionBar();
        setHasOptionsMenu(true);

        receiveFragmentType();
        receiveIdArray();

        return rootView;
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_create_user));
    }

    @Override
    public void onCreateOptionsMenu(Menu _menu, MenuInflater _inflater) {
        super.onCreateOptionsMenu(_menu, _inflater);

        final MenuItem menuItem = _menu.findItem(R.id.mi_create_user);
        menuItem.setVisible(false);
    }

    private void receiveFragmentType() {

        try {
            mFragmentType = getArguments().getInt(Constants.FRAGMENT_TYPE_BUNDLE_KEY, Constants.FRAGMENT_TYPE_DEFAULT_VALUE);

            if (mFragmentType == Constants.FRAGMENT_TYPE_DEFAULT_VALUE)
                throw new NullPointerException();

            Log.i(LOG_TAG, "FRAGMENT TYPE IS RECEIVED: " + mFragmentType + ".");

        } catch (NullPointerException _error) {

            Log.e(LOG_TAG, "FRAGMENT TYPE IS NOT RECEIVED. FRAGMENT TYPE IS = -1.");
            _error.printStackTrace();
        }
    }

    private void receiveIdArray() {

        try {
            mIdArray = getArguments().getIntArray(Constants.ID_ARRAY_BUNDLE_KEY);

            if (mIdArray == null)
                throw new NullPointerException();

            Log.i(LOG_TAG, "ID ARRAY IS RECEIVED: " + Arrays.toString(mIdArray) + ".");

        } catch (NullPointerException _error) {

            Log.e(LOG_TAG, "ID ARRAY IS NOT RECEIVED. ID ARRAY IS NULL.");
            _error.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mIdArray == null) {

            if (mFragmentType == Constants.FRAGMENT_TYPE_USER_LIST)
                mMainActivity.commitFragment(new UserListFragment(), null);

        } else {

            final Bundle bundle = new Bundle();
            bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, mIdArray);

            if (mFragmentType == Constants.FRAGMENT_TYPE_USER_INFO)
                mMainActivity.commitFragment(new UserInfoFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_TODO_LIST)
                mMainActivity.commitFragment(new TodoListFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_TODO_LIST)
                mMainActivity.commitFragment(new TodoListFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_ALBUM_LIST)
                mMainActivity.commitFragment(new AlbumListFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_POST_LIST)
                mMainActivity.commitFragment(new PostListFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_PHOTO_LIST)
                mMainActivity.commitFragment(new PhotoListFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_POST_INFO)
                mMainActivity.commitFragment(new PostInfoFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_LIST)
                mMainActivity.commitFragment(new CommentListFragment(), bundle);

            if (mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_INFO)
                mMainActivity.commitFragment(new CommentInfoFragment(), bundle);
        }
    }
}
