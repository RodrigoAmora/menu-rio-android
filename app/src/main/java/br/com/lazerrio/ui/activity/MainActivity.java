package br.com.lazerrio.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.lazerrio.R;
import br.com.lazerrio.application.MyApplication;
import br.com.lazerrio.component.ListOptionsComponent;
import br.com.lazerrio.ui.fragment.ListFragment;
import br.com.lazerrio.util.FragmentUtil;
import br.com.lazerrio.util.ShareUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createToolbarAndNavigationView();
        getComponents();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();

        if (id == R.id.nav_beach) {
            bundle.putString("option", "beach");
            FragmentUtil.changeFragment(R.id.conatiner, ListFragment.class, getFragmentManager(), false, bundle);
        } else if (id == R.id.nav_hotel) {
            bundle.putString("option", "hotel");
            FragmentUtil.changeFragment(R.id.conatiner, ListFragment.class, getFragmentManager(), false, bundle);
        } else if (id == R.id.nav_leisure) {

        } else if (id == R.id.nav_movie) {

        } else if (id == R.id.nav_museum) {

        } else if (id == R.id.nav_leisure) {

        } else if (id == R.id.nav_restaurant) {

        } else if (id == R.id.nav_shopping) {
            bundle.putString("option", "shopping");
            FragmentUtil.changeFragment(R.id.conatiner, ListFragment.class, getFragmentManager(), false, bundle);
        } else if (id == R.id.nav_sport) {
            bundle.putString("option", "sport");
            FragmentUtil.changeFragment(R.id.conatiner, ListFragment.class, getFragmentManager(), false, bundle);
        } else if (id == R.id.nav_share) {
            ShareUtil.directShare(this, getString(R.string.share), "");
        } else if (id == R.id.nav_theater) {
            bundle.putString("option", "theater");
            FragmentUtil.changeFragment(R.id.conatiner, ListFragment.class, getFragmentManager(), false, bundle);
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

    private void getComponents() {
        MyApplication app = (MyApplication) this.getApplication();
        ListOptionsComponent component = app.getListOptionsComponent();
        component.inject(this);
    }

}
