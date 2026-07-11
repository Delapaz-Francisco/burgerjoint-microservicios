package com.burgerjoint.menuservice.repository;

import com.burgerjoint.menuservice.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
