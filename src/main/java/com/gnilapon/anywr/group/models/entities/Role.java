package com.gnilapon.anywr.group.models.entities;

import com.gnilapon.anywr.group.models.enums.ERole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Enumerated(EnumType.STRING)
  private ERole name;
}