package com.decathlon.alert.mechanism.system.model;

import com.decathlon.alert.mechanism.system.controller.request.CreateTeamRequest;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "developer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Developer implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

    public static Developer build(CreateTeamRequest.DeveloperRequest developer) {
        return Developer.builder()
            .name(developer.getName())
            .phoneNumber(developer.getPhoneNumber())
            .build();
    }
}
