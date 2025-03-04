package com.fotopoint.fotopointapp.repository;

import com.fotopoint.fotopointapp.models.Convidado;
import com.fotopoint.fotopointapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, Long> {
    Evento findById(long id);
}
