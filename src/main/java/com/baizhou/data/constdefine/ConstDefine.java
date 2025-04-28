package com.baizhou.data.constdefine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstDefine {

    public static String SMS_URL = "https://vip.veesing.com/smsApi/verifyCode";//"http://smssh1.253.com/msg/send/json";
    public static boolean CHECK_SMS_BY_SELF = true;//验证码自己发送, 还是第三方sdk发送
    public static int SMS_CODE_VALID_SECS = 120;//60s有效


    public static boolean USE_DFACE = true;//使用脸脸
    public static String SDK_APP_ID = USE_DFACE ? "llmlpk1wqzj7leqi9a" : "wxe2be0fd36248341b";//;

    public static String ItemSperator1 = "|";
    public static String ItemSperator1Split = "\\|";
    public static String ItemSperator2 = ":";
    public static String ItemSperator3 = "_";
    public static String ItemSperator4 = "-";
    public static String ItemSperator5 = "@@@@";
    public static String ItemSperator6 = "%%%%";
    public static String ItemSperator7 = ",";
    public static String BracketL = "(";
    public static String BracketR= ")";
    public static String PlaceHolderForEmp = "NULL";

    public static Long HOUR_MILISEC = 60 * 60 * 1000L;//小时的毫秒数

    public static Long TokenExpireTime = 24 * 60 * 60 * 1000L;//24小时后过期

//    public static long DeleteDBInterval = 1;//每次intsert间隔100ms间隔
    public static long InsertDBIntervalQuick = 1;//每次intsert间隔100ms间隔
    public static long InsertDBInterval = 50;//每次intsert间隔100ms间隔
    public static long DeleteDBInterval = 1000;//每次intsert间隔100ms间隔

    public static String AdminName = "18888888888";
    public static String AdminPwd = "18888888888";

    public static Integer DefaultWeici = 1;        //默认学校位次
    public static List<String> fixedOrder = Arrays.asList("物", "化", "生", "政", "历", "地");



    public static String PanoSourceProj = "PanoSourceProj";  //pano 源文件文件夹
    public static String PanoProj = "PanoProj"; // pano 新建的文件夹
    public static String VTOURFOLDER = "vtour";
    public static String VTOURXML = "tour.xml";
    public static String VTOURHTML = "tour.html";

    public static String USERID = "UserId"; //上传素材带的用户id
    public static String RESTYPE = "ResType";//上传素材带的具体类型
    public static String RESID = "ResID";//上传素材带的原素材id
    public static String ParentId = "ParentId";//上传素材带的父目录
    public static String OrgId = "OrgId";//上传素材带的部门信息

    public static String GROUP1NAME = "默认一级分组";
    public static String GROUP2NAME = "默认二级分组";

    public static Integer OrderStartId = 1;  //顺序开始次序
    public static Integer[] FovRange = new Integer[]{70, 140, 120};//最小视角, 最大视角, 默认视角
    public static Integer[] VLookRange = new Integer[]{-90, 90};//俯仰角最小  最大
    public static Integer[] HLookRange = new Integer[]{-180, 180};//左右视角 最小  最大
    public static Integer KeepInitialPerspective = 1;//自动巡游时候, 保持初始视角

    public static Integer SUPERADMIN = 0;//超级管理员权限

    public static String PROJTYPES = "房产|空间设计|线上会馆|博物馆|大合影|风光|村落|景区|商业";

    public static boolean InTestMode = false;//正在测试

    public static String SynImgUrl = "http://report.cr12g.com.cn:18080/api/uploadFileIcon";

    public static String[] ClassLvl =  new String[]{"A-","A","A+","B-","B","B+","C-","C","C+"}; // pano 新建的文件夹

    public static String UnLimit = "不限";

    public static boolean NewVersion = true;

    public static boolean FastTest2023 = false;//测试

    public static String EXCEL_KEY = "excel";
    public static String ICONFILE_KEY = "icon";
    public static String UpdateEXCEL_KEY = "updateExcel";
    public static String BackupEXCEL_KEY = "BackupEXCEL_KEY";
    public static String MODE_KEY = "mode";

    public static Integer BATCH_SIZE = 5000;


    public static String NewDataKEY = "增";
    public static String DelDataKEY = "删";
    public static String UpdateKEY = "改";
    public static String UnchnageKEY = "无";

    public static boolean FilterByTemplateRange = true;//按模板分数区间过滤
}
