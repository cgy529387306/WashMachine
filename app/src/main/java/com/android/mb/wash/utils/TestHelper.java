package com.android.mb.wash.utils;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static List<String> getTestImage(){
        List<String> dataList = new ArrayList<>();
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279070&di=9701ecdf684d524bc0eb8a2ceb3d3b28&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201105%2F17%2F113554rnu40q7nbgnn3lgq.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279070&di=1e64e02041a81fca889194fec3850c97&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201303%2F18%2F233119quyrec7to3ws3rco.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279070&di=6d7f4ff16f51b1e6735416eafccd52c7&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F3ac79f3df8dcd100501e1ae0708b4710b8122f75.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279069&di=ffc71dbfa53ee89491cb564ce3d255c6&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201310%2F19%2F235439yh04c010wm0qrk5d.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279069&di=1a056f3c61f97b743a1edc852752f6cf&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201312%2F31%2F111859myvyiivetyftfz2n.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279069&di=3dc372939c8d96609dc6dc62389dda45&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201407%2F18%2F135446xbaqj12g0bpp2zh1.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279068&di=d8128e9c5509d0954f6a8938e82ac0c4&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201305%2F29%2F101838y2tkuxpfopkwzpvg.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279068&di=626f813f7ef2cfe202d99d4c68d13010&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1209%2F05%2Fc0%2F13630426_1346827472062.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279067&di=3fd1c09c82cc42a85c8504e0779daace&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2F201304%2F18%2F001339jv88x0qs06vo3qq6.jpg");
        dataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600064279065&di=0d4d492e1372333f26c1ab0a4685b744&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1308%2F29%2Fc0%2F25038622_1377746019192.jpg");
        return dataList;
    }


    public static List<String> getCateList(){
        List<String> cateList = new ArrayList<>();
        for (int i=0; i<10; i++){
            cateList.add("分类###"+i);
        }
        return cateList;
    }
}
