package com.baizhou.http.handler;


import com.baizhou.http.handler.requesthandlers.*;
import com.baizhou.http.handler.requesthandlers.webMsgHandler.*;

public class RequestHandlerFactory {

    public static BaseRequestHandler CreateHandler(String path) {
        switch (path) {
            case "/proto": {
                return new ProtoDispathHandler();
            }
           case "/updateDB": {
                return new getUpdateNewDBHandler();
            }
            case "/updateYFYDDB": {
                return new getUpdateYFYDHandler();
            }
            case "/tool/postExcel": {
                return new postExcelMultiFileHandler();
            }

//            case "/user/postPano": {
//                return new postPanoPicHandler();
//            }
//            case "/user/postRes": {
//                return new postResHandler();
//            }
//            case "/user/postCover": {
//                return new postCoverHandler();
//            }
//            case "/user/beforePostRes": {
//                return new postPanoPicPrepareHandler();
//            }
//            case "/user/postUserImg": {
//                return new postUserResHandler();
//            }
            default: {
                System.out.println("没有对应Path的请求:" + path);
                return null;
            }
        }
    }
}
