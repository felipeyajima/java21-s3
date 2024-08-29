package com.example.s3.representation;

import lombok.Data;

@Data
public class BucketObjectRepresentation {
    private String objectName;
    private String text;

    public String getObjectName(){
        return this.objectName;
    }

    public String getText(){
        return this.text;
    }
}