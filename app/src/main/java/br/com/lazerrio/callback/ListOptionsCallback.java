package br.com.lazerrio.callback;

import java.util.List;

import br.com.lazerrio.delegate.CallbackDelegate;
import br.com.lazerrio.model.Option;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOptionsCallback implements Callback<List<Option>> {

    private CallbackDelegate callbackDelegate;

    public ListOptionsCallback(CallbackDelegate callbackDelegate) {
        this.callbackDelegate = callbackDelegate;
    }

    @Override
    public void onResponse(Call<List<Option>> call, Response<List<Option>> response) {
        if (response.isSuccessful()) {
            List<Option> result = response.body();
            if (result != null) {
                this.callbackDelegate.success(result);
            } else {
                this.callbackDelegate.error();
            }
        } else {
            this.callbackDelegate.error();
        }
    }

    @Override
    public void onFailure(Call<List<Option>> call, Throwable t) {
        this.callbackDelegate.error();
    }

}
