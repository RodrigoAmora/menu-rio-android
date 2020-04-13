package br.com.lazerrio.application;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import br.com.lazerrio.component.DaggerListOptionsComponent;
import br.com.lazerrio.component.ListOptionsComponent;

public class MyApplication extends Application {

    private ListOptionsComponent listOptionsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        createComponents();
        initHawk();
    }

    private void initHawk() {
        Hawk.init(this).build();
    }

    private void createComponents() {
        listOptionsComponent = DaggerListOptionsComponent.builder().build();
    }

    public ListOptionsComponent getListOptionsComponent() {
        return listOptionsComponent;
    }
}
