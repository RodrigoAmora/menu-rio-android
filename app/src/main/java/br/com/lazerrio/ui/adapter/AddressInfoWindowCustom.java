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
    private LayoutInflater layoutInflater;

    private Option option;

    private TextView tvAddress, tvName;

    public AddressInfoWindowCustom(Context context, Option option) {
        this.context = context;
        this.option = option;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.address_info_window_adapter, null);

        tvName = view.findViewById(R.id.name);
        tvName.setText(option.getName());

        String address = option.getAddress()+", "
                            +option.getNumber()+" - "
                            +option.getNeighborhood();

        tvAddress = view.findViewById(R.id.address);
        tvAddress.setText(address);

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
