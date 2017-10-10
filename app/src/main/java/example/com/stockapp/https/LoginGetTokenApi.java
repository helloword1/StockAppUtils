package example.com.stockapp.https;

import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.RequestParam;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/10.
 */

public interface LoginGetTokenApi {
    @FormUrlEncoded
    @POST("User/Login")
    Observable<BaseEntity> getToken(@FieldMap RequestParam param);

}
