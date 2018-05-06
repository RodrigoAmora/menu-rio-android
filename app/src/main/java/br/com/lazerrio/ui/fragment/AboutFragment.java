package br.com.lazerrio.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.lazerrio.R;
import br.com.lazerrio.util.PackageInfoUtil;

public class AboutFragment extends Fragment {

    private TextView versiom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        versiom = rootView.findViewById(R.id.versiom);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        versiom.setText(PackageInfoUtil.getVersionName(getActivity()));
    }

}
