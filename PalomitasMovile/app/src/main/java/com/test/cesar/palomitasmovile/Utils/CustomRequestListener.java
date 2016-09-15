package com.test.cesar.palomitasmovile.Utils;

/**
 * Created by Cesar on 14/09/2016.
 */
public interface CustomRequestListener<T> {

    void onSuccess(T object);
    void onError(String error);

}
