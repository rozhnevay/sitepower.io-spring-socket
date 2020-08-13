package ru.otus.spring.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;

public interface DialogRepository extends JpaRepository<Dialog, UUID>, JpaSpecificationExecutor<Dialog> {
}
