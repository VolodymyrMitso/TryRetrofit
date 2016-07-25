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
import mitso.volodymyr.tryretrofit.api.tasks.PostObjectTask;
import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.FragmentCreateObjectBinding;
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
import mitso.volodymyr.tryretrofit.models.Album;
import mitso.volodymyr.tryretrofit.models.Comment;
import mitso.volodymyr.tryretrofit.models.Photo;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.models.Todo;
import mitso.volodymyr.tryretrofit.models.User;
import mitso.volodymyr.tryretrofit.support.Support;

public class CreateObjectFragment extends BaseFragment {

    private final String                    LOG_TAG = Constants.CREATE_OBJECT_FRAGMENT_LOG_TAG;

    private Support                         mSupport;

    private FragmentCreateObjectBinding     mBinding;

    private int                             mFragmentType;
    private int[]                           mIdArray;
    private boolean                         isFragmentTypeNull;

    private int                             mId;
    private boolean                         isIdNull;

    private Object                          mObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {

        mBinding = DataBindingUtil.inflate(_inflater, R.layout.fragment_create_object, _container, false);
        final View rootView = mBinding.getRoot();

        Log.i(LOG_TAG, "CREATE OBJECT FRAGMENT IS CREATED.");

        mSupport = new Support();

        receiveFragmentType();
        receiveIdArray();

        iniActionBar();
        setHasOptionsMenu(true);

        initButtons();

        return rootView;
    }

    private void receiveFragmentType() {

        mFragmentType = getArguments().getInt(Constants.FRAGMENT_TYPE_BUNDLE_KEY, Constants.FRAGMENT_TYPE_DEFAULT_VALUE);

        if (mFragmentType != Constants.FRAGMENT_TYPE_DEFAULT_VALUE) {

            isFragmentTypeNull = false;
            Log.i(LOG_TAG, "FRAGMENT TYPE IS RECEIVED: " + mFragmentType + ".");

        } else {

            isFragmentTypeNull = true;
            Log.e(LOG_TAG, "FRAGMENT TYPE IS NOT RECEIVED. FRAGMENT TYPE = -1.");

            mSupport.showToastError(mMainActivity);
        }
    }

    private void receiveIdArray() {

        try {
            mIdArray = getArguments().getIntArray(Constants.ID_ARRAY_BUNDLE_KEY);

            if (mIdArray == null)
                throw new NullPointerException();

            Log.i(LOG_TAG, "ID ARRAY IS RECEIVED: " + Arrays.toString(mIdArray) + ".");

        } catch (NullPointerException _error) {

            Log.i(LOG_TAG, "ID ARRAY IS NOT RECEIVED. ID ARRAY IS NULL.");
        }
    }

    private void iniActionBar() {

        if (mMainActivity.getSupportActionBar() != null) {

            if (mFragmentType == Constants.FRAGMENT_TYPE_USER_LIST || mFragmentType == Constants.FRAGMENT_TYPE_USER_INFO)
                mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_create_user));

            if (mFragmentType == Constants.FRAGMENT_TYPE_TODO_LIST)
                mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_create_todo));

            if (mFragmentType == Constants.FRAGMENT_TYPE_ALBUM_LIST)
                mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_create_album));

            if (mFragmentType == Constants.FRAGMENT_TYPE_POST_LIST || mFragmentType == Constants.FRAGMENT_TYPE_POST_INFO)
                mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_create_post));

            if (mFragmentType == Constants.FRAGMENT_TYPE_PHOTO_LIST)
                mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_create_photo));

            if (mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_LIST || mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_INFO)
                mMainActivity.getSupportActionBar().setTitle(mMainActivity.getResources().getString(R.string.s_create_comment));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu _menu, MenuInflater _inflater) {
        super.onCreateOptionsMenu(_menu, _inflater);

        final MenuItem menuItem = _menu.findItem(R.id.mi_create_object);
        menuItem.setVisible(false);
    }

    private void initButtons() {

        mBinding.setClickerPost(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getIdFromEditText();

                if (!isFragmentTypeNull) {
                    createObject();

                    if (mSupport.checkNetworkConnection(mMainActivity))
                        postObject();
                    else
                        mSupport.showToastNoConnection(mMainActivity);

                } else
                    mSupport.showToastError(mMainActivity);

            }
        });
    }

    private void getIdFromEditText() {

        try {
            mId = Integer.parseInt(mBinding.etId.getText().toString());

            isIdNull = false;
            Log.i(LOG_TAG, "ID FROM EDIT TEXT IS: " + String.valueOf(mId) + ".");

        } catch (NumberFormatException _error) {

            isIdNull = true;
            Log.i(LOG_TAG, "ID FROM EDIT TEXT IS NULL.");
        }
    }

    private void createObject() {

        mObject = new Object();

        if (isIdNull) {

            if (mFragmentType == Constants.FRAGMENT_TYPE_USER_LIST || mFragmentType == Constants.FRAGMENT_TYPE_USER_INFO)
                mObject = new User();

            else if (mFragmentType == Constants.FRAGMENT_TYPE_TODO_LIST)
                mObject = new Todo();

            if (mFragmentType == Constants.FRAGMENT_TYPE_ALBUM_LIST)
                mObject = new Album();

            if (mFragmentType == Constants.FRAGMENT_TYPE_POST_LIST || mFragmentType == Constants.FRAGMENT_TYPE_POST_INFO)
                mObject = new Post();

            if (mFragmentType == Constants.FRAGMENT_TYPE_PHOTO_LIST)
                mObject = new Photo();

            if (mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_LIST || mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_INFO)
                mObject = new Comment();

        } else {

            if (mFragmentType == Constants.FRAGMENT_TYPE_USER_LIST || mFragmentType == Constants.FRAGMENT_TYPE_USER_INFO) {

                final User user = new User();
                user.setId(mId);
                mObject = user;

            } else if (mFragmentType == Constants.FRAGMENT_TYPE_TODO_LIST) {

                final Todo todo = new Todo();
                todo.setId(mId);
                mObject = todo;

            } else if (mFragmentType == Constants.FRAGMENT_TYPE_ALBUM_LIST) {

                final Album album = new Album();
                album.setId(mId);
                mObject = album;

            } else if (mFragmentType == Constants.FRAGMENT_TYPE_POST_LIST || mFragmentType == Constants.FRAGMENT_TYPE_POST_INFO) {

                final Post post = new Post();
                post.setId(mId);
                mObject = post;

            } else if (mFragmentType == Constants.FRAGMENT_TYPE_PHOTO_LIST) {

                final Photo photo = new Photo();
                photo.setId(mId);
                mObject = photo;

            } else if (mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_LIST || mFragmentType == Constants.FRAGMENT_TYPE_COMMENT_INFO) {

                final Comment comment = new Comment();
                comment.setId(mId);
                mObject = comment;
            }
        }
    }

    public void postObject() {

        final PostObjectTask postObjectTask = new PostObjectTask(mFragmentType, mObject);
        postObjectTask.setCallback(new PostObjectTask.Callback() {
            @Override
            public void onSuccess(Object _result) {

                Log.i(postObjectTask.LOG_TAG, "ON SUCCESS.");
                Log.i(postObjectTask.LOG_TAG, _result.toString());

                mBinding.setObject(_result);

                postObjectTask.releaseCallback();
            }

            @Override
            public void onFailure(Throwable _error) {

                Log.e(postObjectTask.LOG_TAG, "ON FAILURE: ERROR.");
                _error.printStackTrace();

                mSupport.showToastError(mMainActivity);

                postObjectTask.releaseCallback();
            }
        });
        postObjectTask.execute();
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
