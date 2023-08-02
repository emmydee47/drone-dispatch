package com.technology.dronedispatch.repository;


import com.technology.dronedispatch.model.entity.Sequencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequencerRepository extends JpaRepository<Sequencer, Long> {
    Sequencer findTopByOrderByIdDesc();

}
