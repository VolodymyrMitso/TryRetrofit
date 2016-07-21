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
import mitso.volodymyr.tryretrofit.databinding.FragmentPostInfoBinding;
import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.CommentListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.PostListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.UserListFragment;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.support.Support;

public class PostInfoFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.POST_INFO_FRAGMENT_LOG_TAG;

    private Support                         mSupport;

    private FragmentPostInfoBinding         mBinding;

    private Integer                         mPostId;
    private boolean                         isPostIdNull;
    private Integer                         mUserId;
    private boolean                         isUserIdNull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_post_info, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "POST INFO FRAGMENT IS CREATED.");

        mSupport = new Support();

        iniActionBar();

        receivePostId();
        receiveUserId();

        if (mSupport.checkNetworkConnection(mMainActivity))
            if (!isPostIdNull)
                getPostById();
            else
                mSupport.showToastError(mMainActivity);
        else
            mSupport.showToastNoConnection(mMainActivity);

        return rootView;
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_post_info));
    }

    private void receivePostId() {

        try {
            mPostId = getArguments().getInt(Constants.POST_ID_BUNDLE_KEY);

            isPostIdNull = false;
            Log.i(LOG_TAG, "POST ID IS RECEIVED: " + String.valueOf(mPostId) + ".");

        } catch (NullPointerException _error) {

            isPostIdNull = true;
            Log.e(LOG_TAG, "POST ID IS NOT RECEIVED. POST ID IS NULL.");
            _error.printStackTrace();
        }
    }

    private void receiveUserId() {

        try {
            mUserId = getArguments().getInt(Constants.USER_ID_BUNDLE_KEY);

            isUserIdNull = false;
            Log.i(LOG_TAG, "USER ID IS RECEIVED: " + String.valueOf(mUserId) + ".");

        } catch (NullPointerException _error) {

            isUserIdNull = true;
            Log.e(LOG_TAG, "USER ID IS NOT RECEIVED. USER ID IS NULL.");
            _error.printStackTrace();
        }
    }

    public void getPostById() {

        final GetObjectTask getObjectTask = new GetObjectTask(Constants.OBJECT_TYPE_POST, mPostId);
        getObjectTask.setCallback(new GetObjectTask.Callback() {
            @Override
            public void onSuccess(Object _result) {

                Log.i(getObjectTask.LOG_TAG, "ON SUCCESS.");

                final Post post = (Post) _result;
                mBinding.setPost(post);

                initButtons();

                getObjectTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.i(getObjectTask.LOG_TAG, "ON FAILURE.");
                _error.printStackTrace();

                mSupport.showToastError(mMainActivity);

                getObjectTask.releaseCallback();
            }
        });
        getObjectTask.execute();
    }

    private void initButtons() {

        mBinding.setClickerComments(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.POST_ID_BUNDLE_KEY, mPostId);
                bundle.putSerializable(Constants.USER_ID_BUNDLE_KEY, mUserId);
                mMainActivity.commitFragment(new CommentListFragment(), bundle);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isUserIdNull) {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.USER_ID_BUNDLE_KEY, mUserId);
            mMainActivity.commitFragment(new PostListFragment(), bundle);

        } else
            mMainActivity.commitFragment(new UserListFragment(), null);
    }
}
