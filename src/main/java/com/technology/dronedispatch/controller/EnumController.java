package com.technology.dronedispatch.controller;

import com.technology.dronedispatch.service.EnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/enums")
@RequiredArgsConstructor
@Validated
@CrossOrigin("*")
public class EnumController {

    private final EnumService enumService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<String>>> getEnums() throws IOException {
        return enumService.getAllEnums();
    }
}
