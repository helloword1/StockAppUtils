package example.com.stockapp.https;

import java.util.List;

import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.entries.SearchForCode;
import example.com.stockapp.entries.UserInfo;
import example.com.stockapp.entries.UserList;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/10.
 */

public interface GetUserInfoApi {
    @GET("User/GetUserInfo")
    Observable<BaseEntity<UserInfo>> getUserInfo(@QueryMap RequestParam param);

    @GET("stock/SelectViewPageForIndate")
    Observable<BaseEntity<InventoryEntity>> getOutDateGods(@QueryMap RequestParam param);

    @GET("User/SelectAll")
    Observable<BaseEntity<List<UserList>>> getUserList(@QueryMap RequestParam param);

    @GET("item/ExistsByBarcode")
    Observable<BaseEntity<Boolean>> getCodeStr(@QueryMap RequestParam param);

    @GET("Item/GetViewWithBatchNoByBarcode")
    Observable<BaseEntity<SearchForCode>> getOutGoods(@QueryMap RequestParam param);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("Stockout/AddEx")
    Observable<BaseEntity<Integer>> AddOutGoods(@Body RequestBody param);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("Stockin/AddEx")
    Observable<BaseEntity<Integer>> AddInGoods(@Body RequestBody param);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("Inventory/AddEx2")
    Observable<BaseEntity<Object>> InvertoryGoods(@Body RequestBody param);
}
