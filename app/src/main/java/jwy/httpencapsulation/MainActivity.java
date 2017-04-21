package jwy.httpencapsulation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import jwy.httpencapsulation.bean.HttpResult;
import jwy.httpencapsulation.bean.News;
import jwy.httpencapsulation.biz.CallBack;
import jwy.httpencapsulation.biz.HttpUtils;

/**
 * 如需要使用MVP模式需要把MainActivity里访问数据相关操作转移到M层
 * 此次demo不考虑mvp模式，着重演示BaseApplication一句初始化代码就可实现3方框架的切换工作
 */
public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    //接口为百度百科API 测试用
    private String url = "http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?";
    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void start() {
        //简单的定义几个接口需要的参数
        params = new HashMap<>();
        params.put("scope", "103");
        params.put("format", "json");
        params.put("appid", "379020");
        params.put("bk_key", "Android");
        params.put("bk_length", "600");
        //链式调用类似builder模式 如需其他参数可以在HttpUtils类里添加
        HttpUtils.obtain().url(url)
                .addParams(params)
                .get(new CallBack<News>() {
                    @Override
                    public void onFail(Exception e) {
                        //失败需要处理的事情
                    }

                    @Override
                    public void onSuccess(final News news) {
                        //获取数据成功之后在主线程更新UI，只做测试
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(news.getDesc());
                            }
                        });
                    }
                });


    }


    public void init() {
        mButton = (Button) findViewById(R.id.main_btn);
        mTextView = (TextView) findViewById(R.id.main_text);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }
}
