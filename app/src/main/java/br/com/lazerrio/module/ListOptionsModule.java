package br.com.lazerrio.module;

import br.com.lazerrio.factory.RetrofitFactory;
import br.com.lazerrio.service.ListOpitonsService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ListOptionsModule {

    @Provides
    public ListOpitonsService getListOptionsService() {
        Retrofit retrofit = RetrofitFactory.createRetrofit();
        ListOpitonsService service = retrofit.create(ListOpitonsService.class);
        return service;
    }

}
