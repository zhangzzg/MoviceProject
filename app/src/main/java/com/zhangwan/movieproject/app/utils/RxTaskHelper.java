package com.zhangwan.movieproject.app.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import rx.Subscription;

/**
 * 来自 伍玉南 的装逼小尾巴 on 17/7/3.
 * 统一管理rxjava任务
 */

public class RxTaskHelper {

    private static RxTaskHelper mHelper;

    private Map<String,ArrayList<WeakReference<Subscription>>> taskMap;

    private RxTaskHelper() {
        taskMap = new HashMap<>();
    }

    public static RxTaskHelper getInstance() {
        if (mHelper == null) {
            synchronized (RxTaskHelper.class) {
                if (mHelper == null) {
                    mHelper = new RxTaskHelper();
                }
            }
        }
        return mHelper;
    }

    //添加一个新任务
    public void addTask(Object object, Subscription subscription){
        if(object == null && subscription == null) return;

        Class clazz = object.getClass();
        ArrayList<WeakReference<Subscription>> subs = taskMap.get(clazz.getSimpleName());
        if(subs == null){
            subs = new ArrayList<>();
            taskMap.put(clazz.getSimpleName(),subs);
        }
        subs.add(new WeakReference<Subscription>(subscription));
    }

    //取消任务
    public void cancelTask(Object object){
        if(object == null)  return;

        Class clazz = object.getClass();
        ArrayList<WeakReference<Subscription>> subs = taskMap.get(clazz.getSimpleName());
        if(subs != null && subs.size() > 0){
            for (WeakReference<Subscription> reference : subs){
                Subscription sub = reference.get();
                if(sub != null){
                    sub.unsubscribe();
                }
            }
        }
    }

    //取消所有任务
    public void cancelAllTask(){
        Set<String> keys = taskMap.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while (keyIterator.hasNext()){
            String key = keyIterator.next();
            ArrayList<WeakReference<Subscription>> subs = taskMap.get(key);

            for(WeakReference<Subscription> reference : subs){
                Subscription sub = reference.get();
                if(sub != null){
                    sub.unsubscribe();
                }
            }
        }
    }
}
