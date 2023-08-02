package com.technology.dronedispatch.core.error.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMark {
    BLANK("BLANK"),
    INVALID("INVALID"),
    NOT_FOUND("NOT_FOUND");

    private final String errorMark;
}
