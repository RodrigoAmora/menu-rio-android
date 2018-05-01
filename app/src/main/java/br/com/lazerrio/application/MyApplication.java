package br.com.lazerrio.application;

import android.app.Application;

import br.com.lazerrio.component.DaggerListOptionsComponent;
import br.com.lazerrio.component.ListOptionsComponent;
import br.com.lazerrio.module.ListOptionsModule;

public class MyApplication extends Application {

    private ListOptionsComponent listOptionsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        listOptionsComponent = DaggerListOptionsComponent.builder()
                .listOptionsModule(new ListOptionsModule(this))
                .build();
    }

    public ListOptionsComponent getListOptionsComponent() {
        return listOptionsComponent;
    }

}
