package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.ArrayList;

import example.com.stockapp.R;
import example.com.stockapp.entries.BarEntity;
import example.com.stockapp.view.fragments.HomeFragment;
import example.com.stockapp.view.fragments.InventoryFragment;
import example.com.stockapp.view.fragments.ScanFragment;
import example.com.stockapp.view.graphs.BottomTabBar;

public class MainActivityFail extends BaseActivity implements BottomTabBar.OnSelectListener {
    private BottomTabBar tb;
    private FragmentManager manager;
    private ArrayList<BarEntity> bars;
    private HomeFragment homeFragment;
    private ScanFragment scanFragment;
    private InventoryFragment inventoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        manager = getSupportFragmentManager();
        tb = (BottomTabBar) findViewById(R.id.tb);
        tb.setManager(manager);
        tb.setOnSelectListener(this);
        bars = new ArrayList<>();
        bars.add(new BarEntity("首页", R.drawable.ic_home_select, R.drawable.ic_home_unselect));
        bars.add(new BarEntity("扫一扫", R.drawable.ic_textjoke_select, R.drawable.ic_textjoke_unselect));
        bars.add(new BarEntity("库存", R.drawable.ic_imagejoke_select, R.drawable.ic_imagejoke_unselect));
        tb.setBars(bars);

        //初始化titlebar
        titleBack.setVisibility(View.GONE);
        title.setText(R.string.inventorySystem);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onSelect(int position) {
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                tb.switchContent(homeFragment);
                break;
            case 1:
                if (scanFragment == null) {
                    scanFragment = new ScanFragment();
                }
                tb.switchContent(scanFragment);
                break;
            case 2:
                if (inventoryFragment == null) {
                    inventoryFragment = new InventoryFragment();
                }
                tb.switchContent(inventoryFragment);
                break;
            default:
                break;
        }
    }
}
