package br.com.lazerrio.module;

import br.com.lazerrio.BuildConfig;
import br.com.lazerrio.application.MyApplication;
import br.com.lazerrio.service.ListOpitonsService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ListOptionsModule {

    private MyApplication application;

    public ListOptionsModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    public ListOpitonsService getListOptionsService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_LAZER_RIO_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ListOpitonsService service = retrofit.create(ListOpitonsService.class);
        return service;
    }

}
