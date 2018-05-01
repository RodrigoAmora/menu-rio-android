package br.com.lazerrio.component;

import br.com.lazerrio.module.ListOptionsModule;
import br.com.lazerrio.ui.activity.MainActivity;
import br.com.lazerrio.ui.fragment.ListFragment;
import dagger.Component;

@Component(modules = ListOptionsModule.class)
public interface ListOptionsComponent {

    public void inject(ListFragment fragment);
    public void inject(MainActivity activity);

}
