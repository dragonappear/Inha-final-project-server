package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Role extends JpaBaseTimeEntity implements Serializable {

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
