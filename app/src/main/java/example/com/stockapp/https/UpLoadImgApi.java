package example.com.stockapp.https;

import java.util.List;

import example.com.stockapp.entries.BaseEntity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/10.
 */

public interface UpLoadImgApi {
    @Multipart
    @POST("UpLoadImg")
    Observable<BaseEntity<List<String>>> upLoadImg(@Part("description") RequestBody description,
                                                   @Part MultipartBody.Part file);

}
