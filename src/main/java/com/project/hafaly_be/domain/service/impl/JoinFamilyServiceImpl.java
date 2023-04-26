package com.project.hafaly_be.domain.service.impl;

import com.project.hafaly_be.api.dto.JoinFamilyDTO;
import com.project.hafaly_be.api.exception.customError.AfterHandlerException;
import com.project.hafaly_be.api.exception.customError.UserNotFoundException;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.enums.Role;
import com.project.hafaly_be.domain.enums.StatusRequest;
import com.project.hafaly_be.domain.model.Family;
import com.project.hafaly_be.domain.model.JoinFamily;
import com.project.hafaly_be.domain.model.User;
import com.project.hafaly_be.domain.repository.JoinFamilyRepository;
import com.project.hafaly_be.domain.service.FamilyService;
import com.project.hafaly_be.domain.service.JoinFamilyService;
import com.project.hafaly_be.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinFamilyServiceImpl implements JoinFamilyService {
    private final UserService userService;
    private final FamilyService familyService;
    private final JoinFamilyRepository joinFamilyRepository;

    @Override
    public ResponseClient sendRequest(JoinFamilyDTO joinFamilyDTO) {
        User user = userService.findByEmail(joinFamilyDTO.getEmail());
        Family family = familyService.findByCode(joinFamilyDTO.getFamilyCode());

        if (joinFamilyRepository.existsJoinFamilyByUserAndFamily(user, family)) {
            throw new AfterHandlerException(HttpStatus.BAD_REQUEST, "You have already submitted your request, please wait for the host to agree");
        }

        JoinFamily joinFamily = JoinFamily.builder()
                .user(user)
                .family(family)
                .statusRequest(StatusRequest.WAITING)
                .createAt(new Date())
                .updateAt(new Date())
                .message(joinFamilyDTO.getMessage())
                .build();
        joinFamilyRepository.save(joinFamily);

        return new ResponseClient(HttpStatus.OK, joinFamilyRepository.save(joinFamily));
    }

    @Override
    public ResponseClient handleRequest(String idRequest, StatusRequest typeRequest) {
        JoinFamily joinFamily = joinFamilyRepository.findById(UUID.fromString(idRequest)).orElseThrow(() -> new AfterHandlerException(HttpStatus.BAD_REQUEST, "Can not find join family id"));
        joinFamily.setStatusRequest(typeRequest);
        joinFamily.setUpdateAt(new Date());
        if (typeRequest == StatusRequest.ACCEPT) {
            userService.updateUserRole(String.valueOf(joinFamily.getUser().getId()), Role.CHILD);
        }
        return new ResponseClient(HttpStatus.OK, joinFamilyRepository.save(joinFamily));
    }


    @Override
    public ResponseClient getAllRequest(String familyCode) {
        Family family = familyService.findByCode(familyCode);
        return new ResponseClient(HttpStatus.OK, joinFamilyRepository.findAllByFamily(family));
    }
}
