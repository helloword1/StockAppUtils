package example.com.stockapp.view.activities;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import example.com.stockapp.R;
import example.com.stockapp.entries.EnterGoodsBean;
import example.com.stockapp.view.adapters.EnterGoodsAdapter;
import example.com.stockapp.view.graphs.AlighRecycleView;

/**
 * Created by Administrator on 2017/10/3.
 */

public class EnterGoodsActivity extends BaseActivity {

    private AlighRecycleView entergoodsrv;

    @Override
    public int getContentView() {
        return R.layout.enter_goods_activity;
    }

    @Override
    protected void initView() {
        title.setText(R.string.GoodsInfo);
        this.entergoodsrv = (AlighRecycleView) findViewById(R.id.enter_goods_rv);
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        entergoodsrv.setLayoutManager(manager);
        ArrayList<EnterGoodsBean> datas = new ArrayList<>();
        datas.add(new EnterGoodsBean("商品名称","输入完整名称",false,true));
        datas.add(new EnterGoodsBean("商品编码","输入商品编码",false,true));
        datas.add(new EnterGoodsBean("商品条码","输入商品条码",false,true));
        datas.add(new EnterGoodsBean("分类","点击选择",true,false));
        datas.add(new EnterGoodsBean("规格","输入商品规格",false,true));
        datas.add(new EnterGoodsBean("生产日期","点击选择",true,false));
        datas.add(new EnterGoodsBean("有效期至","点击选择",true,false));
        datas.add(new EnterGoodsBean("负责人","点击选择",true,false));
        datas.add(new EnterGoodsBean("主供应商","输入商品供应商名称",false,true));
        datas.add(new EnterGoodsBean("备注","输入其他信息",false,true));
        datas.add(new EnterGoodsBean("","",false,false));
        datas.add(new EnterGoodsBean("","",false,false));
        entergoodsrv.setAdapter(new EnterGoodsAdapter(this, datas));
    }
}
