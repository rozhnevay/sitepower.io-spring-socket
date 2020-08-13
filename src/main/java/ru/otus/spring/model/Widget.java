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
@Table(name = "widget")
@Getter
@Setter
public class Widget {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  @Column(name = "origin")
  private String origin;

  @Column(name = "position")
  private int position;

  @Column(name = "color")
  private String color;

  @Column(name = "gradient")
  private int gradient;

  @Column(name = "label")
  private String label;

  @Column(name = "message_placeholder")
  private String messagePlaceholder;

  @Column(name = "test")
  private String test;

  @Column(name = "updated_date")
  private Instant updatedDate;

  @Column(name = "created_date")
  private Instant createdDate;
}
