package com.dragonappear.inha.domain.user;

import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.*;

@ToString(exclude = {"users","id","roleName"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column
    private String roleName;

    @Column
    private String roleDesc;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles")
    private Set<User> users = new HashSet<>();
}
