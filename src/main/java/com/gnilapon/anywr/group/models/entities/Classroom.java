package com.gnilapon.anywr.group.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "classroom")
@Getter
@Setter
public class Classroom {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
