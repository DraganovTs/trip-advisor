package com.trip.advisor.user.service.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trip.advisor.user.service.model.enums.EPrivilege;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "USERPRIVILEGES")
@NoArgsConstructor
@Getter
@Setter
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}
