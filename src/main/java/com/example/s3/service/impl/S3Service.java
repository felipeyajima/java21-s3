package com.example.s3.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.s3.representation.BucketObjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {
    //private String bucketName="opa";
    private final AmazonS3 s3Client;
    @Autowired
    public S3Service(AmazonS3 s3Client){
        this.s3Client=s3Client;
    }

    public List<String> listObjects(String bucketName){
        ObjectListing objectListing=s3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());

    }
    public void putObject(String bucketName, BucketObjectRepresentation representation) throws IOException {
        boolean publicObject = false;
        String objectName = representation.getObjectName();
        String objectValue = representation.getText();

        File file = new File("." + File.separator + objectName);
        FileWriter fileWriter = new FileWriter(file, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(objectValue);
        printWriter.flush();
        printWriter.close();

        System.out.println(bucketName);
        System.out.println(objectName);
        System.out.println(objectValue);

        try {
            if(publicObject) {
                var putObjectRequest = new PutObjectRequest(bucketName, objectName, file).withCannedAcl(CannedAccessControlList.PublicRead);
                s3Client.putObject(putObjectRequest);

            } else {
                var putObjectRequest = new PutObjectRequest(bucketName, objectName, file).withCannedAcl(CannedAccessControlList.Private);
                s3Client.putObject(putObjectRequest);



            }
        } catch (Exception e){
            System.out.println("error");//log.error("Some error has ocurred.");

        }

    }



}
