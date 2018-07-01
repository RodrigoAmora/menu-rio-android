package br.com.lazerrio.factory;

import br.com.lazerrio.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    public static Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_LAZER_RIO_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
