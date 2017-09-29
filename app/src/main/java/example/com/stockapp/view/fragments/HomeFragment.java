package example.com.stockapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import example.com.stockapp.R;
import example.com.stockapp.view.tools.GlideImageLoader;

/**
 * Created by ning.wen on 2016/11/1.
 */

public class HomeFragment extends BaseFragment {
    private List<String> images;
    private List<String> titles;
    private Banner banner;
    private android.widget.TextView outInventory;
    private android.widget.TextView inInventory;
    private android.widget.TextView showInventory;
    private android.widget.TextView tvMore;
    private android.widget.GridView glOutDateGoods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);

        initView();
        return getContentView();
    }

    private void initView() {
        this.glOutDateGoods = (GridView) findViewById(R.id.glOutDateGoods);
        this.tvMore = (TextView) findViewById(R.id.tvMore);
        this.showInventory = (TextView) findViewById(R.id.showInventory);
        this.inInventory = (TextView) findViewById(R.id.inInventory);
        this.outInventory = (TextView) findViewById(R.id.outInventory);
        this.banner = (Banner) findViewById(R.id.banner);

        Banner banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images = new ArrayList<>();
        titles = new ArrayList<>();
        images.add("http://images.csdn.net/20170921/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20170921142754_%E5%89%AF%E6%9C%AC.jpg");
        images.add("http://images.csdn.net/20170927/006iJodMzy75u4xzagi92.png");
        images.add("http://images.csdn.net/20170926/MD_%E5%89%AF%E6%9C%AC.jpg");
        images.add("http://images.csdn.net/20170922/VCG41143917956_%E5%89%AF%E6%9C%AC.jpg");
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

}
