package de.jonaslwd.languageapi.language;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

import static de.jonaslwd.languageapi.provider.LanguageProvider.DATA_FOLDER;

@RequiredArgsConstructor
@Getter
public class ConfigLanguage {

    private final String name;

    private final String shortForm;

    public File getFolder () {
        if ( !DATA_FOLDER.exists() ) {
            DATA_FOLDER.mkdir();
        }
        final File file = new File( DATA_FOLDER, name.toLowerCase() + ".json" );

        if ( file.exists() ) {
            try {
                file.createNewFile();
            } catch ( final IOException exception ) {
                exception.printStackTrace( );
            }
        }
        return file;
    }
}
