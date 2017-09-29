package example.com.stockapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.stockapp.R;

/**
 * Created by ning.wen on 2016/11/1.
 */

public class InventoryFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_inventory);
        initView();
        return getContentView();
    }

    private void initView() {
    }

}
