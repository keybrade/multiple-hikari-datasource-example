package com.example.multiplehikaridatasourcedemo.repository.foo;

import com.example.multiplehikaridatasourcedemo.entity.foo.FooEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FooRepository extends JpaRepository<FooEntity, Long> {
}
