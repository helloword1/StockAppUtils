package example.com.stockapp.view.tools;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import cxx.utils.NotNull;
import example.com.stockapp.https.Md5;
import example.com.stockapp.view.activities.BaseActivity;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static example.com.stockapp.view.tools.Constant.TOKEN;

/**
 * Created by Administrator on 2017/10/11.
 */

public class SysInterceptor implements Interceptor {
    private  Context context;

    public SysInterceptor(Context context) {
       this.context=context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url().newBuilder() // add common parameter
                .build();
        String token= ((BaseActivity) context).preferences.getStringValue(TOKEN);
        if (!NotNull.isNotNull(token)){
            token="";
        }
        String value = token + "|" + getSign() + "|" + getTime();
        Log.d("intercept", "------->>" + value);
        Request build = request.newBuilder()
                // add common header
                .addHeader("APP-AUTH-HEADER", value).url(httpUrl).build();
        Response response = chain.proceed(build);
        return response;
    }

    private String getToken() {
        return "";//登录token为空
    }

    private String getTime() {
        return "" + System.currentTimeMillis();
    }

    private String getSign() {
        return Md5.getMd5(getToken() + getTime() + Constant.KEY);
    }
}
