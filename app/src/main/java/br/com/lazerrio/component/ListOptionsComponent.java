package br.com.lazerrio.component;

import br.com.lazerrio.module.ListOptionsModule;
import br.com.lazerrio.ui.fragment.ListOptionsFragment;
import dagger.Component;

@Component(modules = ListOptionsModule.class)
public interface ListOptionsComponent {

    public void inject(ListOptionsFragment fragment);
    //public void inject(MainActivity activity);

}
