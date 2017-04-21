package jwy.httpencapsulation.base;

import android.app.Application;

import jwy.httpencapsulation.biz.HttpUtils;
import jwy.httpencapsulation.biz.VolleyProxy;

/**
 * Created by Administrator on 2017/4/19.
 */

public class BaseApplication extends Application {
    /**
     * onCreate 只是做了简单的Volley框架和OKHttp框架的切换工作，如有其它框架只需要初始化框架就可以替换
     */
    @Override
    public void onCreate() {

        super.onCreate();
        HttpUtils.init(new VolleyProxy(this));
//      HttpUtils.init(new OkHttpProxy());
    }
}
