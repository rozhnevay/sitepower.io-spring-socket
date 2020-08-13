package ru.otus.spring.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Widget;

public interface WidgetRepository extends JpaRepository<Widget, UUID>, JpaSpecificationExecutor<Widget> {
}
