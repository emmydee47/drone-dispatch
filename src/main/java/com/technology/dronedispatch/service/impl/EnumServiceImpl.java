package com.technology.dronedispatch.service.impl;

import com.technology.dronedispatch.service.EnumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class EnumServiceImpl implements EnumService {

    @Override
    public ResponseEntity<Map<String, List<String>>> getAllEnums() throws IOException {
        Map<String, List<String>> enumMap = new TreeMap<>();

        String packageName = "com.technology.dronedispatch.model.enums";
        String packagePath = packageName.replace('.', '/');

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:" + packagePath + "/*.class");

        for (Resource resource : resources) {
            if (Objects.isNull(resource.getFilename())) {
                continue;
            }

            String className = resource.getFilename().replace(".class", "");

            try {
                Class<?> enumClass = Class.forName(packageName + "." + className);
                if (enumClass.isEnum()) {
                    List<String> enumValues = getEnumValues(enumClass);
                    enumMap.put(className, enumValues);
                }
            } catch (ClassNotFoundException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }

        return ResponseEntity.ok(enumMap);
    }

    private List<String> getEnumValues(Class<?> enumClass) {
        List<String> enumValues = new ArrayList<>();
        Object[] constants = enumClass.getEnumConstants();

        for (Object constant : constants) {
            if (constant instanceof Enum<?> enumConstant) {
                enumValues.add(enumConstant.name());
            }
        }

        Collections.sort(enumValues);
        return enumValues;
    }


}
