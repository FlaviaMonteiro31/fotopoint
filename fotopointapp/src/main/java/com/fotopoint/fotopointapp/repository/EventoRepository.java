package com.fotopoint.fotopointapp.repository;

import com.fotopoint.fotopointapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EventoRepository extends CrudRepository<Evento, Long> {
    Evento findById(long id);
}
