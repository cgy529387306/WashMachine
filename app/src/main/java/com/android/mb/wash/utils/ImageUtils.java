package com.android.mb.wash.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.mb.wash.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

/**
 * Created by cgy on 2017/7/17
 */
public class ImageUtils {

    public static String saveBitMapToFile(Context context, String fileName, Bitmap bitmap) {
        if (null == context || null == bitmap) {
            return null;
        }
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        FileOutputStream fOut = null;
        try {
            File file = null;
            String fileDstPath = "";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 保存到sd卡
                fileDstPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator + fileName;

                File homeDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdirs();
                }
            } else {
                // 保存到file目录
                fileDstPath = context.getFilesDir().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator + fileName;

                File homeDir = new File(context.getFilesDir().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdir();
                }
            }

            file = new File(fileDstPath);
            if (file.exists()) {
                file.delete();
            }

            fOut = new FileOutputStream(file);
            if (fileName.endsWith(".jpg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
            } else {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            }
            fOut.flush();
            Log.i("FileSave", "saveDrawableToFile " + fileName
                    + " success, save path is " + fileDstPath);
            return fileDstPath;
        } catch (Exception e) {
            Log.e("FileSave", "saveDrawableToFile: " + fileName + " , error", e);
            return null;
        } finally {
            if (null != fOut) {
                try {
                    fOut.close();
                } catch (Exception e) {
                    Log.e("FileSave", "saveDrawableToFile, close error", e);
                }
            }
        }
    }

    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Context context, Bitmap bmp, String picName) {
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
        } catch (Exception e) {
            e.getStackTrace();
            ToastHelper.showToast("图片保存失败");
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, fileName, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            ToastHelper.showToast("图片保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastHelper.showToast("图片保存失败");
        }
    }

    /**
     * 根据图片的路径得到图片资源(压缩后)
     * 如果targetW或者targetH为0就自动压缩
     *
     * @param path
     * @param
     * @return 压缩后的图片
     */
    public static Bitmap getYaSuoBitmapFromImagePath(String path, int targetW, int targetH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = calculateInSampleSize(options);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(path, options);

        if (src == null) {
            return null;
        }
        Bitmap bitmap = null;

        if (targetH == 0 || targetW == 0) {
            bitmap = Bitmap.createScaledBitmap(src, width / inSampleSize, height / inSampleSize, false);
        } else {
            bitmap = Bitmap.createScaledBitmap(src, targetW, targetH, false);
        }

        if (src != bitmap) {
            src.recycle();
        }

        return bitmap;
    }

    /**
     * 计算压缩比
     *
     * @param options
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options) {
        int height = options.outHeight;
        int width = options.outWidth;

        int min = height > width ? width : height;
        int inSampleSize = min / 400;

        if (inSampleSize == 0)

            return 1;

        return inSampleSize;
    }

    /**
     * base64 转图片
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit
     * @return
     */
    public static String bitmap2Base64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    public static void displayAvatar(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_avatar)// 正在加载中的图片
                .error(R.mipmap.icon_avatar); // 加载失败的图片
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }

    public static void loadImageUrl(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.image_placeholder)// 正在加载中的图片
                .error(R.drawable.image_placeholder); // 加载失败的图片
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }


    @SuppressLint("CheckResult")
    public static void loadVideoScreenshot(final Context context, String uri, ImageView imageView, long frameTimeMicros) {
        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                return toTransform;
            }

            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                try {
                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }
}