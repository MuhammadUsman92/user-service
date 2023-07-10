package com.muhammadusman92.userservice.controller;


import SecuGen.FDxSDKPro.jni.JSGFPLib;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.muhammadusman92.userservice.payloads.Response;
import com.muhammadusman92.userservice.payloads.UserDto;
import com.muhammadusman92.userservice.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

import java.util.Base64;


@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    private String registerTemplate;
    private String verifyTemplate;
    private JSGFPLib fplib = null;

    private static double matchFingerprints(FingerprintTemplate template1, FingerprintTemplate template2) {
        double matchingScore =new FingerprintMatcher()
                .index(template1)
                .match(template2);
        double minScore = 0.0;
        double maxScore = 100.0;
        double percentage = (matchingScore - minScore) / (maxScore - minScore) * 100.0;

        return percentage;
    }
    private static FingerprintTemplate loadTemplate(String templateData) {
        byte[] decodedBytes = Base64.getDecoder().decode(templateData);
        FingerprintImage image = new FingerprintImage(300, 400, decodedBytes); // You need to know the dimensions of the image
        return new FingerprintTemplate(image);
    }
    @PostMapping("/user-cnic/")
    public ResponseEntity<Response> getUserCNIC(@RequestBody UserDto fingerData){
        String cnic = userService.getUserCNIC(fingerData.getFingerData());
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message(cnic)
                .status(CREATED)
                .statusCode(CREATED.value())
                .build(), CREATED);
    }

    @PostMapping("/")
    public ResponseEntity<Response> createUser(@RequestBody UserDto userDto){
        System.out.println(userDto.getCnic());
        UserDto savedUser=userService.createUser(userDto);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("User is successfully created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build(), CREATED);
    }

}
