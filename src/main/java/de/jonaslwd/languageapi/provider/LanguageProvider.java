package de.jonaslwd.languageapi.provider;

import com.google.gson.Gson;
import de.jonaslwd.languageapi.LanguageAPI;
import de.jonaslwd.languageapi.config.ConfigMessage;
import de.jonaslwd.languageapi.config.MasterLanguageConfig;
import de.jonaslwd.languageapi.language.ConfigLanguage;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class LanguageProvider implements LanguageAPI {

    public static File DATA_FOLDER = new File( "languages" );

    private final List< MasterLanguageConfig > configs = new ArrayList<>(  );

    private final ExecutorService service = Executors.newCachedThreadPool( );

    @Getter
    private final Gson gson = new Gson( );

    @Override
    public void loadMessages( ) {
        if ( !DATA_FOLDER.exists( ) ) {
            DATA_FOLDER.mkdir( );
        }

        if ( DATA_FOLDER.listFiles( ) == null || DATA_FOLDER.listFiles( ).length == 0 ) {
            return;
        }

        for ( final File eachFile : DATA_FOLDER.listFiles( ) ) {
            try {
                final MasterLanguageConfig config = gson.fromJson( new FileReader( eachFile ), MasterLanguageConfig.class );

                configs.add( config );
                System.out.println( "Language loaded: " + config.getConfigLanguage( ).getName( ) + " ["
                        + config.getConfigLanguage( ).getShortForm( ) + "]" );
            } catch ( final FileNotFoundException exception ) {
                exception.printStackTrace( );
            }
        }
    }

    @Override
    public void addMessage( final ConfigLanguage language, final ConfigMessage message, final boolean save ) {
        service.execute( ( ) -> {
            for ( final MasterLanguageConfig configs : configs ) {
                if ( configs.getConfigLanguage( ) == language ) {
                    configs.addMessage( message );

                    if ( save )
                        configs.save( );
                }
            }
        } );
    }

    @Override
    public ConfigMessage getMessage( final ConfigLanguage language, final String path ) {
        for ( final MasterLanguageConfig configs : configs ) {
            if ( configs.getConfigLanguage( ).getName( ).equalsIgnoreCase( language.getName( ) )
                    && configs.getConfigLanguage( ).getShortForm( ).equalsIgnoreCase( language.getShortForm( ) ) ) {
                return configs.getMessage( path );
            }
        }
        return new ConfigMessage( "api.error", "Error while loading message: " + path );
    }
}
