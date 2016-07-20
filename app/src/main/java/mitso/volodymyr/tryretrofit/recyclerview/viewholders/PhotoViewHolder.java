package mitso.volodymyr.tryretrofit.recyclerview.viewholders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mitso.volodymyr.tryretrofit.databinding.CardPhotoBinding;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private CardPhotoBinding        mBinding;

    public PhotoViewHolder(View _itemView) {
        super(_itemView);

        mBinding = DataBindingUtil.bind(_itemView);
    }

    public CardPhotoBinding getBinding() {
        return mBinding;
    }
}
