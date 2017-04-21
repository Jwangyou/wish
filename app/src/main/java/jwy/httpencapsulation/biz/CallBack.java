package jwy.httpencapsulation.biz;

/**
 * Created by Administrator on 2017/4/19.
 */

public abstract class CallBack<T> {
    public abstract void onFail(Exception e);

    public abstract void onSuccess(T result);
}
