package ru.otus.spring.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.model.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, UUID>, JpaSpecificationExecutor<UserInfo> {
  Optional<UserInfo> findUserByEmailIgnoreCase(String login);
}
