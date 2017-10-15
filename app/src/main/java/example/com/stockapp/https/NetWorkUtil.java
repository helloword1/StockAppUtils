package example.com.stockapp.https;


import example.com.stockapp.view.tools.Constant;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
public class NetWorkUtil {
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static Interceptor netCacheInterceptor1;
    private static LoginGetTokenApi loginGetTokenApi;
    private static GetUserInfoApi getUserInfoApi;
    private static UpLoadImgApi upLoadImgApi;
    private static AddGoodsApi addGoodsApi;
    private static GetInventoryApi getInventoryApi;
    /**
     * 初始化okhttp
     */
    public static void initOkhttp() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(netCacheInterceptor1).addInterceptor(httpLoggingInterceptor).build();
        }

    }

    /**
     * 获取登录token
     *
     * @return
     */
    public static LoginGetTokenApi getTokenApi(Interceptor netCacheInterceptor) {
       netCacheInterceptor1 =netCacheInterceptor;
        initOkhttp();
        if (loginGetTokenApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            loginGetTokenApi = retrofit.create(LoginGetTokenApi.class);
        }
        return loginGetTokenApi;
    }/**
     * 获取登录用户信息
     *
     * @return
     */
    public static GetUserInfoApi getUserInfoApi(Interceptor netCacheInterceptor) {
       netCacheInterceptor1 =netCacheInterceptor;
        initOkhttp();
        if (getUserInfoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            getUserInfoApi = retrofit.create(GetUserInfoApi.class);
        }
        return getUserInfoApi;
    }
    /**
     * 上传图片
     *
     * @return
     */
    public static UpLoadImgApi getUpLoadImageApi(Interceptor netCacheInterceptor) {
        netCacheInterceptor1 =netCacheInterceptor;
        initOkhttp();
        if (upLoadImgApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_IMG_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            upLoadImgApi = retrofit.create(UpLoadImgApi.class);
        }
        return upLoadImgApi;
    }
    /**
     * 添加编辑商品
     *
     * @return
     */
    public static AddGoodsApi addGoodsApi(Interceptor netCacheInterceptor) {
        netCacheInterceptor1 =netCacheInterceptor;
        initOkhttp();
        if (addGoodsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            addGoodsApi = retrofit.create(AddGoodsApi.class);
        }
        return addGoodsApi;
    }
    /**
     * 获取库存商品
     *
     * @return
     */
    public static GetInventoryApi getInventoryApi(Interceptor netCacheInterceptor) {
        netCacheInterceptor1 =netCacheInterceptor;
        initOkhttp();
        if (getInventoryApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            getInventoryApi = retrofit.create(GetInventoryApi.class);
        }
        return getInventoryApi;
    }

}
