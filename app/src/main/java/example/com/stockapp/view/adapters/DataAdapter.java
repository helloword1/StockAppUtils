package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/30.
 */

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLE_OTHER = 1;
    private static final int TITLE_CONTENT = 2;
    private static final int TITLE_COMMIT = 3;
    private final LayoutInflater inflate;
    private Context context;
    private List<String> datas;
    private popuOnClickListener listener;
    private boolean otherbl = false;

    public DataAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        inflate = LayoutInflater.from(context);
    }

    public void setOtherTime(boolean otherbl) {
        this.otherbl = otherbl;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE_COMMIT) {
            return new CommitViewHolder(inflate.inflate(R.layout.data_item_commit, parent, false));
        } else if (viewType == TITLE_OTHER){
            return new OtherTimeViewHolder(inflate.inflate(R.layout.data_item_bottom, parent, false));
        }else {
            return new ContentViewHolder(inflate.inflate(R.layout.data_item_content, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == datas.size() - 1) {
            CommitViewHolder commitViewHolder = (CommitViewHolder) holder;
            if (otherbl) {
                commitViewHolder.tvOtherTime.setVisibility(View.GONE);
                commitViewHolder.tvCommit.setVisibility(View.VISIBLE);
                commitViewHolder.tvCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setCommit();
                    }
                });
            }else {
                commitViewHolder.tvOtherTime.setVisibility(View.VISIBLE);
                commitViewHolder.tvCommit.setVisibility(View.GONE);
                commitViewHolder.tvOtherTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setMoreTime();
                    }
                });
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (otherbl){
            if (position == datas.size() - 1) {
                return TITLE_COMMIT;
            } else if (position==datas.size() - 2||position==datas.size() - 3||position==datas.size() - 4){
                return TITLE_OTHER;
            }else{
                return TITLE_CONTENT;
            }
        }else {
            if (position == datas.size() - 1) {
                return TITLE_COMMIT;
            } else {
                return TITLE_CONTENT;
            }
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class CommitViewHolder extends RecyclerView.ViewHolder {

        TextView tvCommit;
        TextView tvOtherTime;

        public CommitViewHolder(View inflate) {
            super(inflate);
            tvCommit = ((TextView) inflate.findViewById(R.id.btn_out_inventory));
            tvOtherTime = ((TextView) inflate.findViewById(R.id.otherTime));
        }
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView tvChoice;
        TextView tvData;

        public ContentViewHolder(View inflate) {
            super(inflate);
            tvChoice = ((TextView) inflate.findViewById(R.id.tvChoice));
            tvData = ((TextView) inflate.findViewById(R.id.tvData));
        }
    }
    private class OtherTimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvChoice;
        TextView tvData;

        public OtherTimeViewHolder(View inflate) {
            super(inflate);
            tvChoice = ((TextView) inflate.findViewById(R.id.tvChoice));
            tvData = ((TextView) inflate.findViewById(R.id.tvData));
        }
    }
    public interface popuOnClickListener {
        void setMoreTime();

        void setCommit();
    }

    public void setOnpopuOnClickLIstener(popuOnClickListener listener) {
        this.listener = listener;
    }
}
