package com.gxtc.commlibrary.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Steven on 16/12/15.
 */

public class EventBusUtil {

    public static void register(Object obj){
        if(!EventBus.getDefault().isRegistered(obj)){
            EventBus.getDefault().register(obj);
        }
    }

    public static void unregister(Object obj){
        if(EventBus.getDefault().isRegistered(obj)){
            EventBus.getDefault().unregister(obj);
        }
    }

    public static void post(Object obj){
        EventBus.getDefault().post(obj);
    }
    public static void postStickyEvent(Object obj){
        EventBus.getDefault().postSticky(obj);
    }
}
