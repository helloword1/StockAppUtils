package example.com.stockapp.https;

import example.com.stockapp.entries.BaseEntity;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/14.
 */

public interface AddGoodsApi {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("Item/Save")
    Observable<BaseEntity<Integer>> addGoods(@Body RequestBody param);
}
