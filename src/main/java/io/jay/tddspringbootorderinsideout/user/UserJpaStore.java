package io.jay.tddspringbootorderinsideout.user;

import io.jay.tddspringbootorderinsideout.store.UserStore;
import io.jay.tddspringbootorderinsideout.user.store.exception.NoSuchUserException;
import io.jay.tddspringbootorderinsideout.user.store.repository.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserJpaStore implements UserStore {

    private JpaRepository<UserEntity, String> jpaRepository;

    public UserJpaStore(JpaRepository jpaRepository) {
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
