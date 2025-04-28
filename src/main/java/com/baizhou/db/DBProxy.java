package com.baizhou.db;

import com.baizhou.core.service.*;
import com.baizhou.core.service.impl.*;
import org.springframework.context.ApplicationContext;

public class DBProxy {
    private static DBProxy _instance;
    private ApplicationContext applicationContext;

    //    public AdminUserInfoService AdminUserInfoService;
    public ClientInfoService ClientInfoService;
    public FormInfoService FormInfoService;
    //    public gameDataRecordService GameDataRecordService;
    public MajorClsInfoService MajorClsInfoService;
    public OrderInfoService OrderInfoService;
    public ScoreToRankInfoService ScoreToRankInfoService;
//    public UniMajorInfoAService UniMajorInfoAService;
//    public UniMajorInfoALatestService UniMajorInfoALatestService;
//    public UniMajorInfoBService UniMajorInfoBService;
//    public UniMajorInfoBLatestService UniMajorInfoBLatestService;
//    public UniMajorInfoPreAService UniMajorInfoPreAService;
//    public UniMajorInfoPreALatestService UniMajorInfoPreALatestService;
//    public UniMajorInfoPreBService UniMajorInfoPreBService;
//    public UniMajorInfoPreBLatestService UniMajorInfoPreBLatestService;
    public UsersResService UsersResService;
    public MajorInfoY1Service MajorInfoY1Service;
    public MajorInfoY2Service MajorInfoY2Service;
    public MajorInfoY3Service MajorInfoY3Service;
    public IconMajorInfoService IconMajorInfoService;
    public LimitationInfoService LimitationInfoService;


    public UniGroupInfoALatestService UniGroupInfoALatestService;
    public UniGroupInfoBLatestService UniGroupInfoBLatestService;
    public UniGroupInfoPreALatestService UniGroupInfoPreALatestService;
    public UniGroupInfoPreBLatestService UniGroupInfoPreBLatestService;
    public UniGroupInfoZhuanKeLatestService UniGroupInfoZhuanKeLatestService;
    public UniGroupInfoZXALatestService UniGroupInfoZXALatestService;
    public UniGroupInfoZXBLatestService UniGroupInfoZXBLatestService;

    public UniMajorInfoAService UniMajorInfoAService;
    public UniMajorInfoALatestService UniMajorInfoALatestService;
    public UniMajorInfoBService UniMajorInfoBService;
    public UniMajorInfoBLatestService UniMajorInfoBLatestService;
    public UniMajorInfoPreAService UniMajorInfoPreAService;
    public UniMajorInfoPreALatestService UniMajorInfoPreALatestService;
    public UniMajorInfoPreBService UniMajorInfoPreBService;
    public UniMajorInfoPreBLatestService UniMajorInfoPreBLatestService;
    public UniMajorInfoZhuanKeService UniMajorInfoZhuanKeService;
    public UniMajorInfoZhuanKeLatestService UniMajorInfoZhuanKeLatestService;
    public UniMajorInfoZXAService UniMajorInfoZXAService;
    public UniMajorInfoZXALatestService UniMajorInfoZXALatestService;
    public UniMajorInfoZXBService UniMajorInfoZXBService;
    public UniMajorInfoZXBLatestService UniMajorInfoZXBLatestService;


    public PhysicLimitationInfoService PhysicLimitationInfoService;
    public TemplateInfoService TemplateInfoService;
    public MajorInfoYAllService MajorInfoYAllService;
//    public UniGroupInfoALatestService UniGroupInfoALatestService;
    public LatestVerInfoService LatestVerInfoService;

    public static DBProxy Getinstance() {
        if (_instance == null) {
            _instance = new DBProxy();
        }
        return _instance;
    }

    public void SetApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public ApplicationContext GetApplicationContext() {
        return applicationContext;
    }

    /*
    获取DBService
     */
    public void InitDBProxy() {
        this.ClientInfoService = this.applicationContext.getBean(ClientInfoServiceImpl.class);
        this.FormInfoService = this.applicationContext.getBean(FormInfoServiceImpl.class);
//        this.GameDataRecordService = this.applicationContext.getBean(gameDataRecordServiceImpl.class);
        this.MajorClsInfoService = this.applicationContext.getBean(MajorClsInfoServiceImpl.class);
        this.OrderInfoService = this.applicationContext.getBean(OrderInfoServiceImpl.class);
        this.ScoreToRankInfoService = this.applicationContext.getBean(ScoreToRankInfoServiceImpl.class);
//        this.UniMajorInfoAService = this.applicationContext.getBean(UniMajorInfoAServiceImpl.class);
//        this.UniMajorInfoALatestService = this.applicationContext.getBean(UniMajorInfoALatestServiceImpl.class);
//        this.UniMajorInfoBService = this.applicationContext.getBean(UniMajorInfoBServiceImpl.class);
//        this.UniMajorInfoBLatestService = this.applicationContext.getBean(UniMajorInfoBLatestServiceImpl.class);
//        this.UniMajorInfoPreAService = this.applicationContext.getBean(UniMajorInfoPreAServiceImpl.class);
//        this.UniMajorInfoPreALatestService = this.applicationContext.getBean(UniMajorInfoPreALatestServiceImpl.class);
//        this.UniMajorInfoPreBService = this.applicationContext.getBean(UniMajorInfoPreBServiceImpl.class);
//        this.UniMajorInfoPreBLatestService = this.applicationContext.getBean(UniMajorInfoPreBLatestServiceImpl.class);
        this.UsersResService = this.applicationContext.getBean(UsersResServiceImpl.class);
        this.MajorInfoY1Service = this.applicationContext.getBean(MajorInfoY1ServiceImpl.class);
        this.MajorInfoY2Service = this.applicationContext.getBean(MajorInfoY2ServiceImpl.class);
        this.MajorInfoY3Service = this.applicationContext.getBean(MajorInfoY3ServiceImpl.class);
        this.IconMajorInfoService = this.applicationContext.getBean(IconMajorInfoServiceImpl.class);
        this.LimitationInfoService = this.applicationContext.getBean(LimitationInfoServiceImpl.class);

        this.PhysicLimitationInfoService = this.applicationContext.getBean(PhysicLimitationInfoServiceImpl.class);
        this.TemplateInfoService = this.applicationContext.getBean(TemplateInfoServiceImpl.class);
//        this.UniGroupInfoALatestService = this.applicationContext.getBean(UniGroupInfoALatestServiceImpl.class);



        this.UniGroupInfoALatestService = this.applicationContext.getBean(UniGroupInfoALatestServiceImpl.class);
        this.UniGroupInfoBLatestService = this.applicationContext.getBean(UniGroupInfoBLatestServiceImpl.class);
        this.UniGroupInfoPreALatestService = this.applicationContext.getBean(UniGroupInfoPreALatestServiceImpl.class);
        this.UniGroupInfoPreBLatestService = this.applicationContext.getBean(UniGroupInfoPreBLatestServiceImpl.class);
        this.UniGroupInfoZhuanKeLatestService = this.applicationContext.getBean(UniGroupInfoZhuanKeLatestServiceImpl.class);
        this.UniGroupInfoZXALatestService = this.applicationContext.getBean(UniGroupInfoZXALatestServiceImpl.class);
        this.UniGroupInfoZXBLatestService = this.applicationContext.getBean(UniGroupInfoZXBLatestServiceImpl.class);

        this.UniMajorInfoAService = this.applicationContext.getBean(UniMajorInfoAServiceImpl.class);
        this.UniMajorInfoALatestService = this.applicationContext.getBean(UniMajorInfoALatestServiceImpl.class);
        this.UniMajorInfoBService = this.applicationContext.getBean(UniMajorInfoBServiceImpl.class);
        this.UniMajorInfoBLatestService = this.applicationContext.getBean(UniMajorInfoBLatestServiceImpl.class);
        this.UniMajorInfoPreAService = this.applicationContext.getBean(UniMajorInfoPreAServiceImpl.class);
        this.UniMajorInfoPreALatestService = this.applicationContext.getBean(UniMajorInfoPreALatestServiceImpl.class);
        this.UniMajorInfoPreBService = this.applicationContext.getBean(UniMajorInfoPreBServiceImpl.class);
        this.UniMajorInfoPreBLatestService = this.applicationContext.getBean(UniMajorInfoPreBLatestServiceImpl.class);
        this.UniMajorInfoZhuanKeService = this.applicationContext.getBean(UniMajorInfoZhuanKeServiceImpl.class);
        this.UniMajorInfoZhuanKeLatestService = this.applicationContext.getBean(UniMajorInfoZhuanKeLatestServiceImpl.class);
        this.UniMajorInfoZXAService = this.applicationContext.getBean(UniMajorInfoZXAServiceImpl.class);
        this.UniMajorInfoZXALatestService = this.applicationContext.getBean(UniMajorInfoZXALatestServiceImpl.class);
        this.UniMajorInfoZXBService = this.applicationContext.getBean(UniMajorInfoZXBServiceImpl.class);
        this.UniMajorInfoZXBLatestService = this.applicationContext.getBean(UniMajorInfoZXBLatestServiceImpl.class);

        this.MajorInfoYAllService = this.applicationContext.getBean(MajorInfoYAllServiceImpl.class);

        this.LatestVerInfoService = this.applicationContext.getBean(LatestVerInfoServiceImpl.class);
    }

}
