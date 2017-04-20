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
import mitso.volodymyr.tryretrofit.api.tasks.GetObjectListTask;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.FragmentListCommonBinding;
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

    private FragmentListCommonBinding       mBinding;

    private List<Object>                    mCommentList;
    private CommonAdapter                   mCommonAdapter;

    private int                             mUserId;
    private int                             mPostId;
    private int[]                           mIdArray;
    private boolean                         isIdArrayNull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_list_common, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "COMMENT LIST FRAGMENT IS CREATED.");

        initSupport();
        initActionBar();
        receiveIdArray();

        if (mSupport.checkNetworkConnection(mMainActivity))
            if (!isIdArrayNull)
                getCommentsByPostId();
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
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_comments));
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
            Log.i(LOG_TAG, "POST ID IS RECEIVED: " + String.valueOf(mPostId) + ".");

        } catch (NullPointerException _error) {

            isIdArrayNull = true;
            Log.e(LOG_TAG, "ID ARRAY IS NOT RECEIVED. ID ARRAY IS NULL.");
            _error.printStackTrace();
        }
    }

    public void getCommentsByPostId() {

        final GetObjectListTask getObjectListTask = new GetObjectListTask(mMainActivity, Constants.OBJECT_TYPE_COMMENT, mPostId);
        getObjectListTask.setCallback(new GetObjectListTask.Callback() {
            @Override
            public void onSuccess(List<Object> _result) {

                Log.i(getObjectListTask.LOG_TAG, "ON SUCCESS: COMMENT LIST.");

                mCommentList = new ArrayList<>(_result);

                initRecyclerView();
                setHandler();

                getObjectListTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.e(getObjectListTask.LOG_TAG, "ON FAILURE: ERROR.");
                _error.printStackTrace();

                mSupport.showToastError(mMainActivity);

                getObjectListTask.releaseCallback();
            }
        });
        getObjectListTask.execute();
    }

    private void initRecyclerView() {

        mCommonAdapter = new CommonAdapter(Constants.VIEW_TYPE_COMMENT, mCommentList);

        mBinding.rvModelsFlc.setAdapter(mCommonAdapter);
        mBinding.rvModelsFlc.setLayoutManager(new LinearLayoutManager(mMainActivity));
        mBinding.rvModelsFlc.addItemDecoration(new ItemDecoration(
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
    public void itemOnClick(Object _object) {

        final int commentId = ((Comment) _object).getId();
        final int[] idArray = new int[] { mUserId, mPostId, commentId };
        final Bundle bundle = new Bundle();
        bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, idArray);
        mMainActivity.commitFragment(new CommentInfoFragment(), bundle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isIdArrayNull) {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.ID_ARRAY_BUNDLE_KEY, new int[] { mUserId, mPostId });
            mMainActivity.commitFragment(new PostInfoFragment(), bundle);

        } else
            mMainActivity.commitFragment(new UserListFragment(), null);
    }

    @Override
    public void onOptionsItemSelected() {
        super.onOptionsItemSelected();

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.FRAGMENT_TYPE_BUNDLE_KEY, Constants.FRAGMENT_TYPE_COMMENT_LIST);
        bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, mIdArray);
        mMainActivity.commit10thFragment(bundle);
    }
}

