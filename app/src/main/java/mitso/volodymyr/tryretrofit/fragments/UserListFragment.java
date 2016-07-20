package mitso.volodymyr.tryretrofit.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mitso.volodymyr.tryretrofit.R;
import mitso.volodymyr.tryretrofit.api.tasks.GetAllUsersTask;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.FragmentUserListBinding;
import mitso.volodymyr.tryretrofit.models.User;
import mitso.volodymyr.tryretrofit.recyclerview.CommonAdapter;
import mitso.volodymyr.tryretrofit.recyclerview.ICommonHandler;

public class UserListFragment extends BaseFragment implements ICommonHandler {

    private final String                LOG_TAG = Constants.USER_LIST_LOG_TAG;

    private FragmentUserListBinding     mBinding;

    private List<Object>                mUserList;
    private CommonAdapter               mCommonAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_user_list, _container, false);
        final View rootView = mBinding.getRoot();

        getAllUsers();

        return rootView;
    }

    public void getAllUsers() {

        final GetAllUsersTask getAllUsersTask = new GetAllUsersTask();
        getAllUsersTask.setCallback(new GetAllUsersTask.Callback() {
            @Override
            public void onSuccess(List<User> _result) {

                Log.i(getAllUsersTask.LOG_TAG, "ON SUCCESS.");

                mUserList = new ArrayList<Object>(_result);

                initRecyclerView();
                setHandler();

                getAllUsersTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.i(getAllUsersTask.LOG_TAG, "ON FAILURE");
                Log.i(getAllUsersTask.LOG_TAG, _error.toString());

                getAllUsersTask.releaseCallback();
            }
        });
        getAllUsersTask.execute();
    }

    private void initRecyclerView() {

        mCommonAdapter = new CommonAdapter(1, mUserList);
        mBinding.rvModels.setAdapter(mCommonAdapter);
        mBinding.rvModels.setLayoutManager(new LinearLayoutManager(mMainActivity));

        Log.i(LOG_TAG, "RECYCLER VIEW IS CREATED.");
    }

    private void setHandler() {
        if (mCommonAdapter != null)
            mCommonAdapter.setNoteHandler(this);
    }

    private void releaseHandler() {
        if (mCommonAdapter != null)
            mCommonAdapter.releaseNoteHandler();
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

        mMainActivity.finish();
    }

    @Override
    public void onClick(Object _object, int _position) {

        Toast.makeText(mMainActivity, _object.toString(), Toast.LENGTH_SHORT).show();
    }
}
