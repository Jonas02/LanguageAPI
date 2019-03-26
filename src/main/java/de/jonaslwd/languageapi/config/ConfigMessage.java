package de.jonaslwd.languageapi.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigMessage {

    private final String path;

    private String message;
}
