package br.com.lazerrio.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.lazerrio.R;
import br.com.lazerrio.application.MyApplication;
import br.com.lazerrio.callback.ListOptionsCallback;
import br.com.lazerrio.component.ListOptionsComponent;
import br.com.lazerrio.delegate.OptionDelegate;
import br.com.lazerrio.manager.CacheManager;
import br.com.lazerrio.model.Option;
import br.com.lazerrio.service.ListOpitonsService;
import br.com.lazerrio.ui.activity.MainActivity;
import br.com.lazerrio.ui.adapter.OptionAdapter;
import br.com.lazerrio.ui.listener.OnItemClickListener;
import br.com.lazerrio.util.FragmentUtil;
import br.com.lazerrio.util.GPSUtil;
import br.com.lazerrio.util.NetworkUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

public class ListOptionsFragment extends Fragment implements LocationListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, OptionDelegate<List<Option>> {

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab_list_options_nearby_to_me)
    FloatingActionButton fabListOptionsNextToMe;

    @BindView(R.id.fab_list_all_options)
    FloatingActionButton fabListAllOptions;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    ListOpitonsService service;

    private Call<List<Option>> call;
    private List<Option> optionList;
    private Unbinder unbinder;

    private CacheManager<List<Option>> cacheManager;
    private ListOptionsCallback callback;
    private MainActivity activity;

    private String TAG_CACHE = "opcoes_lista";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.callback = new ListOptionsCallback(this);
        this.cacheManager = new CacheManager<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMaintActivity();
        configureRecyclerView();
        getComponents();
        getListOptions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_option, menu);

        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        mSearchView.setQueryHint(getString(R.string.search_by_name));
        mSearchView.setOnCloseListener(this);
        mSearchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public boolean onQueryTextSubmit(String query) {
        List<Option> resultQuery = filterOptions(query);
        if (resultQuery != null && !resultQuery.isEmpty()) {
            populateRecyclerView(resultQuery);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Option> resultQuery = filterOptions(newText);
        if (resultQuery != null && !resultQuery.isEmpty()) {
            populateRecyclerView(resultQuery);
        }
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public void error() {
        this.progressBar.setVisibility(View.GONE);
        Toast.makeText(activity, getString(R.string.error_couldnt_was_possible_get_options), Toast.LENGTH_LONG).show();
    }

    @Override
    public void success(List<Option> options) {
        this.progressBar.setVisibility(View.GONE);
        this.optionList = options;
        if (this.optionList.isEmpty()) {
            Toast.makeText(activity, getString(R.string.no_result), Toast.LENGTH_LONG).show();
        } else {
            this.cacheManager.deleteCache(TAG_CACHE);
            this.cacheManager.saveCache(TAG_CACHE, this.optionList);
            populateRecyclerView(options);
        }
    }

    private void getComponents() {
        MyApplication myApplication = (MyApplication) activity.getApplication();
        ListOptionsComponent component = myApplication.getListOptionsComponent();
        component.inject(this);
    }

    private void getListOptions() {
        if (NetworkUtil.checkConnection(activity)) {
            String option = getArguments().getString("option");
            this.progressBar.setVisibility(View.VISIBLE);

            if (option != null) {
                switch (option) {
                    case "beach":
                        call = service.listAllBeaches();
                        break;

                    case "hotel":
                        call = service.listAllHotels();
                        break;

                    case "leisure":
                        call = service.listAllLeisure();
                        break;

                    case "movie":
                        call = service.listAllMovie();
                        break;

                    case "museum":
                        call = service.listAllMuseum();
                        break;

                    case "restaurant":
                        call = service.listAllRestaurants();
                        break;

                    case "shopping":
                        call = service.listAllShoppings();
                        break;

                    case "sport":
                        call = service.listAllSports();
                        break;

                    case "theater":
                        call = service.listAllTeathers();
                        break;
                }

                call.enqueue(callback);
            }
        } else {
            this.optionList = cacheManager.getCache(TAG_CACHE);
            populateRecyclerView(this.optionList);
            Toast.makeText(activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        }
    }

    private void configureRecyclerView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(activity);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void populateRecyclerView(List<Option> optionList) {
        OptionAdapter adapter = new OptionAdapter(activity, optionList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new OnItemClickListener<Option>() {
            @Override
            public void onItemClick(Option option) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("option", option);
                FragmentUtil.changeFragment(R.id.container, new DetailsFragment(), activity.getSupportFragmentManager(), true, bundle);
            }
        });
    }

    private List<Option> filterOptions(String text) {
        if (optionList != null && !optionList.isEmpty()) {
            List<Option> resultQuery = new ArrayList();
            for (Option option : optionList) {
                if (option.getName().toLowerCase().contains(text.toLowerCase())) {
                    resultQuery.add(option);
                }
            }
            return resultQuery;
        }
        return null;
    }

    @OnClick(R.id.fab_list_all_options)
    public void listAllOptions() {
        populateRecyclerView(optionList);
        fadeOut(fabListAllOptions);
    }

    @OnClick(R.id.fab_list_options_nearby_to_me)
    public void filterOptionsNearby() {
        fadeOut(fabListOptionsNextToMe);
        ArrayList optionsNearby = new ArrayList();
        Location myLocation = getLocation(activity);
        if (myLocation != null) {
            for (Option option : optionList) {
                Double lat = Double.parseDouble(option.getLat());
                Double lng = Double.parseDouble(option.getLng());

                Location locationOption = new Location(Context.LOCATION_SERVICE);
                locationOption.setLatitude(lat);
                locationOption.setLongitude(lng);

                Float distance = 0f;
                distance = myLocation.distanceTo(locationOption);
                if (distance <= 3500) {
                    optionsNearby.add(option);
                }
            }

            if (optionsNearby.isEmpty()) {
                Toast.makeText(activity, getString(R.string.no_options_nearby), Toast.LENGTH_LONG).show();
            } else {
                populateRecyclerView(optionsNearby);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(Context context) {
        if (GPSUtil.gpsIsEnable(context)) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    return location;
                }

                return null;
            }
        }

        Toast.makeText(context, getString(R.string.alert_gps_disabled), Toast.LENGTH_LONG).show();

        return null;
    }

    public void getMaintActivity() {
        activity = (MainActivity) getActivity();
    }

    private void fadeOut(View view) {
        Animation fadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
        view.startAnimation(fadeOut);
    }
}