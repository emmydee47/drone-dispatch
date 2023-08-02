package com.technology.dronedispatch.service;

import com.technology.dronedispatch.dto.response.ResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

public interface SequencerService {

    @Transactional(isolation = REPEATABLE_READ)
    ResponseDto<Optional<String>> generateSerialNumber();
}
