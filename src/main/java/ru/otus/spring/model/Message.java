package ru.otus.spring.model;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

  /* TODO: здесь и далее убрать EAGER */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_info_id")
  private UserInfo userInfo;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "dialog_id")
  private Dialog dialog;

  @Column(name = "created_date")
  private Instant createdDate;
}
