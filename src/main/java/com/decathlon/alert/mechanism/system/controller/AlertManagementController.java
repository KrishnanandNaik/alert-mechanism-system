package com.decathlon.alert.mechanism.system.controller;

import com.decathlon.alert.mechanism.system.constants.CodeConstants;
import com.decathlon.alert.mechanism.system.controller.request.CreateTeamRequest;
import com.decathlon.alert.mechanism.system.controller.response.CreateTeamResponse;
import com.decathlon.alert.mechanism.system.controller.response.ResponseWrapper;
import com.decathlon.alert.mechanism.system.service.AlertManagementService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class AlertManagementController {

    private final AlertManagementService alertManagementService;

    @PostMapping("/team")
    public ResponseWrapper<CreateTeamResponse> createTeam(
        @RequestBody @Valid CreateTeamRequest createTeamRequest
    ) {
        log.debug("Received team creation request: {}", createTeamRequest);
        Long teamId = alertManagementService.createTeam(createTeamRequest);
        return new ResponseWrapper(CodeConstants.SUCCESS, new CreateTeamResponse(teamId));
    }

    @PostMapping("/{teamId}/alert")
    public ResponseWrapper alert(@PathVariable Long teamId) {
        log.debug("Send alert request: {}", teamId);
        alertManagementService.sendAlert(teamId);
        return new ResponseWrapper(CodeConstants.SUCCESS, "Alert sent!!");
    }
}
