package com.project.hafaly_be.domain.service.impl;

import com.project.hafaly_be.api.dto.CreateFamilyDTO;
import com.project.hafaly_be.api.exception.customError.AfterHandlerException;
import com.project.hafaly_be.api.exception.customError.UserNotFoundException;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.enums.Role;
import com.project.hafaly_be.domain.model.Family;
import com.project.hafaly_be.domain.model.User;
import com.project.hafaly_be.domain.repository.FamilyRepository;
import com.project.hafaly_be.domain.service.FamilyService;
import com.project.hafaly_be.domain.service.FileUpload;
import com.project.hafaly_be.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {
    private final FamilyRepository familyRepository;
    private final UserService userService;
    private final FileUpload fileUpload;
    @Value("${default-family-image-url}")
    private String imgUrl;

    @Override
    public ResponseClient create(CreateFamilyDTO familyDTO) {
        User user = userService.getUserByEmail(familyDTO.getHostEmail());

        if (familyDTO.getImageFile() != null) {
            try {
                imgUrl = fileUpload.uploadFile(familyDTO.getImageFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (user.getFamily() != null) {
            throw new AfterHandlerException(HttpStatus.BAD_REQUEST,
                    String.format("User with email: %s already have a family", user.getFamily().getFamilyId()));
        }

        Family family = Family.builder()
                .host(user)
                .address(familyDTO.getAddress())
                .name(familyDTO.getName())
                .phone(user.getPhone())
                .code(familyDTO.getCode())
                .createdAt(new Date())
                .imageUrl(imgUrl)
                .build();

        user.setFamily(familyRepository.save(family));
        user.setRole(Role.PARENT);
        userService.save(user);
        return new ResponseClient(HttpStatus.CREATED, family);
    }

    @Override
    public ResponseClient getCode() {
        String code;
        do {
            code = randomNumber();
        } while (familyRepository.existsByCode(code));
        return new ResponseClient(HttpStatus.OK, code);
    }

    @Override
    public Family findByCode(String familyCode) {
        return familyRepository.findByCode(familyCode).orElseThrow(() -> new UserNotFoundException(familyCode));
    }

    @Override
    public void addMember(UUID userId, UUID familyId) {

        User user = userService.findById(userId);
        Family family = familyRepository.findById(familyId).orElseThrow(()-> new AfterHandlerException(HttpStatus.BAD_REQUEST, "family not found"));
        user.setFamily(family);
        userService.save(user);

    }

    @Override
    public Family findById(String familyId) {
        System.out.println(familyId);
        return familyRepository.findById(UUID.fromString(familyId)).orElseThrow(() -> new UserNotFoundException(familyId));
    }


    private String randomNumber() {
        Random rnd = new Random();
        return String.valueOf(rnd.nextInt(999999));
    }
}
