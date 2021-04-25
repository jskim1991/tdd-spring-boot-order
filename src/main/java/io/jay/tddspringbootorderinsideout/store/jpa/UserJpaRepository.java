package io.jay.tddspringbootorderinsideout.store.jpa;

import io.jay.tddspringbootorderinsideout.store.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
}
