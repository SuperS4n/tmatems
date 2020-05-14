package cn.shiwensama.utils;

import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shiwensama
 * @create: 2020-05-01
 * @description: fastDfs 工具类
 **/
@Component
public class UploadService {

    @Value("${fastdfs.tracker_servers}")
    private String trackerServers;

    @Value("${fastdfs.connect_timeout_in_seconds}")
    private int connectTimeout;

    @Value("${fastdfs.network_timeout_in_seconds}")
    private int networkTimeout;

    @Value("${fastdfs.charset}")
    private String charset;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @return
     */
    public Map<String, Object> upload(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new RuntimeException("文件不能为空");
        }
        // 上传至fastDFS, 返回文件id
        String fileId = this.fdfsUpload(multipartFile);
        if (StringUtils.isEmpty(fileId)) {
            System.out.println("上传失败");
            throw new RuntimeException("上传失败");
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("fileId", fileId);
        return map;
    }

    /**
     * 上传至fastDFS
     *
     * @param multipartFile
     * @return 文件id
     */
    private String fdfsUpload(MultipartFile multipartFile) {
        // 1. 初始化fastDFS的环境
        initFdfsConfig();
        // 2. 获取trackerClient服务
        TrackerClient trackerClient = new TrackerClient();
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3. 获取storage服务
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            // 4. 获取storageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            // 5. 上传文件 (文件字节, 文件扩展名, )
            // 5.1 获取文件扩展名
            String originalFilename = multipartFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 5.2 上传
            return storageClient1.upload_file1(multipartFile.getBytes(), extName, null);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * 文件删除
     * @param savepath
     * @return
     */
    public boolean fdfsDelete(String savepath) {
        // 1. 初始化fastDFS的环境
        initFdfsConfig();
        // 2. 获取trackerClient服务
        TrackerClient trackerClient = new TrackerClient();
        //存储组
        String group = "";
        //存储路径
        String path = "";

        try {

            int secondindex = savepath.indexOf("/", 2);
            group = savepath.substring(0, secondindex);
            path = savepath.substring(secondindex + 1);
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3. 获取storage服务
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            // 4. 获取storageClient
            StorageClient storageClient = new StorageClient(trackerServer, storeStorage);
            // 5. 删除文件
            int result = storageClient.delete_file(group,path);

            if (result != 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * 下载文件
     *
     * @param url
     * @return 文件id
     */
    private byte[] fdfsDownload(String url) {
        // 1. 初始化fastDFS的环境
        initFdfsConfig();
        // 2. 获取trackerClient服务
        TrackerClient trackerClient = new TrackerClient();
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3. 获取storage服务
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            // 4. 获取storageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            // 5. 下载文件
            return storageClient1.download_file1(url);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * 初始化fastDFS的环境
     */
    private void initFdfsConfig() {
        try {
            ClientGlobal.initByTrackers(trackerServers);
            ClientGlobal.setG_connect_timeout(connectTimeout);
            ClientGlobal.setG_network_timeout(networkTimeout);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
