package jwy.httpencapsulation.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface IhttpProxy {

    public <T> void get(String url, Map<String, String> params, CallBack<T> callBack);

    public <T> void post(String url, Map<String, String> params, CallBack<T> callBack);

    //如果需要其他网络访问请求在此处添加接口，给予相应的实现即可
    public void down();

    public void up();
}
