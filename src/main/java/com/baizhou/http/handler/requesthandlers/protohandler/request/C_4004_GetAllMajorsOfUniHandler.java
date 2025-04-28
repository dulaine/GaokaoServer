package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumLastYear;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.UniMajorManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class C_4004_GetAllMajorsOfUniHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetAllMajorsOfUni_4004 req = C_GetAllMajorsOfUni_4004.parseFrom(data);

            String schoolName = req.getSchoolName();
            EnumPici pici = EnumPici.Get(req.getPici());
//            List<MajorInfoYear> year1 = UniMajorManager.GetInstance().GetAllMajorBySchoolNameAndPici(schoolName, pici, EnumLastYear.LastYear1);
//            List<MajorInfoYear> year2 = UniMajorManager.GetInstance().GetAllMajorBySchoolNameAndPici(schoolName, pici, EnumLastYear.LastYear2);
//            List<MajorInfoYear> year3 = UniMajorManager.GetInstance().GetAllMajorBySchoolNameAndPici(schoolName, pici, EnumLastYear.LastYear3);

            Calendar cal = Calendar.getInstance();
            String yearStr = req.getYear();
            Integer year = StringUtils.isEmpty(yearStr) ? 2023 :  Integer.parseInt(yearStr);// 2023;// cal.get(Calendar.YEAR);

            List<String> last3Year = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                last3Year.add((year - i) + "");
            }

            List<MajorInfoYear> year1 = UniMajorManager.GetInstance().GetAllMajorBySchoolNameAndPiciAndYear(schoolName, pici,last3Year.get(0));
            List<MajorInfoYear> year2 = UniMajorManager.GetInstance().GetAllMajorBySchoolNameAndPiciAndYear(schoolName, pici, last3Year.get(1));
            List<MajorInfoYear> year3 = UniMajorManager.GetInstance().GetAllMajorBySchoolNameAndPiciAndYear(schoolName, pici, last3Year.get(2));

            PlayerProtoResponse.SendGetAllMajorsOfUniResponse(context, EnumErrorMsg.None, last3Year, year1, year2, year3);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

