package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.UserInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserInfo, UUID>, JpaSpecificationExecutor<UserInfo> {
  Optional<UserInfo> findUserByEmailIgnoreCase(String login);

  List<UserInfo> findAllByTenant(Tenant tenant);
}
