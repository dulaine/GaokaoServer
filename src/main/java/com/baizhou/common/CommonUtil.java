package com.baizhou.common;


import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumGender;
import com.baizhou.data.enumdefine.EnumStringDefine;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.GameDataManager;
import com.baizhou.manager.PhysLimitationManager;
import com.baizhou.util.FileUtils;
import com.msg.*;
import net.bytebuddy.utility.RandomString;
import sun.util.resources.cldr.ne.CalendarData_ne_NP;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {
    public static String ByteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String CreateNonceStr() {
        return UUID.randomUUID().toString();
    }

    public static String CreateTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


    public static boolean isNumeric(String str) {
        if(str.length() == 1 && str.equals(".")) return false;
        for (int i = 0; i < str.length(); i++) {
//            System.out.println(str.charAt(i));
            if (str.charAt(i) == '.') continue;
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasNumeric(String str) {
//        for (int i = 0; i < str.length(); i++) {
////            System.out.println(str.charAt(i));
//            if (str.charAt(i) == '.') continue;
//            if (!Character.isDigit(str.charAt(i))) {
//                return false;
//            }
//        }
         return  Character.isDigit(str.charAt(0));

    }

    //是否超过一天
    public static boolean IsPassedDate(Date checkDate) {
        Date now = new Date();
        long diff = now.getTime() - checkDate.getTime();
        int days = (int) (diff / (24 * 60 * 60 * 1000));
        return (days > 1);
    }

    public static boolean IsAnotherDay(Date date1, Date date2) {
        if (date1.getDay() != date2.getDay() ||
                date1.getDate() != date2.getDate() ||
                date1.getMonth() != date2.getMonth() ||
                date1.getYear() != date2.getYear()) {
            //System.out.println("is another day , date1:"+  date1.toString() + " date2:" + date2.toString());
            return true;
        }
        return false;
    }

    //两个日期相差几天
    public static int GetDiffDay(Date day1, Date day2) {
        Date day1Date = new Date(day1.getYear(), day1.getMonth(), day1.getDate());
        Date day2Date = new Date(day2.getYear(), day2.getMonth(), day2.getDate());
        long diff = Math.abs(day1Date.getTime() - day2Date.getTime());
        int days = (int) (diff / (24 * 60 * 60 * 1000));
        return days;
    }

    //是否是连续两天
    public static boolean IsNextDay(Date date1, Date date2) {
        if (CommonUtil.GetDiffDay(date1, date2) == 1 && CommonUtil.IsAnotherDay(date1, date2)) {
            return true;
        }
        return false;
    }


    //获取今日时间
    public static Date GetTodayDate() {
        Date date = new Date();
        return new Date(date.getYear(), date.getMonth(), date.getDate());
    }

    //添加addDay天
    public static Date AddDay(Date date, int addDay) {
//        Locale.setDefault(Locale.CHINA);
//        // GregorianCalendar是Calendar的子类
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.add(Calendar.DAY_OF_MONTH, addDay);// 日期减offset回到周一那天
//        return gc.getTime();
        return new Date(date.getTime() + addDay * 24 * 60 * 60 * 1000L);
    }

    public static Date GetTimestamp() {
        Date date = new Date();
        return date;
    }

    //获取这周的周一的日子
    public static Date FirstDayOfThisWeek() {
        Locale.setDefault(Locale.CHINA);

        // GregorianCalendar是Calendar的子类
        GregorianCalendar gc = new GregorianCalendar();
//        int today = gc.get(Calendar.DAY_OF_MONTH);
//        int month = gc.get(Calendar.MONTH);
//        // 设置月份的起始日期为1
////        gc.set(Calendar.DAY_OF_MONTH, 1);

        // 获取一周中的第几天
        int weekday = gc.get(Calendar.DAY_OF_WEEK);
        // 获取一周中的第一天
        int firstDayOfWeek = gc.getFirstDayOfWeek(); // 1-7:周日-周一

        //周一和今天的日期相差几天
        int offset = weekday - 2;
        if (weekday == 1) {
            offset += 7;
        }
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        gc.add(Calendar.DAY_OF_MONTH, -offset);// 日期减offset回到周一那天
        return gc.getTime();


//        // 计算月份第一行的缩进数
//        int indent = 0;// 缩进数
//        while (weekday != firstDayOfWeek) {
//            indent++;
//            gc.add(Calendar.DAY_OF_MONTH, -1);// 日期减1
//            weekday = gc.get(Calendar.DAY_OF_WEEK);
//        }
//
//        // 打印星期名
//        String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
    }

    //返回1-7: 周一 - 周日
    public static int DayOfWeek() {
        Locale.setDefault(Locale.CHINA);
        GregorianCalendar gc = new GregorianCalendar();
        // 获取一周中的第几天
        int weekday = gc.get(Calendar.DAY_OF_WEEK);  // 1-7:周日-周6
        if (weekday == 1) weekday += 7;
        weekday -= 1; //改成1-7:周一-周日
        return weekday;
    }

    public static String FormatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static String FormatFileDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return formatter.format(date);
    }

    /**
     * 随机数 不包含to
     *
     * @param from
     * @param to
     * @return
     */
    public static Integer RandomRange(Integer from, Integer to) {
        int ran2 = (int) (Math.random() * (to - from) + from);
        return ran2;
    }

    public static Double RandomRange(Float from, Float to) {
        return Math.random() * (to - from) + from;
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] RandomRangeMultiple(int min, int max, int n) {
        //如果n> 可选范围
        if (n > (max - min + 1) || max < min) {
            n = max - min + 1;
            int[] result = new int[n];
            for (int i = 0; i < n; i++) {
                result[i] = min + i;
            }
            return result;
        }

        //如果n数量>2/3的总量
        if (n >= (max - min + 1) * 2 / 3) {
            int num = (int) (Math.random() * (max - min));
            int[] result = new int[n];
            for (int i = 0; i < n; i++) {
                result[i] = min + num;
                num++;
                if (num > max - min) num = 0;
            }
            return result;
        }

        //否则
        int[] result = new int[n];
        int count = 0;
        Random randomGen = new Random();
        while (count < n) {
            int num = (int) (randomGen.nextInt(max - min + 1)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }


    //去除Json的"" 和 \转移符号
    public static String CleanJsonString(String tempString) {
        if (tempString.startsWith("\"")) tempString = tempString.substring(1);
        if (tempString.endsWith("\"")) tempString = tempString.substring(0, tempString.length() - 1);
        tempString = tempString.replace("\\", "");
        return tempString;
    }


    //保存新闻图片, 返回图片名字
    public static String SaveNewsImg(byte[] bytes, String filePrefix) {
        //  org.apache.commons.codec.binary.Base64 base64 = new Base64();
        //  byte[] bytes = base64Img.isEmpty() ? null : base64.decode(base64Img);

        if (bytes != null) {
            //                //二进制转字节
//                Base64 base64 = new Base64();
//                String backMsg = picInfo != null ?  ("data:image/jpg;base64,"+ base64.encodeToString(picInfo)): "";
            String uid = filePrefix + CommonUtil.FormatFileDate(new Date()) + "_" + new Random().nextLong();
            String imgPath = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.IMG_PATH.getStateID());
            //FileUtils.mkdir(imgPath);
            //组成文件名
            String fileName = uid + ".jpg";
            String fileFullName = MessageFormat.format("{0}/{1}", imgPath, fileName);
            try {
                //创建文件
                FileUtils.WriteBytesFile(fileFullName, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return fileName;
        } else {
            return null;
        }
    }

    //获取page信息
    public static com.msg.PagingInfo GetPageInfo(int totalPage, long totalElement, int curPage, int limit) {
        com.msg.PagingInfo.Builder pageInfo = com.msg.PagingInfo.newBuilder();
        pageInfo.setPageCount(totalPage);
        pageInfo.setRowcount(totalElement);//总行数
        pageInfo.setPageNo(curPage);
        pageInfo.setPageSize(limit);
        pageInfo.setStartLimt((curPage - 1) * limit);
        return pageInfo.build();
    }

    public static String GenLoginToken() {
        return new Date().getTime() + new RandomString().nextString();
    }


    //转成
    public static List<String> ConvSubj(List<String> examSubjs) {
        //"物","化 生 政 历 地

        String[] temp = new String[]{null, null, null, null, null, null};

        for (int i = 0; i < examSubjs.size(); i++) {
            for (int j = 0; j < ConstDefine.fixedOrder.size(); j++) {
                if (examSubjs.get(i).startsWith(ConstDefine.fixedOrder.get(j))) {

                    temp[j] = ConstDefine.fixedOrder.get(j);
//                    list.add(ConstDefine.fixedOrder.get(j));
                    break;
                }
            }
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == null) continue;
            list.add(temp[i]);
        }

        return list;
    }

    public static List<com.msg.UniMajorGroupInfo> ConvertMajorGroup(List<UniMajorInfo> list) {

        HashMap<String, List<UniMajorInfo>> groupDic = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            UniMajorInfo uniMajorInfo = list.get(i);
            String uniMajorCode = uniMajorInfo.getUniMajorCode();

            //如果专业组code 之前就没有 ; 新建Array
            List<UniMajorInfo> tempList = groupDic.getOrDefault(uniMajorCode, null);
            if (tempList == null) {
                tempList = new ArrayList<>();
                groupDic.put(uniMajorCode, tempList);
            }

            tempList.add(uniMajorInfo);
        }

        List<com.msg.UniMajorGroupInfo> groupInfos = new ArrayList<>();
        for (Map.Entry<String, List<UniMajorInfo>> groupEntry : groupDic.entrySet()) {
            String groupCode = groupEntry.getKey();
            List<UniMajorInfo> majorInfos = groupEntry.getValue();
//            if(majorInfos.size() > 0)
//            System.out.println("group code " + groupCode);
//            if(groupCode.equals("08292")){
//                System.out.println("group code " + groupCode);
//            }
            majorInfos.sort(new Comparator<UniMajorInfo>() {
                @Override
                public int compare(UniMajorInfo o1, UniMajorInfo o2) {
                    int one = (int) Float.parseFloat(o1.getMajor22LowScore());
                    int two = (int) Float.parseFloat(o2.getMajor22LowScore());
                    if (one == two) return 0;
                    return one - two > 0 ? -1 : 1;

//                    return Float.parseFloat(o1.getMajor22LowScore()) -  Float.parseFloat(o2.getMajor22LowScore()) > 0 ? -1 : 1;
                }
            });

            //初始化专业组
            com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
            groupProto.addAllMajors(majorInfos);

            //添加专业组
            groupInfos.add(groupProto.build());
        }

        groupInfos.sort(new Comparator<UniMajorGroupInfo>() {
            @Override
            public int compare(UniMajorGroupInfo o1, UniMajorGroupInfo o2) {
//                System.out.println("" + o1.getMajorsList().get(0).getScoreFor22() + "," + o2.getMajorsList().get(0).getScoreFor22() + ", " + o1.getMajorsList().get(0).getId() + "," + o1.getMajorsList().get(0).getMajorName()+ ", " + o2.getMajorsList().get(0).getId() + "," + o2.getMajorsList().get(0).getMajorName());
                int one = (int) Float.parseFloat(o1.getMajorsList().get(0).getScoreFor22());
                int two = (int) Float.parseFloat(o2.getMajorsList().get(0).getScoreFor22());
                if (one == two) return 0;
                return one - two > 0 ? -1 : 1;
            }
        });

        return groupInfos;
    }

    public static String AddBracket(String str) {
        return ConstDefine.BracketL + str + ConstDefine.BracketR;
    }

    public static String RemoveBracket(String str) {
        String temp = str.substring(1, str.length() - 1);
        return temp;
    }


    public static String ConvertIdToString(List<Long> tempIds) {
//        List<Long> tempIds = proto.getAuthorTeacherIdsList();
        List<String> tempStrs = new ArrayList<>();
        for (int i = 0; i < tempIds.size(); i++) {
            tempStrs.add(CommonUtil.AddBracket(tempIds.get(i) + ""));
        }
        return (StringParser.MakeString(tempStrs, ConstDefine.ItemSperator7));
    }


    //从大到小
    public static List<UniMajorInfo> SortGroupByMajor(List<UniMajorInfo> majorInfos, EnumGender enumgener){
        boolean isMale = enumgener == EnumGender.Male;
        majorInfos.sort(new Comparator<UniMajorInfo>() {
            @Override
            public int compare(UniMajorInfo o1, UniMajorInfo o2) {
                int one = (int) Float.parseFloat(isMale ? o1.getMajor22LowScore() : o1.getMajor22LowScoreNv() );
                int two = (int) Float.parseFloat(isMale ? o2.getMajor22LowScore(): o2.getMajor22LowScoreNv() );
                if (one == two) return 0;
                return one - two > 0 ? -1 : 1;

            }
        });

        return majorInfos;
    }

    //从大到小
    public static List<UniMajorGroupInfo> SortGroupByGroup(List<UniMajorGroupInfo> groupInfos){
        groupInfos.sort(new Comparator<UniMajorGroupInfo>() {
            @Override
            public int compare(UniMajorGroupInfo o1, UniMajorGroupInfo o2) {
//                System.out.println("" + o1.getMajorsList().get(0).getScoreFor22() + "," + o2.getMajorsList().get(0).getScoreFor22() + ", " + o1.getMajorsList().get(0).getId() + "," + o1.getMajorsList().get(0).getMajorName()+ ", " + o2.getMajorsList().get(0).getId() + "," + o2.getMajorsList().get(0).getMajorName());
                int one = (int) Float.parseFloat(o1.getMajorsList().get(0).getScoreFor22());
                int two = (int) Float.parseFloat(o2.getMajorsList().get(0).getScoreFor22());
                if (one == two) return 0;
                return one - two > 0 ? -1 : 1;
            }
        });

        return groupInfos;
    }



    /*****************************************************************************/
    //体检限报
    //转成proto
    public static List<PhysicLimitationInfo> ParsLimitationItemToProto(String scoreHist) {
        List<PhysicLimitationInfo> list = new ArrayList<>();
        if(scoreHist == null) return  list;

        List<String> hists = StringParser.SplitString(scoreHist, ConstDefine.ItemSperator6);
        for (int i = 0; i < hists.size(); i++) {
            String hist = hists.get(i);
            if(hist.trim().isEmpty()) continue;;
            list.add(ParseLimitationItemToSingleProto(hist));
        }

        return list;
    }

    //
    public static String ParseLimitationItemFromProto(List<PhysicLimitationInfo> protoList) {

        String str = "";
        if(protoList != null){
            for (int i = 0; i < protoList.size(); i++) {
                PhysicLimitationInfo item = protoList.get(i);
//            str += item.getExamName();
                str += ParseSingleLimitationItemFromProto(item);
                if (i < protoList.size() - 1) {
                    str += ConstDefine.ItemSperator6;
                }

            }
        }

        return str;
    }

    //转成proto
    private static PhysicLimitationInfo ParseLimitationItemToSingleProto(String hist) {
        Long id = Long.parseLong(hist);//Long.parseLong(items.get(0));
        PhysicLimitationInfo limitationInfoVO = PhysLimitationManager.GetInstance().GetLimitByType3(id);// DBProxy.Getinstance().LimitationInfoService.findOneById(id);
        return limitationInfoVO;
    }

    //
    private static String ParseSingleLimitationItemFromProto(PhysicLimitationInfo item) {

        String str = "";

//            scoreHistItem item = protoList.get(i);
        str += item.getId();
//        str += ConstDefine.ItemSperator5;
//        str += item.getLimit1StCls();
//        str += ConstDefine.ItemSperator5;
//        str += item.getLimit2NdCls();
//        str += ConstDefine.ItemSperator5;
//        str += item.getContent();
//        str += ConstDefine.ItemSperator5;
//            if (i < protoList.size() - 1) {
//                str += ConstDefine.ItemSperator6;
//            }

//        }

        return str;
    }

    /*****************************************************************************/


    public static List<com.msg.UniMajorGroupInfo> Filter(List<com.msg.UniMajorGroupInfo> templateGroupInfos1 , List<com.msg.UniMajorGroupInfo> groupInfos2){

        List<com.msg.UniMajorGroupInfo> filter = new ArrayList<>();
        for (int j = 0; j < templateGroupInfos1.size(); j++) {
            com.msg.UniMajorGroupInfo tempGroup = templateGroupInfos1.get(j);
            String uniMajorCode = tempGroup.getMajorsList().get(0).getUniMajorCode();

            for (int i = 0; i <groupInfos2.size() ; i++) {
                com.msg.UniMajorGroupInfo groupInfo = groupInfos2.get(i);
                String uniMajorCode2 = groupInfo.getMajorsList().get(0).getUniMajorCode();
                if(uniMajorCode.equals(uniMajorCode2)){
                    filter.add(tempGroup);
                    break;
                }

            }
        }


        return filter;
        
    }

    //按区间过滤
    public static List<com.msg.UniMajorGroupInfo> FilterByRankRange(List<com.msg.UniMajorGroupInfo> templateGroupInfos1 , Integer minScore, Integer maxScore, EnumGender gender){
        //转换位次
        Integer minRank = maxScore;
        Integer maxRank = minScore;
        if (maxScore < minScore) {
            minRank = minScore;
            maxRank = maxScore;
        } else {
            minRank = maxScore;
            maxRank = minScore;
        }
        //[maxRank , minRank] = [2000, 4000]位次
        //1. 从 第1条 开始, 找到 (最接近2000 & >=2000) 的   ,    和 (最接近 4000 & <= 4000 )的 数据
        //2.    返回 之间的数据 ;
        Integer curNearestOffsetMaxRank = Integer.MAX_VALUE;
        Integer curNearestOffsetMaxRank_Index = Integer.MAX_VALUE;
        Integer curNearestOffsetMinRank = Integer.MAX_VALUE;
        Integer curNearestOffsetMinRank_Index = -1;


        for (int j = 0; j < templateGroupInfos1.size(); j++) {
            com.msg.UniMajorGroupInfo tempGroup = templateGroupInfos1.get(j);

            //1. 检测 组分数, 专业最低分  在 [maxRank , minRank]  内
            //2. 如果在范围内
                //2.1 检测是否 更靠近max rank, 记录 最小index
                //2.2 检测是否 更靠近 minRank, 记录 最大index;
            //3 返回 max - minRank 范围内的 专业.

            //1. 检测 组分数, 专业最低分  在 [maxRank , minRank]  内
            boolean inRank = InRank(tempGroup, maxRank, minRank, gender);
            if(!inRank) continue;

            //2. 如果在范围内
            List<Integer> nearestOffsets = GetNearestOffset(tempGroup, maxRank, minRank, gender);
            Integer nearestMax = nearestOffsets.get(0);
            Integer nearestMin = nearestOffsets.get(1);
            //2.1 检测是否 更靠近max rank, 记录 最小index
            if(nearestMax <= curNearestOffsetMaxRank && j <= curNearestOffsetMaxRank_Index){
                curNearestOffsetMaxRank_Index = j;
                curNearestOffsetMaxRank = nearestMax;
            }
            //2.2 检测是否 更靠近 minRank, 记录 最大index;
            if(nearestMin <= curNearestOffsetMinRank && j >= curNearestOffsetMinRank_Index){
                curNearestOffsetMinRank_Index = j;
                curNearestOffsetMinRank = nearestMin;
            }

        }


        List<com.msg.UniMajorGroupInfo> filter = new ArrayList<>();
        //3 返回 max - minRank 范围内的 专业.
        if(curNearestOffsetMaxRank_Index <= curNearestOffsetMinRank_Index){
            for (int i = curNearestOffsetMaxRank_Index; i <= curNearestOffsetMinRank_Index; i++) {
                filter.add(templateGroupInfos1.get(i));
            }
        }

        return filter;

    }

    //检测 是否在范围内
    private static boolean InRank(com.msg.UniMajorGroupInfo uniMajorGroupInfo, Integer maxRank, Integer minRank, EnumGender gender){
        List<UniMajorInfo> list = uniMajorGroupInfo.getMajorsList();
        Integer size = list.size();
        boolean isMale = gender == EnumGender.Male;

        boolean inRank = false;

        //1 没有数据, 不满足
        if(size == 0) return inRank;

        //2 检测组分
        String groupRankStr = isMale ? list.get(0).getRankFor22() : list.get(0).getRankFor22Nv();
        Integer groupRank = (int) Float.parseFloat(groupRankStr);

        //2.1 组分数满足 条件
        if(groupRank > 0){
            inRank = groupRank >= maxRank && groupRank <= minRank;
            if(inRank) return inRank;
        }

        //3 检测专业分数
        for (int i = 0; i < size; i++) {
            UniMajorInfo majorInfo = list.get(i);
            //        Expression<Integer> rank22 = gender == EnumGender.Male ? root.get("rankFor22") : root.get("rankFor22Nv");
            //        Expression<Integer> major22 = gender == EnumGender.Male ? root.get("major22LowRank"): root.get("major22LowRankNv");

            //3.1 检测专业分数
            String majorLowRankStr = isMale ? majorInfo.getMajor22LowRank() : majorInfo.getMajor22LowRankNv();
            Integer majorLowRank = (int) Float.parseFloat(majorLowRankStr);

            //检测是否在范围内
            if(majorLowRank > 0){
                inRank = majorLowRank >= maxRank && majorLowRank <= minRank;
                if(inRank) return inRank;
            }
        }
        return  inRank;
    }

    //获取距离minRank 和 maxRank 最近的offset
    private static List<Integer> GetNearestOffset(com.msg.UniMajorGroupInfo uniMajorGroupInfo, Integer maxRank, Integer minRank, EnumGender gender){
        List<UniMajorInfo> list = uniMajorGroupInfo.getMajorsList();
        Integer size = list.size();
        boolean isMale = gender == EnumGender.Male;

        List<Integer> nearOffset = new ArrayList<>();
        Integer offsetToMax = Integer.MAX_VALUE;
        Integer offsetToMin = Integer.MAX_VALUE;

        //1 没有数据, 不满足
        if(size > 0){
            //2 检测组分
            String groupRankStr = isMale ? list.get(0).getRankFor22() : list.get(0).getRankFor22Nv();
            Integer groupRank = (int) Float.parseFloat(groupRankStr);

            //2.1 组分数满足 条件
            if(groupRank > 0){
                offsetToMax = Math.abs(groupRank - maxRank) ;
                offsetToMin = Math.abs(groupRank - minRank);
            }

            //3 检测专业分数
            for (int i = 0; i < size; i++) {
                UniMajorInfo majorInfo = list.get(i);
                //        Expression<Integer> rank22 = gender == EnumGender.Male ? root.get("rankFor22") : root.get("rankFor22Nv");
                //        Expression<Integer> major22 = gender == EnumGender.Male ? root.get("major22LowRank"): root.get("major22LowRankNv");

                //3.1 检测专业分数
                String majorLowRankStr = isMale ? majorInfo.getMajor22LowRank() : majorInfo.getMajor22LowRankNv();
                Integer majorLowRank = (int) Float.parseFloat(majorLowRankStr);

                //检测最接近maxrank 和minRank的offset
                if(majorLowRank > 0){
                    Integer offsetToMaxTemp = Math.abs(majorLowRank - maxRank);
                    Integer offsetToMinTemp = Math.abs(majorLowRank - minRank);
                    if(offsetToMaxTemp < offsetToMax) offsetToMax = offsetToMaxTemp;
                    if(offsetToMinTemp < offsetToMin) offsetToMin = offsetToMinTemp;
                }
            }
        }

        nearOffset.add(offsetToMax);
        nearOffset.add(offsetToMin);
        return  nearOffset;
    }


    //过滤模板中  包含对应 相关专业的院校组
    public static List<com.msg.UniMajorGroupInfo> FilterByValidMajor(List<com.msg.UniMajorGroupInfo> templateGroupInfos1 , List<Major1stClsInfo> relativeMajors ){
        if(relativeMajors.size() == 0) return  templateGroupInfos1;

        List<Long> majorIdList = GetMajorIdList(relativeMajors);

        List<com.msg.UniMajorGroupInfo> filter = new ArrayList<>();
        for (int j = 0; j < templateGroupInfos1.size(); j++) {
            com.msg.UniMajorGroupInfo tempGroup = templateGroupInfos1.get(j);
            List<com.msg.UniMajorInfo> tempMajorList =  tempGroup.getMajorsList();

            //遍历 找出 包含相关专业的
            for (int i = 0; i < tempMajorList.size(); i++) {
                com.msg.UniMajorInfo tempMajor = tempMajorList.get(i);

                if(ListContainAny(tempMajor.getMajorClsIdsList(), majorIdList)){
                    filter.add(tempGroup);
                    break;
                }

            }
        }

        return filter;

    }

    //解析 相关专业的 major id
    public static List<Long> GetMajorIdList(List<Major1stClsInfo> relativeMajors){
        List<Long> majorId = new ArrayList<>();

        for (int i = 0; i < relativeMajors.size(); i++) {
            Major1stClsInfo major1stClsInfo = relativeMajors.get(i);
            int cls2Count = major1stClsInfo.getMajor2NdClsInfoCount();

            for (int j = 0; j < cls2Count; j++) {
                Major2ndClsInfo major2ndClsInfo = major1stClsInfo.getMajor2NdClsInfo(j);
                int cls3Count = major2ndClsInfo.getMajor3RdClsInfoCount();

                for (int k = 0; k < cls3Count; k++) {
                    Major3rdClsInfo major3rdClsInfo = major2ndClsInfo.getMajor3RdClsInfo(k);
                    majorId.add(major3rdClsInfo.getId());
                }

            }
        }
        return majorId;
    }


    //checklist中 是否包含  containAny中任意一个
    public static boolean ListContainAny(List<Long> checkList, List<Long> containAny){
        for (int i = 0; i < containAny.size(); i++) {
            if(checkList.contains(containAny.get(i))) return  true;
        }
        return false;
    }

    /*****************************************************************************/
    //解析喜欢和不喜欢的专业

    //体检限报
    //转成proto
    public static List<Major1stClsInfo> ParsMajorItemToProto(String scoreHist) {
        List<Major1stClsInfo> list = new ArrayList<>();

        if(scoreHist != null && !scoreHist.isEmpty()){
            List<String> hists = StringParser.SplitString(scoreHist, ConstDefine.ItemSperator1Split);
            for (int i = 0; i < hists.size(); i++) {
                String hist = hists.get(i);
                list.add(ParseMajorToSingleProto(hist));
            }
        }
        return list;
    }

    //
    public static String ParseMajorItemFromProto(List<Major1stClsInfo> protoList) {

        String str = "";
        if(protoList == null) return str;
        for (int i = 0; i < protoList.size(); i++) {
            Major1stClsInfo item = protoList.get(i);
//            str += item.getExamName();
            str += ParseFromSingleProto(item);
            if (i < protoList.size() - 1) {
                str += ConstDefine.ItemSperator1;
            }

        }

        return str;
    }


    //转成proto
    //1类专业_2类专业%专业id, 专业名@专业id, 专业名:2类专业%专业id, 专业名@专业id, 专业名 |  1类专业_2类专业%专业id, 专业名
    private static Major1stClsInfo ParseMajorToSingleProto(String hist) {
//        List<scoreHistItem> list = new ArrayList<>();

//        List<String> hists = StringParser.SplitString(scoreHist, ConstDefine.ItemSperator6);
//        for (int i = 0; i < hists.size(); i++) {
//            String hist = hists.get(i);
        //1类专业_2类专业%专业id, 专业名@专业id, 专业名:2类专业%专业id, 专业名@专业id, 专业名
        List<String> items = StringParser.SplitString(hist, ConstDefine.ItemSperator3);
        if (items.size() == 0) {
            // System.out.println("eror");
        }
        com.msg.Major1stClsInfo.Builder builder = Major1stClsInfo.newBuilder();
        builder.setName(items.get(0).equals(ConstDefine.PlaceHolderForEmp) ? "" : items.get(0));

        List<Major2ndClsInfo> major2ndClsInfoList = new ArrayList<>();
        String lvl2 = items.get(1);
        List<String> item2s = StringParser.SplitString(lvl2, ConstDefine.ItemSperator2);
        for (int i = 0; i < item2s.size(); i++) {
            com.msg.Major2ndClsInfo.Builder builder2 = Major2ndClsInfo.newBuilder();

            //2类专业%专业id, 专业名@专业id, 专业名
            List<String> cls2items = StringParser.SplitString(item2s.get(i), ConstDefine.ItemSperator6);
            builder2.setName(cls2items.get(0).equals(ConstDefine.PlaceHolderForEmp ) ? "" : cls2items.get(0));

            //专业id, 专业名@专业id, 专业名
            String majorStrs = cls2items.size() < 2 ? "" : cls2items.get(1);
            List<String> majorInfos = StringParser.SplitString(majorStrs, ConstDefine.ItemSperator5);
            List<Major3rdClsInfo> major3rdClsInfoList = new ArrayList<>();
            for (int j = 0; j < majorInfos.size(); j++) {
                String majorInfo = majorInfos.get(j);
                List<String> majors = StringParser.SplitString(majorInfo, ConstDefine.ItemSperator7);

                com.msg.Major3rdClsInfo.Builder builder3 = Major3rdClsInfo.newBuilder();
                builder3.setId(Long.parseLong(majors.get(0)));
                builder3.setName(majors.get(1));

                major3rdClsInfoList.add(builder3.build());
            }

            builder2.addAllMajor3RdClsInfo(major3rdClsInfoList);
            major2ndClsInfoList.add(builder2.build());
        }

        builder.addAllMajor2NdClsInfo(major2ndClsInfoList);


//            builder.setId(items.size() <= 0 ? "emptyExam" : items.get(0));
//        builder.setChinese(items.size() <= 1 ? "0" : items.get(1));
//        builder.setMath(items.size() <= 2 ? "0" : items.get(2));

//            list.add(builder.build());
//        }
        return builder.build();

    }

    //
    private static String ParseFromSingleProto(Major1stClsInfo item) {

        String str = "";

//            scoreHistItem item = protoList.get(i);
        str += item.getName().trim().isEmpty() ? ConstDefine.PlaceHolderForEmp : item.getName().trim();
        str += ConstDefine.ItemSperator3;

        List<Major2ndClsInfo> major2ndClsInfos = item.getMajor2NdClsInfoList();
        if(major2ndClsInfos.size() == 0){
//            String major2Name = "";
            str +=   ConstDefine.PlaceHolderForEmp ;//: major2Name;
            str += ConstDefine.ItemSperator6;
        }else {
            for (int i = 0; i < major2ndClsInfos.size(); i++) {
                Major2ndClsInfo major2ndClsInfo = major2ndClsInfos.get(i);

                String major2Name = major2ndClsInfo.getName().trim();
                str += major2Name.isEmpty() ? ConstDefine.PlaceHolderForEmp : major2Name;
                str += ConstDefine.ItemSperator6;

                List<Major3rdClsInfo> major3rdClsInfoList = major2ndClsInfo.getMajor3RdClsInfoList();
                for (int j = 0; j < major3rdClsInfoList.size(); j++) {
                    Major3rdClsInfo major3rdClsInfo = major3rdClsInfoList.get(j);

                    str += major3rdClsInfo.getId();
                    str += ConstDefine.ItemSperator7;
                    str += major3rdClsInfo.getName();

                    if (j != major3rdClsInfoList.size() - 1) {
                        str += ConstDefine.ItemSperator5;
                    }
                }


                if (i != major2ndClsInfos.size() - 1) {
                    str += ConstDefine.ItemSperator2;
                }
            }

        }


//        str += ConstDefine.ItemSperator5;
//        str += item.getLimit1StCls();
//        str += ConstDefine.ItemSperator5;
//        str += item.getLimit2NdCls();
//        str += ConstDefine.ItemSperator5;
//        str += item.getContent();
//        str += ConstDefine.ItemSperator5;
//            if (i < protoList.size() - 1) {
//                str += ConstDefine.ItemSperator6;
//            }

//        }

        return str;
    }


    /*****************************************************************************/

    public static Long GetMajorInfoId(String year, String uniMajorCode, String majorCode) throws Exception {
//        if(uniMajorCode.length() > 5) uniMajorCode = uniMajorCode.substring(0, 5);
//        if(majorCode.length() > 2) majorCode = majorCode.substring(0, 2);

//        System.out.println("unicode 1 " + uniMajorCode + " majorcode " +majorCode);
        uniMajorCode = RemoveDot(uniMajorCode);
        majorCode =RemoveDot(majorCode);
//        System.out.println("unicode2  " + uniMajorCode + " majorcode " +majorCode);

        uniMajorCode = ReplaceAlpha(uniMajorCode);
//        System.out.println("unicode3  " + uniMajorCode + " majorcode " +majorCode);
        String uid = year + uniMajorCode + majorCode;

//        if(uid.indexOf(".") >=0 ){
//            System.out.println("");
//        }
        Long longId = Long.parseLong(uid);
        return longId;
    }

    public static String RemoveDot(String code){
        Integer indx = code.indexOf('.');
        if(indx >= 0){
            code = code.substring(0,indx);
        }
        return code;
    }


    public static String ReplaceAlpha(String str) throws Exception {
//        if(str.length() == 1 && str.equals(".")) return false;
        String ret = "";
        for (int i = 0; i < str.length(); i++) {
//            System.out.println(str.charAt(i));
//            if (str.charAt(i) == '.') continue;
            char temp = str.charAt(i);
            if (!Character.isDigit(temp)) {
                if(Character.isAlphabetic(temp)){
                    //非数字, 是字母
                    ret += Character.getNumericValue(temp)  ;
                }else {
                    //其他非数字
//                    throw new Exception("院校池代码中 包含乱码 " + str);
                    System.out.println("院校池代码中 包含乱码 " + str);
                }
            }else {
                ret += temp;
            }
        }
        return ret;
    }


    /*****************************************************************************/






    /*****************************************************************************/




}
