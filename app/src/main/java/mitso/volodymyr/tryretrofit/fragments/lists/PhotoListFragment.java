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
import mitso.volodymyr.tryretrofit.support.Support;

public class PhotoListFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.PHOTO_LIST_FRAGMENT_LOG_TAG;

    private Support                         mSupport;

    private FragmentCommonListBinding       mBinding;

    private List<Object>                    mPhotoList;

    private int                             mUserId;
    private int                             mAlbumId;
    private int[]                           mIdArray;
    private boolean                         isIdArrayNull;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_common_list, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "PHOTO LIST FRAGMENT IS CREATED.");

        mSupport = new Support();

        iniActionBar();

        receiveIdArray();

        if (mSupport.checkNetworkConnection(mMainActivity))
            if (!isIdArrayNull)
                getPhotosByAlbumId();
            else
                mSupport.showToastError(mMainActivity);
        else
            mSupport.showToastNoConnection(mMainActivity);

        return rootView;
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null)
            mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_photos));
    }

    private void receiveIdArray() {

        try {
            mIdArray = getArguments().getIntArray(Constants.ID_ARRAY_BUNDLE_KEY);
            if (mIdArray == null)
                throw new NullPointerException();

            mUserId = mIdArray[0];
            mAlbumId = mIdArray[1];

            isIdArrayNull = false;
            Log.i(LOG_TAG, "USER ID IS RECEIVED: " + String.valueOf(mUserId) + ".");
            Log.i(LOG_TAG, "ALBUM ID IS RECEIVED: " + String.valueOf(mAlbumId) + ".");

        } catch (NullPointerException _error) {

            isIdArrayNull = true;
            Log.e(LOG_TAG, "ID ARRAY IS NOT RECEIVED. ID ARRAY IS NULL.");
            _error.printStackTrace();
        }
    }

    public void getPhotosByAlbumId() {

        final GetObjectsTask getObjectsTask = new GetObjectsTask(Constants.OBJECT_TYPE_PHOTO, mAlbumId);
        getObjectsTask.setCallback(new GetObjectsTask.Callback() {
            @Override
            public void onSuccess(List<Object> _result) {

                Log.i(getObjectsTask.LOG_TAG, "ON SUCCESS: PHOTO LIST.");

                mPhotoList = new ArrayList<>(_result);

                initRecyclerView();

                getObjectsTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.i(getObjectsTask.LOG_TAG, "ON FAILURE: ERROR.");
                _error.printStackTrace();

                mSupport.showToastError(mMainActivity);

                getObjectsTask.releaseCallback();
            }
        });
        getObjectsTask.execute();
    }

    private void initRecyclerView() {

        mBinding.rvModels.setAdapter(new CommonAdapter(Constants.VIEW_TYPE_PHOTO, mPhotoList));
        mBinding.rvModels.setLayoutManager(new LinearLayoutManager(mMainActivity));
        mBinding.rvModels.addItemDecoration(new ItemDecoration(
                mMainActivity.getResources().getDimensionPixelSize(R.dimen.d_card_margin_small),
                mMainActivity.getResources().getDimensionPixelSize(R.dimen.d_card_margin_big)));

        Log.i(LOG_TAG, "RECYCLER VIEW IS CREATED.");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isIdArrayNull) {

            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.ID_ARRAY_BUNDLE_KEY, new int[] { mUserId });
            mMainActivity.commitFragment(new AlbumListFragment(), bundle);

        } else
            mMainActivity.commitFragment(new UserListFragment(), null);
    }

    @Override
    public void onOptionsItemSelected() {
        super.onOptionsItemSelected();

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.FRAGMENT_TYPE_BUNDLE_KEY, Constants.FRAGMENT_TYPE_PHOTO_LIST);
        bundle.putIntArray(Constants.ID_ARRAY_BUNDLE_KEY, mIdArray);
        mMainActivity.commit10thFragment(bundle);
    }
}

