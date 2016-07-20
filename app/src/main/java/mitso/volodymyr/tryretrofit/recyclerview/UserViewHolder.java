package mitso.volodymyr.tryretrofit.recyclerview;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mitso.volodymyr.tryretrofit.databinding.CardUserBinding;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private CardUserBinding     mBinding;

    public UserViewHolder(View _itemView) {
        super(_itemView);

        mBinding = DataBindingUtil.bind(_itemView);
    }

    public CardUserBinding getBinding() {
        return mBinding;
    }
}
