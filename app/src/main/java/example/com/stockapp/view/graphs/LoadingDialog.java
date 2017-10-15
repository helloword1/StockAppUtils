package example.com.stockapp.view.graphs;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cxx.utils.NotNull;
import example.com.stockapp.R;

@TargetApi(Build.VERSION_CODES.FROYO)
public class LoadingDialog extends Dialog {

	private Context mContext;
	private String message;
	Animation hyperspaceJumpAnimation;
	private boolean cancelable = true;

	public LoadingDialog(Context context, String mesString) {
		super(context, R.style.LoadingDialog);
		this.mContext = context;
		this.message = mesString;
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	public LoadingDialog(Context context, String mesString, int theme) {
		super(context, theme);
		this.mContext = context;
		this.message = mesString;
	}

	public LoadingDialog(Context context) {
		super(context, R.style.LoadingDialog);
		this.mContext = context;
	}
	
	public LoadingDialog(Context context, String mesString, boolean cancelable) {
		super(context, R.style.LoadingDialog);
		this.mContext = context;
		this.cancelable = cancelable;
		this.message = mesString;
	}
	public LoadingDialog(Context context, boolean cancelable) {
		super(context, R.style.LoadingDialog);
		this.mContext = context;
		this.cancelable = cancelable;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);// 加载布局
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		if (!TextUtils.isEmpty(message)) {
			tipTextView.setText(message);// 设置加载信息
		}
		this.setCancelable(this.cancelable);// 不可以用"返回键"取消
		setContentView(layout);// 设置布局
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (NotNull.isNotNull(hyperspaceJumpAnimation))
			hyperspaceJumpAnimation.cancel();
	}
}
