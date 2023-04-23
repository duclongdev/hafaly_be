package com.project.hafaly_be.domain.service.impl;

import com.project.hafaly_be.api.dto.CreateFamilyDTO;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.enums.Role;
import com.project.hafaly_be.domain.model.Family;
import com.project.hafaly_be.domain.model.User;
import com.project.hafaly_be.domain.repository.FamilyRepository;
import com.project.hafaly_be.domain.service.FamilyService;
import com.project.hafaly_be.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {
    private final FamilyRepository familyRepository;
    private final UserService userService;
    @Override
    public ResponseClient create(CreateFamilyDTO familyDTO ) {
        User user = userService.getUserByEmail(familyDTO.getHostEmail());
        Family family = Family.builder()
                .host(user)
                .address(familyDTO.getAddress())
                .name(familyDTO.getName())
                .phone(user.getPhone())
                .code(familyDTO.getCode())
                .createdAt(new Date())
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
        }while(familyRepository.existsByCode(code));
        return new ResponseClient(HttpStatus.OK, code);
    }
    private String randomNumber(){
        Random rnd = new Random();
        return String.valueOf(rnd.nextInt(999999));
    }
}
