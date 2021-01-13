package com.android.mb.wash.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.android.mb.wash.R;
import com.android.mb.wash.constants.AppConstants;
import com.android.mb.wash.utils.DynamicTimeFormat;
import com.danikula.videocache.HttpProxyCacheServer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;

/**
 * 主要用来获取全局的Context
 * @author cgy
 *
 */
public class MBApplication extends Application {
	
	private static final String TAG = MBApplication.class.getSimpleName();
	
	private static Context sInstance;

	private HttpProxyCacheServer proxy;

	static {
		//启用矢量图兼容
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
		//设置全局默认配置（优先级最低，会被其他设置覆盖）
		SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
			@Override
			public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
				//全局设置（优先级最低）
				layout.setEnableAutoLoadMore(true);
				layout.setEnableOverScrollDrag(false);
				layout.setEnableOverScrollBounce(true);
				layout.setEnableLoadMoreWhenContentNotFull(true);
				layout.setEnableScrollContentWhenRefreshed(true);
			}
		});
		SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
			@NonNull
			@Override
			public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
				layout.setPrimaryColorsId(R.color.colorPrimary, R.color.base_gray);
				return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
			}
		});
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
	}

	/**
	 * 获取全局Application对象
	 * @since 2013.08.02 修改错误提示内容为Application by pcqpcq
	 * @return
	 */
	public static Context getInstance(){
		if (sInstance == null) {
			throw new RuntimeException("Application must be init");
		}
		return sInstance;
	}

    /**
     * 初始化
     * <p>若未配置manifest可使用此方法初始化</p>
     * @param application 全局context
     */
    public static void init(Context application) {
        sInstance = application;
    }

	public static HttpProxyCacheServer getProxy(Context context) {
		MBApplication app = (MBApplication) context.getApplicationContext();
		return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
	}

	private HttpProxyCacheServer newProxy() {
		return new HttpProxyCacheServer(this);
	}

    @Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
        Bugly.init(getApplicationContext(), AppConstants.BUGLY_APP_ID, false);
	}
	
}
