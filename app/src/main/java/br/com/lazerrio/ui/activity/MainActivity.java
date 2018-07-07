package br.com.lazerrio.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.lazerrio.R;
import br.com.lazerrio.ui.fragment.AboutFragment;
import br.com.lazerrio.ui.fragment.ListOptionsFragment;
import br.com.lazerrio.ui.fragment.MainFragment;
import br.com.lazerrio.util.FragmentUtil;
import br.com.lazerrio.util.PermissionUtil;
import br.com.lazerrio.util.ShareUtil;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission()) {
            PermissionUtil.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION});
        }

        createToolbarAndNavigationView();
        FragmentUtil.changeFragment(R.id.conatiner, MainFragment.class, getSupportFragmentManager(), false, null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            FragmentUtil.changeFragment(R.id.conatiner, AboutFragment.class, getSupportFragmentManager(), false, null);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();

        switch (id) {
            case R.id.nav_about:
                changeFragment(AboutFragment.class, bundle);
                break;

            case R.id.nav_beach:
                bundle.putString("option", "beach");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_hotel:
                bundle.putString("option", "hotel");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_leisure:
                bundle.putString("option", "leisure");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_movie:
                bundle.putString("option", "movie");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_museum:
                bundle.putString("option", "museum");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_restaurant:
                bundle.putString("option", "restaurant");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_share:
                String shareText = getString(R.string.download_the_app)+"\n"
                                    +getString(R.string.link_app);
                ShareUtil.directShare(this, getString(R.string.share), shareText);
                break;

            case R.id.nav_shopping:
                bundle.putString("option", "shopping");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_sport:
                bundle.putString("option", "sport");
                changeFragment(ListOptionsFragment.class, bundle);
                break;

            case R.id.nav_theater:
                bundle.putString("option", "theater");
                changeFragment(ListOptionsFragment.class, bundle);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void createToolbarAndNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    private boolean checkPermission() {
        int resultAccessFineLocation = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int resultAccessCoarseLocation = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        return resultAccessFineLocation == PackageManager.PERMISSION_GRANTED && resultAccessCoarseLocation == PackageManager.PERMISSION_GRANTED;
    }

    private void changeFragment(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        FragmentUtil.changeFragment(R.id.conatiner, fragmentClass, getSupportFragmentManager(), false, bundle);
    }

}
