package jwy.httpencapsulation.biz;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

import jwy.httpencapsulation.bean.HttpResult;

/**
 * Created by Administrator on 2017/4/19.
 * Volley框架具体操作类 如需要更换其他网络访问框架 按相同实现即可
 */
public class VolleyProxy implements IhttpProxy {

    public static RequestQueue mRequestQueue = null;
    private Gson mGson = null;

    public VolleyProxy(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mGson = new Gson();
    }

    /**
     * @param url      地址
     * @param params   接口需要的参数
     * @param callBack 回调
     */
    @Override
    public <T> void get(String url, Map<String, String> params, final CallBack<T> callBack) {
        String finalUrl = HttpUtils.appendParams(url, params);

        StringRequest request = new StringRequest(Request.Method.GET, finalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Type type = HttpUtils.getTType(callBack.getClass());
                if (type == String.class) {
                    //泛型是String，返回结果json字符串
                    callBack.onSuccess((T) s);
                } else {
                    //泛型是实体或者List<>
                    T t = mGson.fromJson(s, type);
                    callBack.onSuccess(t);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onFail(volleyError);

            }
        });
        mRequestQueue.add(request);
    }

    /**
     * post请求方式可以参考get请求
     */
    @Override
    public <T> void post(String url, Map<String, String> params, CallBack<T> callBack) {

    }


    @Override
    public void down() {

    }

    @Override
    public void up() {

    }
}
