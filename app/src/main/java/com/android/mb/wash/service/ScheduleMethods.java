package com.android.mb.wash.service;

import android.util.Base64;

import com.android.mb.wash.entity.Avatar;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.entity.HomeData;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.entity.ProductListData;
import com.android.mb.wash.entity.ResourceListData;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.entity.VideoData;
import com.android.mb.wash.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.wash.retrofit.http.RetrofitHttpClient;
import com.android.mb.wash.utils.JsonHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * @Created by cgy on 2017/6/27
 */
@SuppressWarnings("unchecked")
public class ScheduleMethods extends BaseHttp {


    private static class SingletonHolder {
        private static final ScheduleMethods INSTANCE = new ScheduleMethods();
    }

    //获取单例
    public static ScheduleMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private IScheduleService getService() {
        return new RetrofitHttpClient.Builder()
                .baseUrl(getServerHost())
                .addHeader(getHead())
                .addDotNetDeserializer(false)
                .addCache(false)
                .addLog(true)
                .build()
                .retrofit()
                .create(IScheduleService.class);
    }



    public Observable getHotList(){
        return getService().getHotList()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable userLogin(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().userLogin(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable userRegister(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().userRegister(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable getCode(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getCode(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable getUserInfo(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getUserInfo(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable updateInfo(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().updateInfo(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable forgetPassword(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().forgetPassword(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable updatePassword(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().updatePassword(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }


    public Observable uploadAvatar(File file){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestBody =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        return getService().uploadAvatar(requestBody)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Avatar>());
    }

    public Observable publishDynamic(List<File> fileList, Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("image" + (i+1), file.getName(), imageBody);
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        return getService().publishDynamic(parts,requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getDynamicList(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getDynamicList(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<PostListData>());
    }

    public Observable praise(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().praise(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable comment(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().comment(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getCommentList(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getCommentList(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<PostListData>());
    }

    public Observable getCategoryList(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getCategoryList(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<List<Category>>());
    }

    public Observable getProductList(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getProductList(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<ProductListData>());
    }

    public Observable getProductDetail(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getProductDetail(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<VideoData>());
    }

    public Observable getHomeData(){
        return getService().getHomeData()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<HomeData>());
    }

    public Observable getResourceList(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getResourceList(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<ResourceListData>());
    }

    public Observable getAreaList(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getAreaList(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<ResourceListData>());
    }


}
