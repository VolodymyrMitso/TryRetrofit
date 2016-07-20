package mitso.volodymyr.tryretrofit.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mitso.volodymyr.tryretrofit.constants.Constants;
import mitso.volodymyr.tryretrofit.databinding.CardUserBinding;
import mitso.volodymyr.tryretrofit.models.User;

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

        if (mViewType == 1)
            return new UserViewHolder(CardUserBinding.inflate(LayoutInflater.from(_parent.getContext()), _parent, false).getRoot());
        else
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _holder, int _position) {

        if (mViewType == 1) {

            final User user = (User) mObjectList.get(_position);

            final UserViewHolder userViewHolder = (UserViewHolder) _holder;

            userViewHolder.getBinding().setUser(user);

            userViewHolder.getBinding().setClicker(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommonHandler.onClick(user, userViewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mObjectList.size();
    }

    public void setNoteHandler(ICommonHandler _userHandler) {
        if (mCommonHandler == null) {
            this.mCommonHandler = _userHandler;
            Log.i(LOG_TAG, "HANDLER IS SET.");
        }
    }

    public void releaseNoteHandler() {
        if (mCommonHandler != null) {
            this.mCommonHandler = null;
            Log.i(LOG_TAG, "HANDLER IS NULL.");
        }
    }
}
