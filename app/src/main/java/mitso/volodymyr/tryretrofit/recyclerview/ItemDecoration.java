package mitso.volodymyr.tryretrofit.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int         mMarginSmall;
    private int         mMarginBig;

    public ItemDecoration(int _margin, int _marginTop) {

        this.mMarginSmall = _margin;
        this.mMarginBig = _marginTop;
    }

    @Override
    public void getItemOffsets(Rect _outRect, View _view, RecyclerView _parent, RecyclerView.State _state) {

        _outRect.left = mMarginSmall;
        _outRect.right = mMarginSmall;

        if (_parent.getChildAdapterPosition(_view) == _parent.getLayoutManager().getItemCount() - 1)
            _outRect.bottom = mMarginSmall;
        else
            _outRect.bottom = mMarginBig;

        if (_parent.getChildAdapterPosition(_view) == 0)
            _outRect.top = mMarginBig;
        else
            _outRect.top = 0;
    }
}
