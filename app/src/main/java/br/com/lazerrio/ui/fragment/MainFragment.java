package br.com.lazerrio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.lazerrio.R;
import br.com.lazerrio.ui.activity.MainActivity;
import br.com.lazerrio.util.FragmentUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    @BindView(R.id.beach)
    LinearLayout beach;
    @BindView(R.id.hotel)
    LinearLayout hotel;
    @BindView(R.id.leisure)
    LinearLayout leisure;
    @BindView(R.id.movie)
    LinearLayout movie;
    @BindView(R.id.restaurant)
    LinearLayout restaurant;
    @BindView(R.id.shopping_mall)
    LinearLayout shoppingMall;
    @BindView(R.id.sport)
    LinearLayout sport;
    @BindView(R.id.theater)
    LinearLayout theater;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.beach, R.id.hotel, R.id.leisure, R.id.movie, R.id.museum, R.id.restaurant, R.id.shopping_mall, R.id.sport, R.id.theater})
    public void irOpcoes(View view) {
        MainActivity activity = (MainActivity) getActivity();
        Bundle bundle = new Bundle();

        int viewId = view.getId();
        switch (viewId) {
            case R.id.beach:
                bundle.putString("option", "beach");
                break;

            case R.id.hotel:
                bundle.putString("option", "hotel");
                break;

            case R.id.leisure:
                bundle.putString("option", "leisure");
                break;

            case R.id.movie:
                bundle.putString("option", "movie");
                break;

            case R.id.museum:
                bundle.putString("option", "museum");
                break;

            case R.id.restaurant:
                bundle.putString("option", "restaurant");
                break;

            case R.id.shopping_mall:
                bundle.putString("option", "shopping");
                break;

            case R.id.sport:
                bundle.putString("option", "sport");
                break;

            case R.id.theater:
                bundle.putString("option", "theater");
                break;
        }

        FragmentUtil.changeFragment(R.id.conatiner, new ListOptionsFragment(), activity.getSupportFragmentManager(), true, bundle);
    }

}
