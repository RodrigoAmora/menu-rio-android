package br.com.lazerrio.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.lazerrio.R;
import br.com.lazerrio.application.MyApplication;
import br.com.lazerrio.callback.ListOptionsCallback;
import br.com.lazerrio.component.ListOptionsComponent;
import br.com.lazerrio.delegate.CallbackDelegate;
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

public class ListOptionsFragment extends Fragment implements LocationListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, CallbackDelegate<List<Option>> {

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab_list_options_nearby_to_me)
    FloatingActionButton fabListOptionsNextToMe;

    @BindView(R.id.fab_list_all_options)
    FloatingActionButton fabListAllOptions;

    @BindView(R.id.progress_bar)
    LinearLayout progressBar;

    private Call<List<Option>> call;
    private List<Option> optionList;
    private Unbinder unbinder;

    @Inject
    ListOpitonsService service;
    ListOptionsCallback callback;
    MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        callback = new ListOptionsCallback(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container,	false);
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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_option, menu);

        android.support.v7.widget.SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
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
        progressBar.setVisibility(View.GONE);
        Toast.makeText(activity, getString(R.string.error_couldnt_was_possible_get_options), Toast.LENGTH_LONG).show();
    }

    @Override
    public void success(List<Option> options) {
        progressBar.setVisibility(View.GONE);
        optionList = options;
        if (optionList.isEmpty()) {
            Toast.makeText(activity, getString(R.string.no_result), Toast.LENGTH_LONG).show();
        } else {
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
            progressBar.setVisibility(View.VISIBLE);
            switch (option) {
                case "beach":
                    call = service.listAllBeahes();
                    break;

                case "hotel":
                    call = service.listAllHotels();
                    break;

                case "leisure":
                    call = service.listAllLeisures();
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
        } else {
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
                FragmentUtil.changeFragment(R.id.conatiner, new DetailsFragment(), activity.getSupportFragmentManager(), false, bundle);
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
    }

    @OnClick(R.id.fab_list_options_nearby_to_me)
    public void filterOptionsNearby() {
        List<Option> optionsNearby = new ArrayList();

        for (Option option : optionList) {
            Double lat = Double.parseDouble(option.getLat());
            Double lng = Double.parseDouble(option.getLng());

            Location locationOption = new Location(Context.LOCATION_SERVICE);
            locationOption.setLatitude(lat);
            locationOption.setLongitude(lng);
            Location myLocation = getLocation(activity);

            Float distance = 0f;
            if (myLocation != null) {
                distance = myLocation.distanceTo(locationOption);
            }
            if (distance <= 3000) {
                optionsNearby.add(option);
            }
        }

        if (optionsNearby.isEmpty()) {
            Toast.makeText(activity, getString(R.string.no_options_nearby), Toast.LENGTH_LONG).show();
        } else {
            populateRecyclerView(optionsNearby);
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(Context context) {
        if (GPSUtil.gpsIsEnable(activity)) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                return location;
            }

            return null;
        }

        Toast.makeText(activity, getString(R.string.alert_gps_disabled), Toast.LENGTH_LONG).show();
        return null;
    }

    public MainActivity getMaintActivity() {
        activity = (MainActivity) getActivity();
        return activity;
    }

}