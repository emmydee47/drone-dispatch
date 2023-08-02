package com.technology.dronedispatch.service.impl;

import com.technology.dronedispatch.config.ConfigConstant;
import com.technology.dronedispatch.dto.response.ResponseDto;
import com.technology.dronedispatch.model.entity.Sequencer;
import com.technology.dronedispatch.model.enums.ResponseCode;
import com.technology.dronedispatch.repository.SequencerRepository;
import com.technology.dronedispatch.service.SequencerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Service
@RequiredArgsConstructor
@Slf4j
public class SequencerServiceImpl implements SequencerService {
    private final SequencerRepository sequencerRepository;
    private final ConfigConstant configConstant;

    @Transactional(isolation = REPEATABLE_READ)
    public ResponseDto<Optional<String>> generateSerialNumber() {
        String serialNumber;
        Sequencer sequencer;
        //if no sequence has not been initialized, start a sequence
        if (sequencerRepository.count() == 0) {
            sequencer = Sequencer.builder().build();
            sequencer.incrementSequence();

            //if a sequence exists get the last generated sequence, increment and persist
        } else {
            sequencer = sequencerRepository.findTopByOrderByIdDesc();
            sequencer.incrementSequence();
        }

        serialNumber = sequencer.getSequence();

        //validate the generated serial no character length
        if (serialNumber.length() > configConstant.getMaxDroneSerialNoCharacterLength()) {
            return ResponseDto.<Optional<String>>builder()
                    .data(Optional.empty())
                    .statusMessage("Serial Number length maxed out")
                    .statusCode(ResponseCode.maximumParameterLimitCode())
                    .build();
        }

        sequencerRepository.save(sequencer);

        log.info("Generated Drone Serial Number: " + serialNumber);
        return ResponseDto.<Optional<String>>builder()
                .statusMessage(ResponseCode.successMessage())
                .data(Optional.of(serialNumber))
                .statusCode(ResponseCode.successCode())
                .build();
    }

}
