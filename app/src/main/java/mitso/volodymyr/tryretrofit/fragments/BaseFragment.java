package mitso.volodymyr.tryretrofit.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import mitso.volodymyr.tryretrofit.MainActivity;

public class BaseFragment extends Fragment {

    MainActivity        mMainActivity;

    @Override
    public void onAttach(Context _context) {
        super.onAttach(_context);

        mMainActivity = (MainActivity) _context;
    }

    public void onBackPressed() {

    }
}
