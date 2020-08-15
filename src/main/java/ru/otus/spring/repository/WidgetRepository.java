package ru.otus.spring.repository;

import java.util.ArrayList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.Widget;

public interface WidgetRepository extends JpaRepository<Widget, UUID>, JpaSpecificationExecutor<Widget> {
  ArrayList<Widget> findAllByTenant(Tenant tenant);
}
