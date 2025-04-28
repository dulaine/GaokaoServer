package com.baizhou.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baizhou.data.enumdefine.EnumStringDefine;
import com.baizhou.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class WorkingDayManager {
    private static WorkingDayManager instance;

    public static WorkingDayManager GetInstance() {
        if (instance == null) {
            instance = new WorkingDayManager();
        }
        return instance;
    }

    //定义两个List，一个存放节假日日期，另一个存放调休的工作日期
    private static List<String> HOLIDAY_LIST = new ArrayList<>();
    private static List<String> SPECIAL_WORKDAY_LIST = new ArrayList<>();

    private int FromHour = 8;
    private int ToHour = 20;

    public boolean UsingConfig(){
        boolean inlimit = GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.USE_WORKING_DAY.getStateID()).equals("1");
        return inlimit;
    }

    public void Init() {
        if(!UsingConfig()) return;
        FromHour = Integer.parseInt(GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.WORKING_DAY_FROM.getStateID()));
        ToHour = Integer.parseInt(GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.WORKING_DAY_TO.getStateID()));

        Map<String, String> param = new HashMap<String, String>() {
            {
                put("key", "4c781232a9273508d1e74a2b47699761");//这里key值是注册天行API账号是给的
                put("type", "1");
            }
        };

        //获取当前年份，循环调用3次，拿到3年的数据
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 5; i++) {
            param.put("date", "" + (currentYear - i));
            String url = "http://api.tianapi.com/jiejiari/index";
            String response = HttpClientUtil.doGet(url, param, null);// HttpUtil.get(url, param);
            JSONObject resObj = JSON.parseObject(response);
            int code = (int) resObj.get("code");
            if (code == 200) {
                JSONArray newslist = resObj.getJSONArray("newslist");
                for (Object listObj : newslist) {
                    JSONObject obj = (JSONObject) listObj;
                    String holidays = (String) obj.get("vacation");
                    String[] holidayArray = holidays.split("\\|");
                    HOLIDAY_LIST.addAll(Arrays.asList(holidayArray));
                    String remark = (String) obj.get("remark");
                    if (StringUtils.isNotEmpty(remark)) {
                        String[] special = remark.split("\\|");
                        SPECIAL_WORKDAY_LIST.addAll(Arrays.asList(special));
                    }
                }
            }

        }
    }

    public boolean IsWorkingDay(Date date) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String current = formatter.format(date);

        int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
            if (!HOLIDAY_LIST.contains(current)) {
                return true;
            }
        }

        if (SPECIAL_WORKDAY_LIST.contains(current)) {
            return true;
        }

        return false;
    }

    private boolean lastCheckResult = false;
    private String lastCheckDate = null;

    public boolean IsTodayWorkingDay() {
        if(!UsingConfig()) return true;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String current = formatter.format(date);

        if (!current.equals(lastCheckDate)) {
            lastCheckDate = current;
            lastCheckResult = IsWorkingDay(date);
        }

        //如果是工作日, 看是否在8-20点之间
        if (lastCheckResult) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            lastCheckResult = hour >= FromHour && hour <= ToHour;
        }

        return lastCheckResult;
    }
}
