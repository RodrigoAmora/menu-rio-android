package br.com.lazerrio.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.lazerrio.R;
import br.com.lazerrio.application.MyApplication;
import br.com.lazerrio.callback.ListOptionsCallback;
import br.com.lazerrio.component.ListOptionsComponent;
import br.com.lazerrio.delegate.Delegate;
import br.com.lazerrio.model.Option;
import br.com.lazerrio.service.ListOpitonsService;
import br.com.lazerrio.ui.adapter.OptionAdapter;
import br.com.lazerrio.ui.listener.OnItemClickListener;
import br.com.lazerrio.util.NetworkUtil;
import br.com.lazerrio.util.ProgressDiaologUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

public class ListOptionsFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, Delegate {

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;

    private Call<List<Option>> call;
    private List<Option> optionList;
    private Unbinder unbinder;

    ListOptionsCallback callback;

    @Inject
    ListOpitonsService service;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container,	false);

        unbinder = ButterKnife.bind(this, rootView);
        callback = new ListOptionsCallback(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureRecyclerView();
        getComponents();
        getListOptions();
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
    public boolean onQueryTextSubmit(String query) {
        List<Option> resultQuery = filterOptions(query);
        if (resultQuery != null) {
            populateRecyclerView(resultQuery);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Option> resultQuery = filterOptions(newText);
        if (resultQuery != null) {
            populateRecyclerView(resultQuery);
        }
        return false;
    }

    @Override
    public boolean onClose() {
        populateRecyclerView(optionList);
        return false;
    }

    @Override
    public void error() {
        ProgressDiaologUtil.dimissProgressDialog();
        Toast.makeText(getActivity(), getString(R.string.error_couldnt_was_possible_get_options), Toast.LENGTH_LONG).show();
    }

    @Override
    public void success() {
        ProgressDiaologUtil.dimissProgressDialog();
        optionList = callback.getOptions();
        populateRecyclerView(optionList);
    }

    private void getComponents() {
        MyApplication app = (MyApplication) getActivity().getApplication();
        ListOptionsComponent component = app.getListOptionsComponent();
        component.inject(this);
    }

    private void getListOptions() {
        if (NetworkUtil.checkConnection(getActivity())) {
            String option = getArguments().getString("option");
            ProgressDiaologUtil.showProgressDiaolg(getActivity(), "", getString(R.string.wait), false);

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
            Toast.makeText(getActivity(), getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        }
    }

    private void configureRecyclerView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayout);
    }

    private void populateRecyclerView(List<Option> optionList) {
        OptionAdapter adapter = new OptionAdapter(getActivity(), optionList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Option option) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("option", option);

                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.conatiner, detailsFragment).commit();
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

}