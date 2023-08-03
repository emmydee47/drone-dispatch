package com.technology.dronedispatch;

import com.technology.dronedispatch.config.ConfigConstant;
import com.technology.dronedispatch.model.entity.Sequencer;
import com.technology.dronedispatch.model.enums.ResponseCode;
import com.technology.dronedispatch.repository.SequencerRepository;
import com.technology.dronedispatch.service.impl.SequencerServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class SequencerServiceTests {

    @Mock
    private SequencerRepository sequencerRepository;

    @Mock
    private ConfigConstant configConstant;

    @InjectMocks
    private SequencerServiceImpl sequencerService;


    @Test
    public void testGenerateSerialNumber_SequenceStartsFromZero() {
        when(sequencerRepository.count()).thenReturn(0L);
        when(configConstant.getMaxDroneSerialNoCharacterLength()).thenReturn(100);

        // Act
        var response = sequencerService.generateSerialNumber();
        log.info(response.getStatusMessage());

        // Assert
        assertEquals(ResponseCode.successCode(), response.getStatusCode());
        assertEquals("001", response.getData().get());
        verify(sequencerRepository, times(1)).count();
        verify(sequencerRepository, times(1)).save(any());
        verify(sequencerRepository, never()).findTopByOrderByIdDesc();
    }

    @Test
    public void testGenerateSerialNumber_SequenceIncrement() {
        Sequencer existingSequencer = Sequencer.builder().sequence(100).build();
        when(sequencerRepository.count()).thenReturn(1L);
        when(sequencerRepository.findTopByOrderByIdDesc()).thenReturn(existingSequencer);
        when(configConstant.getMaxDroneSerialNoCharacterLength()).thenReturn(100);

        // Act
        var response = sequencerService.generateSerialNumber();
        log.info(response.getStatusMessage());

        // Assert
        assertEquals(ResponseCode.successCode(), response.getStatusCode());
        assertEquals("101", response.getData().get());
        verify(sequencerRepository, times(1)).count();
        verify(sequencerRepository, times(1)).findTopByOrderByIdDesc();
        verify(sequencerRepository, times(1)).save(existingSequencer);
    }

    @Test
    public void testGenerateSerialNumber_SequenceMaxedOut() {
        Sequencer existingSequence = Sequencer.builder().sequence(999).build();
        when(sequencerRepository.count()).thenReturn(1L);
        when(sequencerRepository.findTopByOrderByIdDesc()).thenReturn(existingSequence);
        when(configConstant.getMaxDroneSerialNoCharacterLength()).thenReturn(3);

        // Act
        var response = sequencerService.generateSerialNumber();
        log.info(response.getStatusMessage());

        // Assert
        assertEquals(ResponseCode.maximumParameterLimitCode(), response.getStatusCode());
        assertEquals("Serial Number length maxed out", response.getStatusMessage());
        assertEquals(Optional.empty(), response.getData());
        verify(sequencerRepository, times(1)).count();
        verify(sequencerRepository, times(1)).findTopByOrderByIdDesc();
        verify(sequencerRepository, never()).save(any());
    }

}
