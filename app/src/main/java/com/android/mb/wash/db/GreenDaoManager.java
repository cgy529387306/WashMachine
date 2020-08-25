package com.android.mb.wash.db;


import com.android.mb.wash.app.MBApplication;
import com.android.mb.wash.greendao.DaoMaster;
import com.android.mb.wash.greendao.DaoSession;

/**
 * Created by Administrator on 2018\8\7 0007.
 */

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static volatile GreenDaoManager mInstance = null;

    private GreenDaoManager() {
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new
                    DaoMaster.DevOpenHelper(MBApplication.getInstance(), "movieStore.db");
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
