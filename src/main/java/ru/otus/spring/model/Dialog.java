package ru.otus.spring.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity
@Table(name = "dialog")
@Getter
@Setter
public class Dialog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private UUID id;

  @Column(name = "class")
  private String className;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "region")
  private String region;

  /* TODO: здесь и далее убрать EAGER */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "widget_id")
  private Widget widget;

//  private
}
