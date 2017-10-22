package example.com.stockapp.view.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import cxx.utils.NotNull;
import cxx.utils.StringUtils;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.https.DefaultObserver;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.graphs.LoadingDialog;
import example.com.stockapp.view.graphs.PopWindowUtils;
import example.com.stockapp.view.tools.LogUtils;
import example.com.stockapp.view.tools.SAApplication;
import example.com.stockapp.view.tools.SysInterceptor;
import example.com.stockapp.view.tools.SysSharedPreferences;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected android.widget.FrameLayout ll;
    private android.widget.FrameLayout flbase;
    private LayoutInflater inflation;
    protected android.widget.Button titleBack;
    protected android.widget.TextView title;
    protected LoadingDialog progressDialog;// 加载对话框
    protected android.widget.ImageView titleRight;
    public SysSharedPreferences preferences;// 配置文件
    protected View baseLine;
    protected String fileImagePath;
    private Bitmap bitmap;
    private SAApplication saApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

        setContentView(R.layout.base_content_view);
        preferences = SysSharedPreferences.getInstance(this);
        this.flbase = (FrameLayout) findViewById(R.id.fl_base_content_view);
        this.ll = (FrameLayout) findViewById(R.id.ll_title);
        baseLine = findViewById(R.id.baseLine);
        inflation = LayoutInflater.from(this);
        saApplication = (SAApplication) getApplication();
        saApplication.addActivity(this);
        //滑动删除库初始化
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);

        initTitleView();
        initContentView();
        initView();
        initData();
    }

    protected void initView() {

    }

    protected void initData() {

    }

    private void initContentView() {
        flbase.addView(inflation.inflate(getContentView(), null));
    }

    public abstract int getContentView();

    private void initTitleView() {
        View titleView = inflation.inflate(R.layout.base_title_view, null);
        this.titleRight = (ImageView) titleView.findViewById(R.id.titleRight);
        this.title = (TextView) titleView.findViewById(R.id.title);
        this.titleBack = (Button) titleView.findViewById(R.id.titleBack);
        ll.addView(titleView);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到对应的Activity
     */
    public <T> void showActivity(Class<T> activityCls) {
        showActivity(activityCls, null);
    }

    public <T> void showActivity(Class<T> activityCls, Bundle extras) {
        Intent intent = new Intent(this, activityCls);
        if (null == extras) {
            startActivity(intent);
        } else {
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PopWindowUtils.IMAGE_CHIOCE:
                    Uri uri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    ContentResolver cr = this.getContentResolver();
                    Cursor cursor = cr.query(uri, filePathColumn, null, null, null);
                    //首先获取路径
                    if (null != cursor) {
                        cursor.moveToFirst();
                        if (cursor.getColumnCount() > 0) {
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            if (cursor.getCount() > 0) {
                                fileImagePath = cursor.getString(columnIndex);
                                LogUtils.i(TAG, "--->baseActivity" + fileImagePath);
                            }
                        }
                        cursor.close();
                    }

                    if (fileImagePath == null || "".equals(fileImagePath)) {
                        fileImagePath = uri.getPath();
                    }

                    //路径没有获取到
                    if (fileImagePath == null || "".equals(fileImagePath)) {
                        //再去获取图片
                        Bundle extras = data.getExtras();
                        boolean isGetBitmap = false;
                        if (null != extras) {
                            Bitmap bitmap = extras.getParcelable("data");
                            if (null != bitmap) {
                                onBitmapComplete("", bitmap);
                                isGetBitmap = true;
                            } else {
                                isGetBitmap = false;
                            }
                        } else {
                            isGetBitmap = false;
                        }
                        //图片也没有获取到，直接调用系统裁剪
                        if (!isGetBitmap) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                                onBitmapComplete("", bitmap);
                            } catch (FileNotFoundException e) {
                                LogUtils.i(TAG, e.getMessage());
                                onBitmapComplete("", null);
                            } catch (IOException e) {
                                LogUtils.i(TAG, e.getMessage());
                                onBitmapComplete("", null);
                            }
                        }
                    } else {
                        if (fileImagePath.indexOf("/document/image") > -1) {
//                            MyToast.showToastCustomerStyleText(this, "图片路径不对请重新选择");
                            return;
                        } else {
                            fileImagePath = getCorrectFilePath(fileImagePath);
                            uploadImage(fileImagePath);
                        }

                    }
                    break;
                case PopWindowUtils.TAKE_PHOTO:
                    String bitmapPath = PopWindowUtils.takePhotoPath;
                    bitmapPath = getCorrectFilePath(bitmapPath);
                    Uri mImageCaptureUri = Uri.fromFile(new File(bitmapPath));
                    uploadImage(bitmapPath);
                    break;
            }
        }

    }

    private void uploadImage(String bitmapPath) {
        Observer<BaseEntity<List<String>>> obLg = new DefaultObserver<BaseEntity<List<String>>>(this) {

            @Override
            public void onCompleted() {
                Log.d("onCompleted", "------->>");
            }

            @Override
            public void onError(Throwable e) {
                dismissDialog();
                Log.d("onError", "------->>" + e);
            }

            @Override
            public void onNext(BaseEntity<List<String>> baseEntity) {
                super.onNext(baseEntity);
                List<String> data = baseEntity.getData();
                onBackForImage(data,bitmap);
                for (String str :
                        data) {
                    Log.d("onNext", "------->>" + str);
                }
                dismissDialog();
            }
        };

        showProgressDialog();
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "上传图片");
        File file = decodeImg(bitmapPath, 800, 900);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);
        NetWorkUtil.getUpLoadImageApi(new SysInterceptor(BaseActivity.this))
                .upLoadImg(description, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obLg);
    }

    protected void onBackForImage(List<String> data, Bitmap bitmap) {

    }

    /**
     * @param filePath   要加载的图片路径
     * @param destWidth  显示图片的控件宽度
     * @param destHeight 显示图片的控件的高度
     * @return
     */
    public File decodeImg(String filePath, int destWidth, int destHeight) {
        //第一次采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
        BitmapFactory.decodeFile(filePath, options);
        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //定义缩放比例
        int sampleSize = 1;
        while (outHeight / sampleSize > destHeight || outWidth / sampleSize > destWidth) {
            //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
            sampleSize *= 2;
        }
        /********************************************************************************************/
        //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
        /********************************************************************************************/
        //二次采样开始
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
        options.inJustDecodeBounds = false;
        //设置缩放比例
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //加载图片并返回
        bitmap = BitmapFactory.decodeFile(filePath, options);
        return saveBitmapFile(bitmap, filePath);
    }

    // Bitmap对象保存味图片文件
    public File saveBitmapFile(Bitmap bitmap, String filePath) {
        File file = new File(filePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public <T> void showActivity(Class<T> activityCls, String key, Serializable obj) {
        Intent intent = new Intent(this, activityCls);
        intent.putExtra(key, obj);
        startActivity(intent);
    }

    public <T> void showActivityByLoginFilter(Class<T> activityCls,
                                              Bundle extras) {
        Intent intent = new Intent(this, activityCls);
        if (null != extras) {
            intent.putExtras(extras);
        }

        showActivity(activityCls, extras);

    }

    /**
     * 跳转到对应的Activity返回的时候可以接受到结果
     *
     * @param activityCls 对应的Activity.class
     * @param requestCode 请求码
     */
    public <T> void showActivityForResult(Class<T> activityCls,
                                          int requestCode) {
        showActivityForResult(activityCls, null, requestCode);
    }

    public <T> void showActivityForResult(Class<T> activityCls,
                                          Bundle extras, int requestCode) {
        Intent intent = new Intent(this, activityCls);
        if (null == extras) {
            startActivityForResult(intent, requestCode);
        } else {
            intent.putExtras(extras);
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 跳转到对应的Activity by Flags的过滤
     *
     * @param activityCls
     * @param flags
     */
    public <T> void showActivityByFlags(Class<T> activityCls, int flags) {
        Intent intent = new Intent(this, activityCls);
        intent.setFlags(flags);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public <T> void showActivityByFlags(Class<T> activityCls, Bundle extras, int flags) {
        Intent intent = new Intent(this, activityCls);
        intent.setFlags(flags);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * 显示加载对话框
     **/
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(this);
        }
        progressDialog.show();
    }

    /**
     * 隐藏对话框
     **/
    public void dismissDialog() {
        if (NotNull.isNotNull(progressDialog) && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saApplication.removeActivity(this);
        dismissDialog();
    }

    /**
     * 显示加载对话框
     *
     * @param message 需要加载的信息
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(this, message);
        }
        progressDialog.show();
    }

    public void showProgressDialog(String message, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(this, message, cancelable);
        }
        progressDialog.show();
    }

    private String getCorrectFilePath(String photoPath) {
        String filePath = photoPath;
        if (!StringUtils.isEmpty(filePath)) {
            if (filePath.indexOf("/storage/emulated/0") > -1) {
                filePath = filePath.replace("/storage/emulated/0", "/sdcard");
            }
        }
        return filePath;

    }

    /**
     * 获取图片接口
     *
     * @param bitmapPath
     * @param bitmap
     */
    protected void onBitmapComplete(String bitmapPath, Bitmap bitmap) {

    }

}
