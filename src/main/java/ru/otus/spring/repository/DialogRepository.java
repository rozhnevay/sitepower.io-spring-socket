package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Widget;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface DialogRepository extends JpaRepository<Dialog, UUID>, JpaSpecificationExecutor<Dialog> {
  @EntityGraph(value = "dialogWidget", type = EntityGraph.EntityGraphType.LOAD)
  Optional<Dialog> findById(UUID dialogId);

  ArrayList<Dialog> findAllByWidget(Widget widget);
}
