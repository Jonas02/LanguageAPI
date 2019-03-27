package de.jonaslwd.languageapi.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigMessage {

    /* The path of this message  */
    private final String path;

    /* The text of this message */
    private String message;
}
