package mitso.volodymyr.tryretrofit.fragments.lists;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mitso.volodymyr.tryretrofit.R;
import mitso.volodymyr.tryretrofit.api.tasks.GetObjectsTask;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.FragmentCommonListBinding;
import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.infos.CommentInfoFragment;
import mitso.volodymyr.tryretrofit.fragments.infos.PostInfoFragment;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.recyclerview.CommonAdapter;
import mitso.volodymyr.tryretrofit.recyclerview.ICommonHandler;
import mitso.volodymyr.tryretrofit.recyclerview.ItemDecoration;
import mitso.volodymyr.tryretrofit.support.Support;

public class CommentListFragment extends BaseFragment implements ICommonHandler {

    private final String                    LOG_TAG = Constants.COMMENT_LIST_FRAGMENT_LOG_TAG;

    private Support                         mSupport;

    private FragmentCommonListBinding       mBinding;

    private List<Object>                    mCommentList;
    private CommonAdapter                   mCommonAdapter;

    private Integer                         mPostId;
    private boolean                         isPostIdNull;
    private Integer                         mUserId;
    private boolean                         isUserIdNull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_common_list, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "COMMENT LIST FRAGMENT IS CREATED.");

        mSupport = new Support();

        iniActionBar();

        receivePostId();
        receiveUserId();

        if (mSupport.checkNetworkConnection(mMainActivity))
            if (!isPostIdNull)
                getCommentsByPostId();
            else
                mSupport.showToastError(mMainActivity);
        else
            mSupport.showToastNoConnection(mMainActivity);

        return rootView;
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_comments));
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

    public void getCommentsByPostId() {

        final GetObjectsTask getObjectsTask = new GetObjectsTask(Constants.OBJECT_TYPE_COMMENT, mPostId);
        getObjectsTask.setCallback(new GetObjectsTask.Callback() {
            @Override
            public void onSuccess(List<Object> _result) {

                Log.i(getObjectsTask.LOG_TAG, "ON SUCCESS.");

                mCommentList = new ArrayList<>(_result);

                initRecyclerView();
                setHandler();

                getObjectsTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.i(getObjectsTask.LOG_TAG, "ON FAILURE.");
                _error.printStackTrace();

                mSupport.showToastError(mMainActivity);

                getObjectsTask.releaseCallback();
            }
        });
        getObjectsTask.execute();
    }

    private void initRecyclerView() {

        mCommonAdapter = new CommonAdapter(Constants.VIEW_TYPE_COMMENT, mCommentList);

        mBinding.rvModels.setAdapter(mCommonAdapter);
        mBinding.rvModels.setLayoutManager(new LinearLayoutManager(mMainActivity));
        mBinding.rvModels.addItemDecoration(new ItemDecoration(
                mMainActivity.getResources().getDimensionPixelSize(R.dimen.d_card_margin_small),
                mMainActivity.getResources().getDimensionPixelSize(R.dimen.d_card_margin_big)));

        Log.i(LOG_TAG, "RECYCLER VIEW IS CREATED.");
    }

    private void setHandler() {

        if (mCommonAdapter != null)
            mCommonAdapter.setCommonHandler(this);
    }

    private void releaseHandler() {

        if (mCommonAdapter != null)
            mCommonAdapter.releaseCommonHandler();
    }

    @Override
    public void onResume() {
        super.onResume();

        setHandler();
    }

    @Override
    public void onPause() {
        super.onPause();

        releaseHandler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isPostIdNull && !isUserIdNull) {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.POST_ID_BUNDLE_KEY, mPostId);
            bundle.putSerializable(Constants.USER_ID_BUNDLE_KEY, mUserId);
            mMainActivity.commitFragment(new PostInfoFragment(), bundle);

        } else
            mMainActivity.commitFragment(new UserListFragment(), null);
    }

    @Override
    public void itemOnClick(Object _object, int _position) {

        final int commentId = ((Comment) _object).getId();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.COMMENT_ID_BUNDLE_KEY, commentId);
        bundle.putSerializable(Constants.POST_ID_BUNDLE_KEY, mPostId);
        bundle.putSerializable(Constants.USER_ID_BUNDLE_KEY, mUserId);
        mMainActivity.commitFragment(new CommentInfoFragment(), bundle);

    }
}

