package example.com.stockapp.view.tools;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */

public class SAApplication extends Application {
    public static boolean isDebug = true;//全局log 关闭或打开
    private List<Activity> activityList = new LinkedList<>();
    private CrashUtil crashUtil;//全局崩溃监听

    @Override
    public void onCreate() {
        super.onCreate();
        crashUtil = CrashUtil.getInstance();
        crashUtil.init(this);
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }
    //遍历所有Activity并finish

    public void exit() {
        for (Activity activity : activityList) {
//            if (activity instanceof LoginActivity)
//                continue;
//            else {
                activity.finish();
//            }
        }
    }
}
