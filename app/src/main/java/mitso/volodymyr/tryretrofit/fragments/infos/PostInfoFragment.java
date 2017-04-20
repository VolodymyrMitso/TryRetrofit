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
import mitso.volodymyr.tryretrofit.databinding.FragmentInfoPostBinding;
import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.CommentListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.PostListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.UserListFragment;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.support.Support;

public class PostInfoFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.POST_INFO_FRAGMENT_LOG_TAG;

    private Support                         mSupport;

    private FragmentInfoPostBinding         mBinding;

    private int                             mUserId;
    private int                             mPostId;
    private int[]                           mIdArray;
    private boolean                         isIdArrayNull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_info_post, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "POST INFO FRAGMENT IS CREATED.");

        initSupport();
        initActionBar();
        receiveIdArray();

        if (mSupport.checkNetworkConnection(mMainActivity))
            if (!isIdArrayNull)
                getPostById();
            else
                mSupport.showToastError(mMainActivity);
        else
            mSupport.showToastNoNetworkConnection(mMainActivity);

        return rootView;
    }

    private void initSupport() {

        mSupport = new Support();
    }

    private void initActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_post_info));
    }

    private void receiveIdArray() {

        try {
            mIdArray = getArguments().getIntArray(Constants.ID_ARRAY_BUNDLE_KEY);
            if (mIdArray == null)
                throw new NullPointerException();

            mUserId = mIdArray[0];
            mPostId = mIdArray[1];

            isIdArrayNull = false;
            Log.i(LOG_TAG, "USER ID IS RECEIVED: " + String.valueOf(mUserId) + ".");
            Log.i(LOG_TAG, "ALBUM ID IS RECEIVED: " + String.valueOf(mPostId) + ".");

        } catch (NullPointerException _error) {

            isIdArrayNull = true;
            Log.e(LOG_TAG, "ID ARRAY IS NOT RECEIVED. ID ARRAY IS NULL.");
            _error.printStackTrace();
        }
    }

    public void getPostById() {

        final GetObjectTask getObjectTask = new GetObjectTask(mMainActivity, Constants.OBJECT_TYPE_POST, mPostId);
        getObjectTask.setCallback(new GetObjectTask.Callback() {
            @Override
            public void onSuccess(Object _result) {

                Log.i(getObjectTask.LOG_TAG, "ON SUCCESS: POST.");

                final Post post = (Post) _result;
                mBinding.setPost(post);

                initButtons();

                getObjectTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.e(getObjectTask.LOG_TAG, "ON FAILURE: ERROR.");
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

                final int[] idArray = new int[] { mUserId, mPostId };
                final Bundle bundle = new Bundle();
                bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, idArray);
                mMainActivity.commitFragment(new CommentListFragment(), bundle);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isIdArrayNull) {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.ID_ARRAY_BUNDLE_KEY, new int[] { mUserId });
            mMainActivity.commitFragment(new PostListFragment(), bundle);

        } else
            mMainActivity.commitFragment(new UserListFragment(), null);
    }

    @Override
    public void onOptionsItemSelected() {
        super.onOptionsItemSelected();

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.FRAGMENT_TYPE_BUNDLE_KEY, Constants.FRAGMENT_TYPE_POST_INFO);
        bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, mIdArray);
        mMainActivity.commit10thFragment(bundle);
    }
}
