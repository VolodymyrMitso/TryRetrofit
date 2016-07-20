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
import mitso.volodymyr.tryretrofit.recyclerview.CommonAdapter;
import mitso.volodymyr.tryretrofit.recyclerview.ItemDecoration;

public class PhotoListFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.PHOTO_LIST_FRAGMENT_LOG_TAG;

    private FragmentCommonListBinding       mBinding;

    private List<Object>                    mPhotoList;
    private CommonAdapter                   mCommonAdapter;

    private Integer                         mAlbumId;
    private boolean                         isAlbumIdNull;
    private Integer                         mUserId;
    private boolean                         isUserINull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_common_list, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "PHOTO LIST FRAGMENT IS CREATED.");

        iniActionBar();

        receiveAlbumId();
        receiveUserId();

        if (!isAlbumIdNull)
            getPhotosByAlbumId();

        return rootView;
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_photos));
    }

    private void receiveAlbumId() {

        try {
            mAlbumId = getArguments().getInt(Constants.ALBUM_ID_BUNDLE_KEY);

            isAlbumIdNull = false;
            Log.i(LOG_TAG, "ALBUM ID IS RECEIVED: " + String.valueOf(mAlbumId) + ".");

        } catch (NullPointerException _error) {

            isAlbumIdNull = true;
            Log.e(LOG_TAG, "ALBUM ID IS NOT RECEIVED. ALBUM ID IS NULL.");
            _error.printStackTrace();
        }
    }

    private void receiveUserId() {

        try {
            mUserId = getArguments().getInt(Constants.USER_ID_BUNDLE_KEY);

            isUserINull = false;
            Log.i(LOG_TAG, "USER ID IS RECEIVED: " + String.valueOf(mUserId) + ".");

        } catch (NullPointerException _error) {

            isUserINull = true;
            Log.e(LOG_TAG, "USER ID IS NOT RECEIVED. USER ID IS NULL.");
            _error.printStackTrace();
        }
    }

    public void getPhotosByAlbumId() {

        final GetObjectsTask getObjectsTask = new GetObjectsTask(Constants.OBJECT_TYPE_PHOTO, mAlbumId);
        getObjectsTask.setCallback(new GetObjectsTask.Callback() {
            @Override
            public void onSuccess(List<Object> _result) {

                Log.i(getObjectsTask.LOG_TAG, "ON SUCCESS.");

                mPhotoList = new ArrayList<>(_result);

                initRecyclerView();

                getObjectsTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.i(getObjectsTask.LOG_TAG, "ON FAILURE");
                _error.printStackTrace();

                getObjectsTask.releaseCallback();
            }
        });
        getObjectsTask.execute();
    }

    private void initRecyclerView() {

        mCommonAdapter = new CommonAdapter(Constants.VIEW_TYPE_PHOTO, mPhotoList);
        mBinding.rvModels.setAdapter(mCommonAdapter);
        mBinding.rvModels.setLayoutManager(new LinearLayoutManager(mMainActivity));
        mBinding.rvModels.addItemDecoration(new ItemDecoration(
                mMainActivity.getResources().getDimensionPixelSize(R.dimen.d_card_margin_small),
                mMainActivity.getResources().getDimensionPixelSize(R.dimen.d_card_margin_big)));

        Log.i(LOG_TAG, "RECYCLER VIEW IS CREATED.");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isUserINull) {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.USER_ID_BUNDLE_KEY, mUserId);
            mMainActivity.commitFragment(new AlbumListFragment(), bundle);

        } else
            mMainActivity.commitFragment(new UserListFragment(), null);
    }
}
