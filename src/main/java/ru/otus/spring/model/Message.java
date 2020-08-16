package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private UUID id;

  @Column(name = "body")
  private String body;

  @Column(name = "type")
  private String type;

  @Column(name = "link")
  private String link;

  @Column(name = "direction")
  private String direction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_info_id")
  private UserInfo userInfo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dialog_id")
  private Dialog dialog;

  @Column(name = "created_date")
  private Instant createdDate;
}
