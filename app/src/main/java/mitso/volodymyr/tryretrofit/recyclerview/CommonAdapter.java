package mitso.volodymyr.tryretrofit.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.CardAlbumBinding;
import mitso.volodymyr.tryretrofit.databinding.CardPhotoBinding;
import mitso.volodymyr.tryretrofit.databinding.CardPostBinding;
import mitso.volodymyr.tryretrofit.databinding.CardTodoBinding;
import mitso.volodymyr.tryretrofit.databinding.CardUserBinding;
import mitso.volodymyr.tryretrofit.models.Album;
import mitso.volodymyr.tryretrofit.models.Photo;
import mitso.volodymyr.tryretrofit.models.Post;
import mitso.volodymyr.tryretrofit.models.Todo;
import mitso.volodymyr.tryretrofit.models.User;
import mitso.volodymyr.tryretrofit.recyclerview.viewholders.AlbumViewHolder;
import mitso.volodymyr.tryretrofit.recyclerview.viewholders.PhotoViewHolder;
import mitso.volodymyr.tryretrofit.recyclerview.viewholders.PostViewHolder;
import mitso.volodymyr.tryretrofit.recyclerview.viewholders.TodoViewHolder;
import mitso.volodymyr.tryretrofit.recyclerview.viewholders.UserViewHolder;

public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = Constants.COMMON_ADAPTER_LOG_TAG;

    private List<Object>        mObjectList;
    private int                 mViewType;

    private ICommonHandler      mCommonHandler;

    public CommonAdapter(int _viewType, List<Object> _objectList) {

        this.mViewType = _viewType;
        this.mObjectList = new ArrayList<>(_objectList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup _parent, int _viewType) {

        if (mViewType == Constants.VIEW_TYPE_USER)
            return new UserViewHolder(CardUserBinding.inflate(LayoutInflater.from(_parent.getContext()), _parent, false).getRoot());

        else if (mViewType == Constants.VIEW_TYPE_TODO)
            return new TodoViewHolder(CardTodoBinding.inflate(LayoutInflater.from(_parent.getContext()), _parent, false).getRoot());

        else if (mViewType == Constants.VIEW_TYPE_ALBUM)
            return new AlbumViewHolder(CardAlbumBinding.inflate(LayoutInflater.from(_parent.getContext()), _parent, false).getRoot());

        else if (mViewType == Constants.VIEW_TYPE_POST)
            return new PostViewHolder(CardPostBinding.inflate(LayoutInflater.from(_parent.getContext()), _parent, false).getRoot());

        else if (mViewType == Constants.VIEW_TYPE_PHOTO)
            return new PhotoViewHolder(CardPhotoBinding.inflate(LayoutInflater.from(_parent.getContext()), _parent, false).getRoot());

        else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _holder, int _position) {

        if (mViewType == Constants.VIEW_TYPE_USER) {

            final User user = (User) mObjectList.get(_position);
            final UserViewHolder userViewHolder = (UserViewHolder) _holder;

            userViewHolder.getBinding().setUser(user);
            userViewHolder.getBinding().setClicker(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommonHandler.onClick(user, userViewHolder.getAdapterPosition());
                }
            });

        } else if (mViewType == Constants.VIEW_TYPE_TODO) {

            final Todo todo = (Todo) mObjectList.get(_position);
            final TodoViewHolder todoViewHolder = (TodoViewHolder) _holder;

            todoViewHolder.getBinding().setTodo(todo);
            todoViewHolder.getBinding().setClicker(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommonHandler.onClick(todo, todoViewHolder.getAdapterPosition());
                }
            });

        } else if (mViewType == Constants.VIEW_TYPE_ALBUM) {

            final Album album = (Album) mObjectList.get(_position);
            final AlbumViewHolder albumViewHolder = (AlbumViewHolder) _holder;

            albumViewHolder.getBinding().setAlbum(album);
            albumViewHolder.getBinding().setClicker(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommonHandler.onClick(album, albumViewHolder.getAdapterPosition());
                }
            });

        } else if (mViewType == Constants.VIEW_TYPE_POST) {

            final Post post = (Post) mObjectList.get(_position);
            final PostViewHolder postViewHolder = (PostViewHolder) _holder;

            postViewHolder.getBinding().setPost(post);
            postViewHolder.getBinding().setClicker(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommonHandler.onClick(post, postViewHolder.getAdapterPosition());
                }
            });

        } else if (mViewType == Constants.VIEW_TYPE_PHOTO) {

            final Photo photo = (Photo) mObjectList.get(_position);
            final PhotoViewHolder photoViewHolder = (PhotoViewHolder) _holder;

            photoViewHolder.getBinding().setPhoto(photo);
        }
    }

    @Override
    public int getItemCount() {

        return mObjectList.size();
    }

    public void setCommonHandler(ICommonHandler _commonHandler) {

        if (mCommonHandler == null) {
            this.mCommonHandler = _commonHandler;
            Log.i(LOG_TAG, "HANDLER IS SET.");
        }
    }

    public void releaseCommonHandler() {

        if (mCommonHandler != null) {
            this.mCommonHandler = null;
            Log.i(LOG_TAG, "HANDLER IS NULL.");
        }
    }
}
