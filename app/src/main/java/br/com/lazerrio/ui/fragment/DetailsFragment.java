package br.com.lazerrio.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import br.com.lazerrio.R;
import br.com.lazerrio.model.Option;
import br.com.lazerrio.ui.activity.MainActivity;
import br.com.lazerrio.ui.adapter.AddressInfoWindowCustom;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsFragment extends Fragment implements com.google.android.gms.maps.OnMapReadyCallback {

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.name)
    TextView name;

    MainActivity activity;
    private Unbinder unbinder;

    private Option option;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        option = (Option) getArguments().getSerializable("option");

        description.setText(option.getDescription());
        name.setText(option.getName());

        String urlPhoto = option.getDescription();
        if (urlPhoto == null || urlPhoto.isEmpty()) {
            Picasso.get().load(R.drawable.no_photo).placeholder(R.mipmap.ic_launcher).into(photo);
        } else {
            Picasso.get().load(urlPhoto).placeholder(R.mipmap.ic_launcher).into(photo);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(Double.parseDouble(option.getLat()), Double.parseDouble(option.getLng()));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        map.moveCamera(update);
        map.addMarker(new MarkerOptions()
                .title(getArguments().getString("name"))
                //.snippet(getArguments().getString("desc"))
                .position(latLng));
        map.setMyLocationEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        AddressInfoWindowCustom infoWindowCustom = new AddressInfoWindowCustom(activity, option);
        map.setInfoWindowAdapter(infoWindowCustom);
    }

}
