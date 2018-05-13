package br.com.lazerrio.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class ListFragment extends Fragment implements Delegate {

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;

    private Call<List<Option>> call;
    private List<Option> optionList;
    private Unbinder unbinder;

    ListOptionsCallback callback;

    @Inject
    ListOpitonsService service;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container,	false);

        configureRecyclerView();

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
        getComponents();
        getListOptions();
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
        populateRecyclerView();
    }

    private void configureRecyclerView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayout);
    }

    private void getComponents() {
        MyApplication app = (MyApplication) getActivity().getApplication();
        ListOptionsComponent component = app.getListOptionsComponent();
        component.inject(this);
    }

    private void getListOptions() {
        String option = getArguments().getString("option");
        if (NetworkUtil.checkConnection(getActivity())) {
            ProgressDiaologUtil.showProgressDiaolg(getActivity(), "", getString(R.string.wait), false);

            if (option.equals("beach")) {
                call = service.listAllBeahes();
            } else if (option.equals("hotel")) {
                call = service.listAllHotels();
            } else if (option.equals("leisure")) {
                call = service.listAllLeisures();
            } else if (option.equals("movie")) {
                call = service.listAllMovie();
            } else if (option.equals("museum")) {
                call = service.listAllMuseum();
            } else if (option.equals("restaurant")) {
                call = service.listAllRestaurants();
            } else if (option.equals("shopping")) {
                call = service.listAllShoppings();
            } else if (option.equals("sport")) {
                call = service.listAllSports();
            } else if (option.equals("theater")) {
                call = service.listAllTeathers();
            }

            call.enqueue(callback);
        } else {
            Toast.makeText(getActivity(), getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        }
    }

    private void populateRecyclerView() {
        OptionAdapter adapter = new OptionAdapter(getActivity(), optionList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Option option) {
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", Double.parseDouble(option.getLat()));
                bundle.putDouble("lng", Double.parseDouble(option.getLng()));
                bundle.putString("desc", option.getDescription());
                bundle.putString("name", option.getName());
                bundle.putString("photo", option.getPhoto());

                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.conatiner, detailsFragment).commit();
            }
        });
    }

}
