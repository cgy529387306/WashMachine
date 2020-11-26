package com.android.mb.wash.service;

import com.android.mb.wash.entity.Advert;
import com.android.mb.wash.entity.AreaBean;
import com.android.mb.wash.entity.Avatar;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.HomeData;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.entity.ProductDetail;
import com.android.mb.wash.entity.ProductListData;
import com.android.mb.wash.entity.ResourceListData;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.retrofit.http.entity.HttpResult;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @created by cgy on 2017/6/19
 */
public interface IScheduleService {
    @GET("/wash/api/skill/hot/list")
    Observable<HttpResult<Object>> getHotList();

    /**
     * phone:手机号  type:类型，0:用户注册 1:忘记密码
     * @param requestMap
     * @return
     */
    @GET("/ljbathroom/api/1.0/user/getCode")
    Observable<HttpResult<UserBean>> getCode(@QueryMap Map<String,Object> requestMap);

    /**
     * avatar:头像文件流
     * @return
     */
    @POST("/ljbathroom/api/1.0/user/uploadAvatar")
    @Multipart
    Observable<HttpResult<Avatar>> uploadAvatar(@Part MultipartBody.Part file);

    /**
     * userId:用户id
     * @param requestMap
     * @return
     */
    @GET("/ljbathroom/api/1.0/user/getUserInfo")
    Observable<HttpResult<UserBean>> getUserInfo(@QueryMap Map<String,Object> requestMap);

    /**
     * userId:用户id
     * @param requestMap
     * @return
     */
    @GET("/ljbathroom/api/1.0/user/updateUserInfo")
    Observable<HttpResult<UserBean>> updateInfo(@QueryMap Map<String,Object> requestMap);

    /**
     * account:手机号或者用户名  password:密码
     * @param requestMap
     * @return
     */
    @GET("/ljbathroom/api/1.0/user/login")
    Observable<HttpResult<UserBean>> userLogin(@QueryMap Map<String,Object> requestMap);

    /**
     * account:手机号或者用户名  password:密码 code:验证码
     * @param requestMap
     * @return
     */
    @GET("/ljbathroom/api/1.0/user/register")
    Observable<HttpResult<CurrentUser>> userRegister(@QueryMap Map<String,Object> requestMap);


    /**
     * phone:手机号 newPassword:新密码 valcode:验证码
     * @param requestMap
     * @return
     */
    @GET("/ljbathroom/api/1.0/user/forgetPassword")
    Observable<HttpResult<Object>> forgetPassword(@QueryMap Map<String,Object> requestMap);

    /**
     * newPassword:新密码 newPassword:旧密码
     * @param requestMap
     * @return
     */
    @GET("/ljbathroom/api/1.0/user/updatePassword")
    Observable<HttpResult<Object>> updatePassword(@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @Multipart
    @POST("/ljbathroom/api/1.0/common/publishDynamic")
    Observable<HttpResult<Object>> publishDynamic(@Part List<MultipartBody.Part> fileList, @QueryMap Map<String,Object> requestMap);

    /**
     * @returnm
     */
    @GET("/ljbathroom/api/1.0/common/getDynamicList")
    Observable<HttpResult<PostListData>> getDynamicList(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/praise")
    Observable<HttpResult<Object>> praise(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * content
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/comment")
    Observable<HttpResult<Object>> comment(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/getCommentList")
    Observable<HttpResult<CommentListData>> getCommentList(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/getCategoryList")
    Observable<HttpResult<List<Category>>> getCategoryList(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/getProductList")
    Observable<HttpResult<ProductListData>> getProductList(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * content
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/getProductDetail")
    Observable<HttpResult<ProductDetail>> getProductDetail(@QueryMap Map<String,Object> requestMap);


    /**
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/getHomeData")
    Observable<HttpResult<HomeData>> getHomeData(@QueryMap Map<String,Object> requestMap);

    /**
     * @return type 1：工程案例  
     * 2：品牌骄傲 
     * 3：门店照片
     * 4：专卖店 
     * 5：专区 
     * 6：广告素材
     */
    @GET("/ljbathroom/api/1.0/common/getResourceList")
    Observable<HttpResult<ResourceListData>> getResourceList(@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/getAreaList")
    Observable<HttpResult<List<AreaBean>>> getAreaList(@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @GET("/ljbathroom/api/1.0/common/getCateAdverList")
    Observable<HttpResult<List<Advert>>> getCateAdverList(@QueryMap Map<String,Object> requestMap);

}
