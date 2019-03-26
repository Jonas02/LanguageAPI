package de.jonaslwd.languageapi.config;

import com.google.gson.GsonBuilder;
import de.jonaslwd.languageapi.language.ConfigLanguage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class MasterLanguageConfig {

    @Getter
    private final ConfigLanguage configLanguage;

    private List< ConfigMessage > messages = new ArrayList<>(  );

    public void addMessage( final ConfigMessage message ) {
        messages.add( message );
    }

    public ConfigMessage getMessage( final String path ) {
        for ( final ConfigMessage everyMessage : messages ) {
            if ( everyMessage.getPath( ).equalsIgnoreCase( path ) ) {
                return everyMessage;
            }
        }
        return new ConfigMessage( "api.error", "Error while loading message: " + path );
    }

    public void save () {
        try {
            final FileWriter writer = new FileWriter( configLanguage.getFolder() );

            writer.write( new GsonBuilder().setPrettyPrinting().create().toJson( this ) );
            writer.flush();
            writer.close();
        } catch ( final IOException exception ) {
            exception.printStackTrace();
        }
    }
}
