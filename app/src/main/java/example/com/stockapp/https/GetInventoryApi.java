package example.com.stockapp.https;

import java.util.List;

import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.GoodsDetails;
import example.com.stockapp.entries.Grassify;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.entries.RequestParam;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/10.
 */

public interface GetInventoryApi {
    @GET("Stock/SelectViewPage")
    Observable<BaseEntity<InventoryEntity>> getUserInfo(@QueryMap RequestParam param);

    @GET("Item/GetViewByID")
    Observable<BaseEntity<GoodsDetails>> getGoodDetail(@QueryMap RequestParam param);

    @FormUrlEncoded
    @POST("Item/Update")
    Observable<BaseEntity<Object>> upDataGoods(@FieldMap RequestParam param);

    @GET("ItemCategory/SelectAll")
    Observable<BaseEntity<List<Grassify>>> getGlassify(@QueryMap RequestParam param);
}
