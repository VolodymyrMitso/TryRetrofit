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
import mitso.volodymyr.tryretrofit.databinding.FragmentCommentInfoBinding;
import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.CommentListFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.UserListFragment;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.support.Support;

public class CommentInfoFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.COMMENT_INFO_FRAGMENT_LOG_TAG;

    private Support                         mSupport;

    private FragmentCommentInfoBinding      mBinding;

    private int                             mUserId;
    private int                             mPostId;
    private int                             mCommentId;
    private boolean                         isIdArrayNull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_comment_info, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "COMMENT INFO FRAGMENT IS CREATED.");

        mSupport = new Support();

        iniActionBar();

        receiveIdArray();

        if (mSupport.checkNetworkConnection(mMainActivity))
            if (!isIdArrayNull)
                getCommentById();
            else
                mSupport.showToastError(mMainActivity);
        else
            mSupport.showToastNoConnection(mMainActivity);

        return rootView;
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_comment_info));
    }

    private void receiveIdArray() {

        try {
            final int[] idArray = getArguments().getIntArray(Constants.ID_ARRAY_BUNDLE_KEY);
            if (idArray == null)
                throw new NullPointerException();

            mUserId = idArray[0];
            mPostId = idArray[1];
            mCommentId = idArray[2];

            isIdArrayNull = false;
            Log.i(LOG_TAG, "USER ID IS RECEIVED: " + String.valueOf(mUserId) + ".");
            Log.i(LOG_TAG, "POST ID IS RECEIVED: " + String.valueOf(mPostId) + ".");
            Log.i(LOG_TAG, "COMMENT ID IS RECEIVED: " + String.valueOf(mCommentId) + ".");

        } catch (NullPointerException _error) {

            isIdArrayNull = true;
            Log.e(LOG_TAG, "ID ARRAY IS NOT RECEIVED. ID ARRAY IS NULL.");
            _error.printStackTrace();
        }
    }

    public void getCommentById() {

        final GetObjectTask getObjectTask = new GetObjectTask(Constants.OBJECT_TYPE_COMMENT, mCommentId);
        getObjectTask.setCallback(new GetObjectTask.Callback() {
            @Override
            public void onSuccess(Object _result) {

                Log.i(getObjectTask.LOG_TAG, "ON SUCCESS: COMMENT.");

                final Comment comment = (Comment) _result;
                mBinding.setComment(comment);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isIdArrayNull) {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.ID_ARRAY_BUNDLE_KEY, new int[] { mUserId, mPostId });
            mMainActivity.commitFragment(new CommentListFragment(), bundle);

        } else
            mMainActivity.commitFragment(new UserListFragment(), null);
    }
}
