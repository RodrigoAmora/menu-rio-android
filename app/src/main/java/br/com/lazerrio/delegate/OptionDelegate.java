package br.com.lazerrio.delegate;

public interface OptionDelegate<T> {
    void error();
    void success(T t);
}
