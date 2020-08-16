package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;

import java.util.ArrayList;

public interface MessageRepository extends JpaRepository<Message, String>, JpaSpecificationExecutor<Message> {
  ArrayList<Message> findAllByDialog(Dialog dialog);
}
