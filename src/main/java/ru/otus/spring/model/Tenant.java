package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tenant")
@Getter
@Setter
public class Tenant {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private UUID id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "main_user_info_id")
  private UserInfo mainUserInfo;
}
