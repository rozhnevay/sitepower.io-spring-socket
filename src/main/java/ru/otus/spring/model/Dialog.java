package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "dialog")
@Getter
@Setter
@NamedEntityGraph(name = "dialogWidget",
    attributeNodes = @NamedAttributeNode("widget"))
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

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "created_date")
  private Instant createdDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "widget_id")
  private Widget widget;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "last_msg_id")
  private Message lastMessage;
//  private
}
