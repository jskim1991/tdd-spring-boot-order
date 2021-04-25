package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.store.entity.UserEntity;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchUserException;
import io.jay.tddspringbootorderinsideout.store.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserJpaStore implements UserStore {

    private UserJpaRepository jpaRepository;

    public UserJpaStore(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return jpaRepository.findAll()
                .stream()
                .map(UserEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(String id) {
        UserEntity userEntity = jpaRepository.findById(id).orElseThrow(
                () -> new NoSuchUserException("No such user for id " + id));
        return userEntity.toDomain();
    }

    @Override
    public User addUser(User user) {
        return jpaRepository.save(new UserEntity(user)).toDomain();
    }

    @Override
    public User updateUser(User user) {
        return jpaRepository.save(new UserEntity(user)).toDomain();
    }

    @Override
    public void deleteUser(String id) {
        jpaRepository.deleteById(id);
    }
}
