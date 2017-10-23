package example.com.stockapp.view.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/27.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).placeholder(R.mipmap.empty_icon).into(imageView);
    }
}
