package com.android.mb.wash.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;


/**
 * <pre>
 *     author: cgy
 *     time  : 2017/7/20
 *     desc  :
 * </pre>
 *
 */

public abstract class BaseMvpFragment<P extends Presenter<V>,V extends BaseMvpView>
        extends BaseFragment implements BaseMvpView{


    protected P mPresenter;

    private MaterialDialog mMaterialDialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void back() {

    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgressDialog() {
        if (mMaterialDialog == null){
            mMaterialDialog = new MaterialDialog.Builder(mContext)
                    .content("加载中...")
                    .progress(true, 0).build();
        }
        mMaterialDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mContext==null || mContext.isFinishing()) {
            return;
        }
        if (mMaterialDialog!=null && mMaterialDialog.isShowing()){
            mMaterialDialog.dismiss();
        }
    }

    /**
     * add a keylistener for progress dialog
     */
    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissProgressDialog();
            }
            return false;
        }
    };

}
