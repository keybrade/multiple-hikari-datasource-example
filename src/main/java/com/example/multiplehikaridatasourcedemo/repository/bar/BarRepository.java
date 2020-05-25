package com.example.multiplehikaridatasourcedemo.repository.bar;

import com.example.multiplehikaridatasourcedemo.entity.bar.BarEntity;
import com.example.multiplehikaridatasourcedemo.entity.foo.FooEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BarRepository extends JpaRepository<BarEntity, Long> {
}
