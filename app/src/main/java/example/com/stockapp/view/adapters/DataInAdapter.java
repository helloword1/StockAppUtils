package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/30.
 */

public class DataInAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLE_OTHER = 1;
    private static final int TITLE_CONTENT = 2;
    private static final int TITLE_COMMIT = 3;
    private final LayoutInflater inflate;
    private Context context;
    private List<String> datas;
    private popuOnClickListener listener;
    private boolean otherbl = false;
    private boolean isOutInventor = false;
    private String content;

    public DataInAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        inflate = LayoutInflater.from(context);
    }

    public void setOtherTime(boolean otherbl) {
        this.otherbl = otherbl;
    }

    public void setOutInventor(boolean isOutInventor) {
        this.isOutInventor = isOutInventor;
    }

    public void setCommitContet(String content) {
        this.content = content;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE_COMMIT) {
            return new CommitViewHolder(inflate.inflate(R.layout.data_item_commit, parent, false));
        } else if (viewType == TITLE_OTHER) {
            return new OtherTimeViewHolder(inflate.inflate(R.layout.data_item_bottom, parent, false));
        } else {
            return new ContentViewHolder(inflate.inflate(R.layout.data_item_content, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (otherbl) {
            if (position == datas.size() - 1) {
                CommitViewHolder commitViewHolder = (CommitViewHolder) holder;
                commitViewHolder.tvOtherTime.setVisibility(View.GONE);
                commitViewHolder.tvCommit.setVisibility(View.VISIBLE);
if (NotNull.isNotNull(content)){
    commitViewHolder.tvCommit.setText(content);
}
                commitViewHolder.tvCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setCommit();
                    }
                });
            } else if (position == datas.size() - 2 || position == datas.size() - 3 || position == datas.size() - 4) {
                OtherTimeViewHolder otherTimeViewHolder = (OtherTimeViewHolder) holder;
                if (position == datas.size() - 4) {
                    otherTimeViewHolder.tvDateContent.setVisibility(View.GONE);
                    otherTimeViewHolder.item_section_iv.setVisibility(View.GONE);
                    otherTimeViewHolder.dateView.setBackgroundResource(R.color.white);
                    int dimension = (int) context.getResources().getDimension(R.dimen.x12);
                    otherTimeViewHolder.dateView.setPadding(dimension, dimension, dimension, 0);
                } else {
                    otherTimeViewHolder.tvDateContent.setVisibility(View.VISIBLE);
                    otherTimeViewHolder.item_section_iv.setVisibility(View.VISIBLE);
                    otherTimeViewHolder.dateView.setBackgroundResource(R.drawable.tv_strok_down);
                    int dimension = (int) context.getResources().getDimension(R.dimen.x12);
                    otherTimeViewHolder.dateView.setPadding(dimension, dimension, dimension, dimension);
                    otherTimeViewHolder.dateView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.setChoice(position);
                        }
                    });
//                    listener.setChoice(position);
                }
                otherTimeViewHolder.tvDateTitle.setText(datas.get(position + 1));
            } else {
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                contentViewHolder.tvChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setChoice(position);
                    }
                });
                contentViewHolder.tvData.setText(datas.get(position + 1));
            }
        } else {
            if (position == datas.size() - 1) {
                CommitViewHolder commitViewHolder = (CommitViewHolder) holder;
                if (isOutInventor) {
                    commitViewHolder.tvOtherTime.setVisibility(View.GONE);

                } else {
                    commitViewHolder.tvOtherTime.setVisibility(View.VISIBLE);
                }

                commitViewHolder.tvCommit.setVisibility(View.GONE);
                commitViewHolder.tvOtherTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setMoreTime();
                    }
                });
            } else {
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                contentViewHolder.tvChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setChoice(position);
                    }
                });
                contentViewHolder.tvData.setText(datas.get(position + 1));
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (otherbl) {
            if (position == datas.size() - 1) {
                return TITLE_COMMIT;
            } else if (position == datas.size() - 2 || position == datas.size() - 3 || position == datas.size() - 4) {
                return TITLE_OTHER;
            } else {
                return TITLE_CONTENT;
            }
        } else {
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
        TextView tvDateContent;
        TextView tvDateTitle;
        ImageView item_section_iv;
        RelativeLayout dateView;

        public OtherTimeViewHolder(View inflate) {
            super(inflate);
            tvDateContent = ((TextView) inflate.findViewById(R.id.tvDateContent));
            tvDateTitle = ((TextView) inflate.findViewById(R.id.tvDateTitle));
            item_section_iv = ((ImageView) inflate.findViewById(R.id.item_section_iv));
            dateView = ((RelativeLayout) inflate.findViewById(R.id.dateView));
        }
    }

    public interface popuOnClickListener {
        void setMoreTime();

        void setChoice(int position);

        void setCommit();
    }

    public void setOnpopuOnClickLIstener(popuOnClickListener listener) {
        this.listener = listener;
    }
}
