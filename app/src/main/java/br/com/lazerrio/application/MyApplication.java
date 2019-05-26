package br.com.lazerrio.application;

import android.app.Application;

import br.com.lazerrio.component.DaggerListOptionsComponent;
import br.com.lazerrio.component.ListOptionsComponent;

public class MyApplication extends Application {

    private ListOptionsComponent listOptionsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        createComponents();
    }

    private void createComponents() {
        listOptionsComponent = DaggerListOptionsComponent.builder()
                                .build();
    }

    public ListOptionsComponent getListOptionsComponent() {
        return listOptionsComponent;
    }

}
