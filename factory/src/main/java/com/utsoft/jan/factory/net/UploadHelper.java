package com.utsoft.jan.factory.net;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.utsoft.jan.utils.HashUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.DateFormat;

/**
 * Created by Administrator on 2019/7/24.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.net
 */
public class UploadHelper {

    private static final String TAG = "UploadHelper";

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
            + "EndpointSuffix=core.chinacloudapi.cn;"
            + "AccountName=exeutest;"
            + "AccountKey=RUYNkTdIlKGTfVYwsLUssxOCnL0U13psXgOQWpFEk3m5wf/kXATaIT6lMCOgtscvCFeMtrAWg0DemLPpJlbdlA==";


    public static CloudBlobContainer getBlobContainer(){

        try {
            CloudStorageAccount account = CloudStorageAccount
                    .parse(storageConnectionString);

            // Create a blob service client
            CloudBlobClient blobClient = account.createCloudBlobClient();

            // Get a reference to a container
            // The container name must be lower case
            // Append a random UUID to the end of the container name so that
            // this sample can be run more than once in quick succession.
            CloudBlobContainer container = blobClient.getContainerReference("ZWLbasicscontainer");

//            + UUID.randomUUID().toString().replace("-", "")

            // Create the container if it does not exist
            container.createIfNotExists();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    public static String upload(String objKey, String path){
        try {
            // Make the container public
            // Create a permissions object
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Include public access in the permissions object
            containerPermissions
                    .setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

            CloudBlobContainer container = getBlobContainer();

            // Set the permissions on the container
            container.uploadPermissions(containerPermissions);

            /**
             * 每月存储月份分文件夹
             */
            CloudBlobDirectory directoryReference = container.getDirectoryReference(getDateString());

            // Upload 3 blobs
            // Get a reference to a blob in the container
            CloudBlockBlob blob1 = directoryReference
                    .getBlockBlobReference(objKey);

            // Upload text to the blob
            blob1.uploadFromFile(path);

            // List the blobs in a container, loop over them and
            // output the URI of each of them
            for (ListBlobItem blobItem : container.listBlobs()) {

                return blobItem.getUri().toString();
            }

            return null;
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String uploadPortrait(String path){
        String portraitKey = getPortraitKey(path);
        return upload(portraitKey,path);
    }

    private static String getPortraitKey(String path) {
        String md5String = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("portrait%s%s",dateString,md5String);
    }

    /**
     * 分月存储，取用时间格式
     */
    private static String getDateString(){
       return android.text.format.DateFormat.format("yyyyMM",System.currentTimeMillis()).toString();
    }

//    public  void init(){
//        // Setup the cloud storage account.
//
//            // Download the blob
//            // For each item in the container
//            for (ListBlobItem blobItem : container.listBlobs()) {
//                // If the item is a blob, not a virtual directory
//                if (blobItem instanceof CloudBlockBlob) {
//                    // Download the text
//                    CloudBlockBlob retrievedBlob = (CloudBlockBlob) blobItem;
//                    Log.e(TAG, "init: downloadText"+retrievedBlob.downloadText() );
//                }
//            }
//            // Delete the blobs
////            blob1.deleteIfExists();
////            blob2.deleteIfExists();
////            blob3.deleteIfExists();
//
//            // Delete the container
////            container.deleteIfExists();
//    }
}
