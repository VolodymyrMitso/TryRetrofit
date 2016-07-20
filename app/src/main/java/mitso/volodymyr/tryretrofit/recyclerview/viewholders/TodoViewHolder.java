package mitso.volodymyr.tryretrofit.recyclerview.viewholders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mitso.volodymyr.tryretrofit.databinding.CardTodoBinding;

public class TodoViewHolder extends RecyclerView.ViewHolder {

    private CardTodoBinding     mBinding;

    public TodoViewHolder(View _itemView) {
        super(_itemView);

        mBinding = DataBindingUtil.bind(_itemView);
    }

    public CardTodoBinding getBinding() {
        return mBinding;
    }
}
