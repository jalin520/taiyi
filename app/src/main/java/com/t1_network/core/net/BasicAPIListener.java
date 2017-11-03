package com.t1_network.core.net;

/**
 * Created by David on 15/10/21.
 */
public interface BasicAPIListener {

    public void requestSuccess(String data);

    public void requestError(long code, String msg);

}
