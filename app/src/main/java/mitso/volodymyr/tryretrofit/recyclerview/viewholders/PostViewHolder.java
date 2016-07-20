package mitso.volodymyr.tryretrofit.recyclerview.viewholders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mitso.volodymyr.tryretrofit.databinding.CardPostBinding;

public class PostViewHolder extends RecyclerView.ViewHolder {

    private CardPostBinding     mBinding;

    public PostViewHolder(View _itemView) {
        super(_itemView);

        mBinding = DataBindingUtil.bind(_itemView);
    }

    public CardPostBinding getBinding() {
        return mBinding;
    }
}
