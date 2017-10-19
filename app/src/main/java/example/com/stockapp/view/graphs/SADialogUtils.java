package example.com.stockapp.view.graphs;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.DialogBean;
import example.com.stockapp.view.adapters.DialogListViewAdapter;

/**
 * Created by Administrator on 2017/10/15.
 */

public class SADialogUtils {
    private Context context;
    private DialogListViewAdapter adapter;

    public SADialogUtils(Context context) {
        this.context = context;
    }

    private DialogClickListener clickListenerInterface;

    public void notifyAdapter() {
        if (NotNull.isNotNull(adapter)){
            adapter.notifyDataSetChanged();
        }
    }

    public interface DialogClickListener {
        void doClick(int position);
    }

    public void setClickListenerInterface(
            DialogClickListener clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public void showSADialog(final List<DialogBean> mDatas) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_listview, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialogRecycleView);
        final AlertDialog dialog6 = getDialongView(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new DialogListViewAdapter(context, mDatas);
        recyclerView.setAdapter(adapter);
        adapter.setoOnGetAdapterListener(new DialogListViewAdapter.OnGetAdapterListener() {
            @Override
            public void itemClick(int position) {
                clickListenerInterface.doClick(position);
                dialog6.dismiss();
            }
        });
        setWindowCenter(dialog6);
    }

    private void setWindowCenter(AlertDialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    private AlertDialog getDialongView(View view) {
        final AlertDialog.Builder builder6 = new AlertDialog.Builder(context);
        builder6.setView(view);
        builder6.create();
        return builder6.show();
    }
}
