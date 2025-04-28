package com.baizhou.util;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import io.netty.handler.codec.http.multipart.FileUpload;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class OssFileUtil {
    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    public static String OssRegion = "oss-cn-beijing";
    private static String endpoint = "http://" + OssRegion + ".aliyuncs.com/";

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    private static String accessKeyId = "";
    private static String accessKeySecret = "";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
    public static String bucketName = "";

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。
    private static String firstKey = "my-first-key"; //http://baizhoudata.oss-cn-beijing.aliyuncs.com/healthCommunity/upload_-2028272230814884062_5_0.jpg

    public static void Init(String key, String secrete, String buckName){
        accessKeyId = key;
        accessKeySecret = secrete;
        bucketName = buckName;
    }

    public static String UploadImg(String imgBase64, String fileName) {

        String filePath = "";//上传的文件路径
        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {

//            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
//            if (ossClient.doesBucketExist(bucketName)) {
//                System.out.println("您已经创建Bucket：" + bucketName + "。");
//            } else {
//                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
//                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
//                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
//                ossClient.createBucket(bucketName);
//            }
//
//            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
//            BucketInfo info = ossClient.getBucketInfo(bucketName);
//            System.out.println("Bucket " + bucketName + "的信息如下：");
//            System.out.println("\t数据中心：" + info.getBucket().getLocation());
//            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
//            System.out.println("\t用户标志：" + info.getBucket().getOwner());


            // 创建PutObjectRequest对象。
            String content = imgBase64;// "Hello OSS";

            BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] b = decoder.decodeBuffer(content);
//            for (int i = 0; i < b.length; ++i) {
//                if (b[i] < 0) {//调整异常数据
//                    b[i] += 256;
//                }
//            }

            //压缩图片
            b = ImgCompressUtil.CompressPic(b, 1, fileName, 0.5);

// <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(b));//new ByteArrayInputStream(content.getBytes()));

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();

            //
            filePath = "http://" + bucketName + "." + OssRegion + ".aliyuncs.com/" + fileName;

//            // 把字符串存入OSS，Object的名称为firstKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
//            InputStream is = new ByteArrayInputStream("Hello OSS".getBytes());
//            ossClient.putObject(bucketName, fileName, is);
//            System.out.println("Object：" + fileName + "存入OSS成功。");

//            // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
//            PutObjectRequest putObjectRequest = new PutObjectRequest("<yourBucketName>", "<yourObjectName>", new ByteArrayInputStream(content.getBytes()));
//            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);


//            // 下载文件。详细请参看“SDK手册 > Java-SDK > 下载文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/download_object.html?spm=5176.docoss/sdk/java-sdk/manage_object
//            OSSObject ossObject = ossClient.getObject(bucketName, firstKey);
//            InputStream inputStream = ossObject.getObjectContent();
//            StringBuilder objectContent = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            while (true) {
//                String line = reader.readLine();
//                if (line == null)
//                    break;
//                objectContent.append(line);
//            }
//            inputStream.close();
//            System.out.println("Object：" + firstKey + "的内容是：" + objectContent);
//
//            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
//            String fileKey = "README.md";
//            ossClient.putObject(bucketName, fileKey, new File("README.md"));
//            System.out.println("Object：" + fileKey + "存入OSS成功。");
//
//            // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
//            ObjectListing objectListing = ossClient.listObjects(bucketName);
//            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
//            System.out.println("您有以下Object：");
//            for (OSSObjectSummary object : objectSummary) {
//                System.out.println("\t" + object.getKey());
//            }
//
//            // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
//            ossClient.deleteObject(bucketName, firstKey);
//            System.out.println("删除Object：" + firstKey + "成功。");
//            ossClient.deleteObject(bucketName, fileKey);
//            System.out.println("删除Object：" + fileKey + "成功。");


        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
            return filePath;
        }
    }

    public static String UploadIFile(FileUpload fileUpload, String fileName) {

        String filePath = "";//上传的文件路径
        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {


            byte[] b = fileUpload.get();

// <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(b));//new ByteArrayInputStream(content.getBytes()));

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();

            //
            filePath = "http://" + bucketName + "." + OssRegion + ".aliyuncs.com/" + fileName;

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
            return filePath;
        }
    }

    /**
     * String fileUrl = OssFileUtil.UploadImg(base64, "citydesignv2/screenshot/" + uid + ".jpg");
     * String filePath = prePicUrl.substring(prePicUrl.lastIndexOf("citydesignv2/screenshot/"));
       OssFileUtil.DeleteFile(filePath);
     * @param fileNmae
     */
    public static void DeleteFile(String fileNmae){

        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileNmae);

// 关闭OSSClient。
        ossClient.shutdown();

    }
}
