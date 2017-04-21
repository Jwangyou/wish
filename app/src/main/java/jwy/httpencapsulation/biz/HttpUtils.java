package jwy.httpencapsulation.biz;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/19.
 * 框架封装的主要操作类
 */

public class HttpUtils {
    //get请求
    private static final int REQUEST_TYPE_GET = 1;
    //post请求
    private static final int REQUEST_TYPE_POST = 2;
    //请求类型 默认为get请求
    private int mRequestType = REQUEST_TYPE_GET;
    public static IhttpProxy mIhttpProxy = null;
    public static String mUrl;
    //此map参数的value可以用objec，本次使用String做测试
    public static Map<String, String> mParams;

    //初始化 CallBack 防止出现空指针
    private CallBack defaultCallBack = new CallBack() {
        @Override
        public void onFail(Exception e) {

        }

        @Override
        public void onSuccess(Object o) {

        }
    };

    public static HttpUtils obtain() {
        return new HttpUtils();
    }

    public HttpUtils() {
        mParams = new HashMap<String, String>();
    }

    public static void init(IhttpProxy ihttpProxy) {
        mIhttpProxy = ihttpProxy;
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils addParams(Map<String, String> params) {
        mParams = params;
        return this;
    }

    public <T> void get(CallBack<T> callBack) {
        mRequestType = REQUEST_TYPE_GET;
        request(mRequestType, callBack);
    }

    public <T> void post(CallBack<T> callBack) {
        mRequestType = REQUEST_TYPE_POST;
        request(mRequestType, callBack);
    }

    /**
     * 判断是get还是post请求
     * @param mRequestType 请求类型
     * @param callBack     请求回调
     */
    private <T> void request(int mRequestType, CallBack<T> callBack) {
        if (callBack == null) {
            callBack = defaultCallBack;
        }
        switch (mRequestType) {
            case REQUEST_TYPE_GET:
                mIhttpProxy.get(mUrl, mParams, callBack);
                break;
            case REQUEST_TYPE_POST:
                mIhttpProxy.get(mUrl, mParams, callBack);
                break;
        }
    }

    /**
     * 拼接请求地址url与参数
     *
     * @param url    传入的地址
     * @param params 所需要的参数
     */
    public static String appendParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder urlStringBuilder = new StringBuilder(url);
        //判断地址末尾有无？ 如果没有拼接
        if (urlStringBuilder.indexOf("?") <= 0) {
            urlStringBuilder.append("?");
        } else {
            if (!urlStringBuilder.toString().endsWith("?")) {
                urlStringBuilder.append("&");
            }
        }
        //地址与参数拼接
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlStringBuilder.append(entry.getKey()).append("=").append(encode(entry.getValue().toString())).append("&");
        }
        //删除掉末尾多余的&
        urlStringBuilder.deleteCharAt(urlStringBuilder.length() - 1);
        return urlStringBuilder.toString();
    }

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取类的具体类型
     *
     * @param clazz 传入的相关类
     */
    public static Type getTType(Class<?> clazz) {
        Type mySuperClassType = clazz.getGenericSuperclass();
        Type[] types = ((ParameterizedType) mySuperClassType).getActualTypeArguments();
        if (types != null && types.length > 0) {
            //T
            return types[0];
        }
        return null;
    }
}
