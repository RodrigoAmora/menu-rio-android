package br.com.lazerrio.module;

import br.com.lazerrio.BuildConfig;
import br.com.lazerrio.factory.RetrofitFactory;
import br.com.lazerrio.service.ListOptionsService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ListOptionsModule {
    @Provides
    public ListOptionsService getListOptionsService() {
        Retrofit retrofit = RetrofitFactory.createRetrofit(BuildConfig.BASE_URL_LAZER_RIO_API);
        return retrofit.create(ListOptionsService.class);
    }
}
