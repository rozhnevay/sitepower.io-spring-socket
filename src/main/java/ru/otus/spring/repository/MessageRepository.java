package ru.otus.spring.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;
import ru.otus.spring.model.UserInfo;

public interface MessageRepository extends JpaRepository<Message, String>, JpaSpecificationExecutor<Message> {
  ArrayList<Message> findAllByDialog(Dialog dialog);
}
