package com.fotopoint.fotopointapp.repository;

import com.fotopoint.fotopointapp.models.Convidado;
import com.fotopoint.fotopointapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
    Convidado findByRg(String rg);
    Iterable<Convidado> findByEvento(Evento evento);

}
