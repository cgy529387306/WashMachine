package com.android.mb.wash.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.android.mb.wash.entity.AreaBean;
import com.android.mb.wash.view.CompanyDescActivity;
import com.android.mb.wash.view.ResourceActivity;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cgy on 19/1/12.
 */

public class ProjectHelper {

    /**
     * 防止控件被连续点击
     * @param view
     */
    public static void disableViewDoubleClick(final View view) {
        if(Helper.isNull(view)) {
            return;
        }
        view.setEnabled(false);
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }

    /**
     * 手机验证
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    public static String getCommonText(String data){
        return data == null?"":data;
    }


    public static void openQQ(Context context,String qq){
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin="+qq;//uin是发送过去的qq号码
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"请检查是否安装QQ",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 用Intent打开url(即处理外部链接地址)
     *
     * @param context
     * @param url
     */
    public static void openUrlWithIntent(Context context, String url) {
        if (Helper.isNull(context) || Helper.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getResourceTitle(int type, AreaBean areaBean){
        String resourceTitle = "";
        switch (type){
            case 1:
                resourceTitle = "工程案例";
                break;
            case 2:
                resourceTitle = "品牌骄傲";
                break;
            case 3:
                resourceTitle = "门店照片";
                break;
            case 4:
                if (Helper.isNotEmpty(areaBean) && Helper.isNotEmpty(areaBean.getName())) {
                    resourceTitle = areaBean.getName();
                } else {
                    resourceTitle = "专卖店";
                }
                break;
            case 5:
                resourceTitle = "专区";
                break;
            case 6:
                resourceTitle = "广告素材";
                break;
            case 7:
                resourceTitle = "丽景实物图库";
                break;
        }
        return resourceTitle;
    }


    public static boolean isVideo(String url) {
        boolean isVideo = false;
        try {
            int begin = url.lastIndexOf('.')+1;
            int end = url.indexOf('?');
            String fileName = url.substring(begin, end);
            String[] videoTypes = {"avi","flv","mpg","mpeg","mpe","m1v","m2v","mpv2","mp2v","dat","ts","tp","tpr","pva","pss","mp4","m4v",
                    "m4p","m4b","3gp","3gpp","3g2","3gp2","ogg","mov","qt","amr","rm","ram","rmvb","rpm"};
            if (Arrays.asList(videoTypes).contains(fileName)) {
                isVideo = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isVideo;
    }

}