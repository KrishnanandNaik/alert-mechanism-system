package com.decathlon.alert.mechanism.system.service;

import com.decathlon.alert.mechanism.system.constants.CodeConstants;
import com.decathlon.alert.mechanism.system.controller.request.CreateTeamRequest;
import com.decathlon.alert.mechanism.system.exception.AlertManagementException;
import com.decathlon.alert.mechanism.system.feign.SmsServiceFeign;
import com.decathlon.alert.mechanism.system.feign.request.SendSmsRequest;
import com.decathlon.alert.mechanism.system.model.Developer;
import com.decathlon.alert.mechanism.system.model.Team;
import com.decathlon.alert.mechanism.system.repository.DeveloperRepository;
import com.decathlon.alert.mechanism.system.repository.TeamRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertManagementService {
    private final TeamRepository teamRepository;
    private final DeveloperRepository developerRepository;
    private final SmsServiceFeign smsServiceFeign;

    @Transactional
    public Long createTeam(CreateTeamRequest createTeamRequest) {
        Team team = saveTeam(createTeamRequest);
        saveDevelopers(createTeamRequest, team);
        return team.getId();
    }

    public void sendAlert(Long teamId) {
        Developer developer = developerRepository.findRandomDevByTeamId(teamId);
        if (developer == null) {
            throw new AlertManagementException(CodeConstants.DEV_NOT_FOUND, null);
        }
        try {
            ResponseEntity<String> response = smsServiceFeign.sendSms(new SendSmsRequest(developer.getPhoneNumber()));
            log.info("Sms sent to {}, external response: {}", developer.getPhoneNumber(), response.getBody());
        } catch (Exception ex) {
            log.error("error occurred while calling sms service", ex);
        }
    }

    private Team saveTeam(CreateTeamRequest createTeamRequest) {
        try {
            return teamRepository.save(Team.build(createTeamRequest.getTeamName()));
        } catch (Exception ex) {
            log.error("exception occurred on team save", ex);
            throw new AlertManagementException(CodeConstants.TEAM_CREATION_ERROR, ex);
        }
    }

    private void saveDevelopers(CreateTeamRequest createTeamRequest, Team team) {
        try {
            createTeamRequest.getDevelopers().stream().map(Developer::build).forEach(it -> {
                it.setTeam(team);
                developerRepository.save(it);
            });
        } catch (Exception ex) {
            log.error("exception occurred on developer details save", ex);
            throw new AlertManagementException(CodeConstants.DEV_CREATION_ERROR, ex);
        }
    }

}
