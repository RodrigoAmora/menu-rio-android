package br.com.lazerrio.ui.activity;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.List;

import br.com.lazerrio.R;
import br.com.lazerrio.factory.ShortcutFactory;
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
        createToolbarAndNavigationView();

        if (!checkPermission()) {
            PermissionUtil.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION});
        }

        if (Build.VERSION.SDK_INT >= 26) {
            createShorcut();
            checkOptionInIntent();
        } else {
            changeFragment(new MainFragment(), null);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int fragmentCount = getSupportFragmentManager().getBackStackEntryCount();
            if (fragmentCount > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            changeFragment(new AboutFragment(), null);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();

        switch (id) {
            case R.id.nav_about:
                changeFragment(new AboutFragment(), bundle);
                break;

            case R.id.nav_beach:
                bundle.putString("option", "beach");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_hotel:
                bundle.putString("option", "hotel");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_leisure:
                bundle.putString("option", "leisure");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_movie:
                bundle.putString("option", "movie");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_museum:
                bundle.putString("option", "museum");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_restaurant:
                bundle.putString("option", "restaurant");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_share:
                String shareText = getString(R.string.download_the_app)+"\n"
                                    +getString(R.string.link_app);
                ShareUtil.directShare(this, getString(R.string.share), shareText);
                break;

            case R.id.nav_shopping:
                bundle.putString("option", "shopping");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_sport:
                bundle.putString("option", "sport");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            case R.id.nav_theater:
                bundle.putString("option", "theater");
                changeFragment(new ListOptionsFragment(), bundle);
                break;

            default:
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

    private void changeFragment(Fragment fragment, Bundle bundle) {
        FragmentUtil.changeFragment(R.id.conatiner, fragment, getSupportFragmentManager(), true, bundle);
    }

    private void checkOptionInIntent() {
        String option = getIntent().getStringExtra("option");
        if (option != null && !option.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString("option", option);
            changeFragment(new ListOptionsFragment(), bundle);
        } else {
            changeFragment(new MainFragment(), null);
        }
    }

    @TargetApi(26)
    private void createShorcut() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        String shortLabels[] = {getString(R.string.leisure), getString(R.string.movies),
                getString(R.string.restaurants), getString(R.string.shopping_mall), getString(R.string.sports)};

        String disabledMessage[] = {getString(R.string.shortcut_info_leisures),
                getString(R.string.shortcut_info_movies), getString(R.string.shortcut_info_restaurants), getString(R.string.shortcut_info_shopping_mall),
                getString(R.string.shortcut_info_sport)};

        String options[] = {"leisure", "movie", "restaurant", "shopping", "sport"};

        Integer icons[] = {R.mipmap.opcoes, R.mipmap.cine, R.mipmap.chef, R.mipmap.shopping, R.mipmap.esportes};

        List<ShortcutInfo> shortcutInfos = ShortcutFactory.createShortcutInfos(this, shortLabels, disabledMessage, options, icons);
        shortcutManager.setDynamicShortcuts(shortcutInfos);
    }

}
