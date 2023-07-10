package com.muhammadusman92.userservice.services.impl;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.muhammadusman92.userservice.exception.ResourceNotFoundException;
import com.muhammadusman92.userservice.model.User;
import com.muhammadusman92.userservice.payloads.UserDto;
import com.muhammadusman92.userservice.repo.UserRepo;
import com.muhammadusman92.userservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        if(userRepo.existsCNIC(user.getCNIC())>0){
            User findUser = userRepo.findById(user.getCNIC()).orElseThrow(
                    ()->new ResourceNotFoundException("User","User CNIC", user.getCNIC()));
            findUser.setFingerData(user.getFingerData());
            User savedUser = userRepo.save(findUser);
            return modelMapper.map(savedUser,UserDto.class);
        }
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userCNIC) {
        User findUser = userRepo.findById(userCNIC)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userCNIC));
        User user = modelMapper.map(userDto, User.class);
        findUser.setFingerData(user.getFingerData());
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto getUserByCNIC(String userCNIC) {
        User findUser = userRepo.findById(userCNIC)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userCNIC));
        UserDto userDto = modelMapper.map(findUser, UserDto.class);
        return userDto;
    }
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
    @Override
    public String getUserCNIC(String fingerPrintData) {
        String cnic = null;
        List<User> users = userRepo.findAll();
        for(User user:users){
            if(matchFingerprints(loadTemplate(user.getFingerData()),loadTemplate(fingerPrintData)) > 20){
                cnic = user.getCNIC();
            }
        }
        if(cnic==null){
            throw new ResourceNotFoundException("User","Finger Print","");
        }
        return cnic;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUserByCNIC(String userCNIC) {
        User findUser = userRepo.findById(userCNIC)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userCNIC));
        userRepo.delete(findUser);
    }


}
