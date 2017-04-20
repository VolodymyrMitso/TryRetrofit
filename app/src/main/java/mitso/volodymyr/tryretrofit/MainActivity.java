package mitso.volodymyr.tryretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import mitso.volodymyr.tryretrofit.fragments.BaseFragment;
import mitso.volodymyr.tryretrofit.fragments.create.CreateObjectFragment;
import mitso.volodymyr.tryretrofit.fragments.lists.UserListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        commitFragment(new UserListFragment(), null);
    }

    public void commitFragment(BaseFragment _baseFragment, Bundle _bundle) {

        _baseFragment.setArguments(_bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container_am, _baseFragment)
                .commitAllowingStateLoss();
    }

    public void commit10thFragment(Bundle _bundle) {

        commitFragment(new CreateObjectFragment(), _bundle);
    }

    @Override
    public void onBackPressed() {

        final BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_am);
        baseFragment.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {

        getMenuInflater().inflate(R.menu.menu, _menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {

        final BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_am);

        switch (_item.getItemId()) {

            case android.R.id.home:
                baseFragment.onBackPressed();
                return true;

            case R.id.mi_create_object:
                baseFragment.onOptionsItemSelected();
                return true;

            default:
                return super.onOptionsItemSelected(_item);
        }
    }
}