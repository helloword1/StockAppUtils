package example.com.stockapp.https;

import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.RequestParam;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/14.
 */

public interface AddGoodsApi {
    @FormUrlEncoded
    @POST("Item/Add")
    Observable<BaseEntity<Object>> addGoods(@FieldMap RequestParam param);
}
