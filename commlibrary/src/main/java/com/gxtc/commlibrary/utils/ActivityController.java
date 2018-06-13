package com.gxtc.commlibrary.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzg on 2018/4/21.
 * 统一管理Activity
 */

public class ActivityController {
    private static volatile ActivityController instant;
    List<Activity> activitys = new ArrayList<>();

    private ActivityController() {}

    public static ActivityController getInstant(){
        if(instant == null){
            synchronized (ActivityController.class){
                if(instant == null){
                    instant = new ActivityController();
                }
            }
        }
        return instant;
    }

    public  void  addActivity( Activity activity){
        activitys.add(activity);
    }

    public void  removedActivity( Activity activity){
        activitys.remove(activity);
    }

    //一键退出程序
    public void  finishAllActivity(){
        for (Activity activity:activitys){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
