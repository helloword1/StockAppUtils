package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/30.
 */

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater inflate;
    private Context context;
    private List<String> datas;

    public DataAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TitleViewHolder(inflate.inflate(R.layout.data_item_title, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {
        public TitleViewHolder(View view) {
            super(view);
        }
    }private class DataContentViewHolder extends RecyclerView.ViewHolder {
        public DataContentViewHolder(View view) {
            super(view);
        }
    }
}
