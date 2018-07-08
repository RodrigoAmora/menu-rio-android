package br.com.lazerrio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.com.lazerrio.R;
import br.com.lazerrio.model.Option;

public class AddressInfoWindowCustom implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private LayoutInflater inflater;

    private Option machineShop;

    TextView address;

    TextView name;

    public AddressInfoWindowCustom(Context context, Option machineShop) {
        this.context = context;
        this.machineShop = machineShop;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.address_info_window_adapter, null);

        name = view.findViewById(R.id.address);
        name.setText(machineShop.getAddress());

        address = view.findViewById(R.id.name);
        address.setText(machineShop.getName());

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
