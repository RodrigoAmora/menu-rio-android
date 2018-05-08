package br.com.lazerrio.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import br.com.lazerrio.R;

public class DetailsFragment extends Fragment implements com.google.android.gms.maps.OnMapReadyCallback {

    ImageView photo;
    TextView description, name;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        description = rootView.findViewById(R.id.description);
        description.setText(getArguments().getString("desc"));

        name = rootView.findViewById(R.id.name);
        name.setText(getArguments().getString("name"));

        photo = rootView.findViewById(R.id.photo);

        String urlPhoto = getArguments().getString("photo");
        if (urlPhoto == null || urlPhoto.isEmpty()) {
            Picasso.get().load(R.drawable.no_photo).placeholder(R.mipmap.ic_launcher).into(photo);
        } else {
            Picasso.get().load(urlPhoto).placeholder(R.mipmap.ic_launcher).into(photo);
        }

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(false);

        LatLng latLng = new LatLng(getArguments().getDouble("lat"), getArguments().getDouble("lng"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        map.moveCamera(update);
        map.addMarker(new MarkerOptions()
                .title(getArguments().getString("name"))
                //.snippet(getArguments().getString("desc"))
                .position(latLng));

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

}
