package mitso.volodymyr.tryretrofit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.create.CreateObjectFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.UserListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commitFragment(new UserListFragment(), null);
    }

    public void commitFragment(BaseFragment _baseFragment, Bundle _bundle) {

        _baseFragment.setArguments(_bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fl_container, _baseFragment)
                .commitAllowingStateLoss();
    }

    public void commit10thFragment(Bundle _bundle) {

        final CreateObjectFragment createObjectFragment = new CreateObjectFragment();

        commitFragment(createObjectFragment, _bundle);
    }

    @Override
    public void onBackPressed() {

        final BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container);
        baseFragment.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {

        getMenuInflater().inflate(R.menu.menu, _menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {

        switch (_item.getItemId()) {
            case R.id.mi_create_object:

                final BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container);
                baseFragment.onOptionsItemSelected();

                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }
}