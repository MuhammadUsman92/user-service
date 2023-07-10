package com.muhammadusman92.userservice.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@ToString
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User {
    @Id
    @Column(name = "CNIC",unique = true,nullable = false,length = 50)
    private String CNIC;
    @Column(length = 120000)
    private String fingerData;
    private boolean deleted = Boolean.FALSE;
}
