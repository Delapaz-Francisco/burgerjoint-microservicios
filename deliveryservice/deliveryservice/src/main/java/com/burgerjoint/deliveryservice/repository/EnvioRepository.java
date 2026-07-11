package com.burgerjoint.deliveryservice.repository;

import com.burgerjoint.deliveryservice.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

}
