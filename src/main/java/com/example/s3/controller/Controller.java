package com.example.s3.controller;

import com.example.s3.representation.BucketObjectRepresentation;
import com.example.s3.service.impl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/buckets")
public class Controller {

    private final S3Service s3Service;

    @Autowired
    public Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @GetMapping(value = "/{bucketName}/objects")
    public ResponseEntity<List<String>> listObjects(@PathVariable String bucketName) {
        return new ResponseEntity<>(s3Service.listObjects(bucketName), HttpStatus.OK);
    }

    @PostMapping(value = "/{bucketName}/objects")
    public void createObject(@PathVariable String bucketName, @RequestBody BucketObjectRepresentation representation) throws IOException {
        s3Service.putObject(bucketName, representation);
    }






}
