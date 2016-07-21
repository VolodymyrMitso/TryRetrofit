package mitso.volodymyr.tryretrofit.recyclerview.viewholders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mitso.volodymyr.tryretrofit.databinding.CardCommentBinding;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private CardCommentBinding      mBinding;

    public CommentViewHolder(View _itemView) {
        super(_itemView);

        mBinding = DataBindingUtil.bind(_itemView);
    }

    public CardCommentBinding getBinding() {
        return mBinding;
    }
}
