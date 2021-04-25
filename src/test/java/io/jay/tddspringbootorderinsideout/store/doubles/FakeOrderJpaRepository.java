package io.jay.tddspringbootorderinsideout.store.doubles;

import io.jay.tddspringbootorderinsideout.store.entity.OrderEntity;
import io.jay.tddspringbootorderinsideout.store.jpa.OrderJpaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FakeOrderJpaRepository implements OrderJpaRepository {

    private HashMap<String, OrderEntity> orderMap;

    public FakeOrderJpaRepository() {
        this.orderMap = new HashMap<>();
    }

    @Override
    public List<OrderEntity> findAll() {
        return new ArrayList<>(orderMap.values());
    }

    @Override
    public List<OrderEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<OrderEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<OrderEntity> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {
        orderMap.remove(s);
    }

    @Override
    public void delete(OrderEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends OrderEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public OrderEntity save(OrderEntity entity) {
        orderMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends OrderEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<OrderEntity> findById(String s) {
        return orderMap.containsKey(s) ? Optional.of(orderMap.get(s)) : Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends OrderEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends OrderEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<OrderEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public OrderEntity getOne(String s) {
        return null;
    }

    @Override
    public OrderEntity getById(String s) {
        return null;
    }

    @Override
    public <S extends OrderEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends OrderEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends OrderEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends OrderEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends OrderEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends OrderEntity> boolean exists(Example<S> example) {
        return false;
    }
}