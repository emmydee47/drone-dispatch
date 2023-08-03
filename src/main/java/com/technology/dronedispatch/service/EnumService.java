package com.technology.dronedispatch.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EnumService {

    ResponseEntity<Map<String, List<String>>> getAllEnums() throws IOException;
}
