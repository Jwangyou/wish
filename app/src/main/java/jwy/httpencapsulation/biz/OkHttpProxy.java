package jwy.httpencapsulation.biz;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/19.
 * OKHttp框架具体操作类，如需要更换其他网络访问框架 按相同实现即可
 */

public class OkHttpProxy implements IhttpProxy {
    private OkHttpClient mOkHttpClient;
    private Gson mGson = null;

    public OkHttpProxy() {
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
    }


    @Override
    public <T> void get(String url, Map<String, String> params, final CallBack<T> callBack) {
        String finalUrl = HttpUtils.appendParams(url, params);
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OkHttpProxy", "IOException:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
//                    Log.e("OkHttpProxy", "code:" + response.code());
//                    Log.e("OkHttpProxy", "body:" + response.body().string());
                    Type type = HttpUtils.getTType(callBack.getClass());
                    //可以试着把返回response.code()和response.body().string()存到HttpResult类中做中转
                    if (type == String.class) {
                        //泛型是String，返回结果json字符串
                        callBack.onSuccess((T) response.body().string());
                    } else {
                        //泛型是实体或者List<>
                        T t = mGson.fromJson(response.body().string(), type);
                        callBack.onSuccess(t);
                    }
                }
            }
        });
    }

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
