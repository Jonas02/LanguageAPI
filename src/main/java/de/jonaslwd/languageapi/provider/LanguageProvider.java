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

public final class LanguageProvider implements LanguageAPI {

    /* the instance of this class with a static getter */
    @Getter
    private static LanguageProvider provider;

    /* This list contains each MasterLanguageConfiguration which were loaded */
    private final List< MasterLanguageConfig > configs = new ArrayList<>( );

    /* Gets the main directory which contains each language file */
    @Getter
    private File dataFolder;

    /**
     * Constructs this class and initializes the instance of folder object
     *
     * @param path This means the path of the main directory which will contain all language files
     */
    public LanguageProvider( final String path ) {
        provider = this;
        this.dataFolder = new File( path + "languages" );
    }

    /* The instance of the helper library Gson */
    @Getter
    private final Gson gson = new Gson( );

    @Override
    public void loadMessages( ) {
        if ( !dataFolder.exists( ) ) {
            dataFolder.mkdir( );
        }

        if ( dataFolder.listFiles( ) == null || dataFolder.listFiles( ).length == 0 ) {
            return;
        }

        for ( final File eachFile : dataFolder.listFiles( ) ) {
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
        if ( !configs.contains( language ) ) {
            final MasterLanguageConfig config = new MasterLanguageConfig( language );

            config.save( );
            configs.add( config );
        }
        for ( final MasterLanguageConfig configs : configs ) {
            if ( configs.getConfigLanguage( ).getShortForm( ).equalsIgnoreCase( language.getShortForm( ) )
                    && language.getName( ).equalsIgnoreCase( configs.getConfigLanguage( ).getName( ) ) ) {
                configs.addMessage( message );
                if ( save )
                    configs.save( );
            }
        }
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
