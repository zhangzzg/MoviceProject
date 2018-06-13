package com.gxtc.commlibrary.data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import rx.Subscription;

public class BaseRepository {
    private List<Subscription> mCallList;


    public void destroy() {
        if (mCallList != null && mCallList.size() > 0) {
            for (int i = 0; i < mCallList.size(); i++) {
                mCallList.get(i).unsubscribe();
            }
        }
    }

    public void addSub(Subscription sub) {
        if (mCallList == null) {
            mCallList = new ArrayList<>();
        }

        if(sub != null){
            mCallList.add(sub);
        }

    }
}
