package mitso.volodymyr.tryretrofit.recyclerview.viewholders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mitso.volodymyr.tryretrofit.databinding.CardAlbumBinding;

public class AlbumViewHolder extends RecyclerView.ViewHolder {

    private CardAlbumBinding    mBinding;

    public AlbumViewHolder(View _itemView) {
        super(_itemView);

        mBinding = DataBindingUtil.bind(_itemView);
    }

    public CardAlbumBinding getBinding() {
        return mBinding;
    }
}
