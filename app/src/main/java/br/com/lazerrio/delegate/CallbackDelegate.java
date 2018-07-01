package br.com.lazerrio.delegate;

public interface CallbackDelegate<T> {

    void error();
    void success(T t);

}
