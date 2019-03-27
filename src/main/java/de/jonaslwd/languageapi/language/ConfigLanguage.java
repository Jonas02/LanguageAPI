package de.jonaslwd.languageapi.language;

import de.jonaslwd.languageapi.provider.LanguageProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Getter
public class ConfigLanguage {

    /* Means the name of this language */
    private final String name;

    /* Means the shorter form of this language */
    private final String shortForm;

    /**
     * Gets the configuration file of this language
     *
     * @return The configuration file of this language
     */
    public File getFolder () {
        if ( !LanguageProvider.getProvider().getDataFolder().exists() ) {
            LanguageProvider.getProvider().getDataFolder().mkdir();
        }
        final File file = new File( LanguageProvider.getProvider().getDataFolder(), name.toLowerCase() + ".json" );

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
