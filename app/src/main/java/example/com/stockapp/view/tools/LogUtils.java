package example.com.stockapp.view.tools;

import android.util.Log;

public class LogUtils {

	public final static boolean isDebug = SAApplication.isDebug;

	private static final int LOG_LENGTH = 30000; // 分段打印Log

	public static void d(Class<?> classes, Object msg) {
		if (isDebug) {
			if (msg.toString().length() < LOG_LENGTH) {
				Log.d(classes.getSimpleName(), msg.toString());
			} else {
				// msg过长
				String str1 = msg.toString().substring(0, LOG_LENGTH);
				Log.d(classes.getSimpleName(), str1);
				d(classes, msg.toString().substring(LOG_LENGTH));
			}
		}
	}

	public static void i(Class<?> classes, Object msg) {
		if (isDebug) {
			if (msg.toString().length() < LOG_LENGTH) {
				Log.i(classes.getSimpleName(), msg.toString());
			} else {
				// msg过长
				String str1 = msg.toString().substring(0, LOG_LENGTH);
				Log.i(classes.getSimpleName(), str1);
				i(classes, msg.toString().substring(LOG_LENGTH));
			}
		}
	}

	public static void v(Class<?> classes, Object msg) {
		if (isDebug) {
			if (msg.toString().length() < LOG_LENGTH) {
				Log.v(classes.getSimpleName(), msg.toString());
			} else {
				// msg过长
				String str1 = msg.toString().substring(0, LOG_LENGTH);
				Log.v(classes.getSimpleName(), str1);
				v(classes, msg.toString().substring(LOG_LENGTH));
			}
		}
	}

	public static void e(Class<?> classes, Object msg) {
		if (isDebug) {
			if (msg.toString().length() < LOG_LENGTH) {
				Log.e(classes.getSimpleName(), msg.toString());
			} else {
				// msg过长
				String str1 = msg.toString().substring(0, LOG_LENGTH);
				Log.e(classes.getSimpleName(), str1);
				e(classes, msg.toString().substring(LOG_LENGTH));
			}
		}
	}

	public static void w(Class<?> classes, Object msg) {
		if (isDebug) {
			if (msg.toString().length() < LOG_LENGTH) {
				Log.w(classes.getSimpleName(), msg.toString());
			} else {
				// msg过长
				String str1 = msg.toString().substring(0, LOG_LENGTH);
				Log.w(classes.getSimpleName(), str1);
				w(classes, msg.toString().substring(LOG_LENGTH));
			}
		}
	}

	public static int LOG_LEVEL = 6;

	public static int ERROR = 1;
	public static int WARN = 2;
	public static int INFO = 3;
	public static int DEBUG = 4;
	public static int VERBOS = 5;

	public static void e(String TAG, String msg) {
		if (LOG_LEVEL > ERROR)
			Log.e(TAG, msg);
	}

	public static void e(String TAG, String msg, Exception e) {
		if (LOG_LEVEL > ERROR)
			Log.e(TAG, msg, e);
	}

	public static void w(String TAG, String msg) {
		if (LOG_LEVEL > WARN)
			Log.w(TAG, msg);
	}

	public static void i(String TAG, String msg) {
		if (LOG_LEVEL > INFO)
			Log.i(TAG, msg);
	}

	public static void l(String tag, String content) {
		int p = 2000;
		long length = content.length();
		if (length < p || length == p)
			Log.e(tag, content);
		else {
			Log.i(tag, content.substring(0, content.length()/2));
			Log.i(tag, content.substring(content.length()/2, content.length()));
		}
	}

	public static void d(String TAG, String msg) {
		if (LOG_LEVEL > DEBUG)
			Log.d(TAG, msg);
	}

	public static void v(String TAG, String msg) {
		if (LOG_LEVEL > VERBOS)
			Log.v(TAG, msg);
	}
}
