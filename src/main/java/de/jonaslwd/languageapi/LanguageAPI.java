package de.jonaslwd.languageapi;

import de.jonaslwd.languageapi.config.ConfigMessage;
import de.jonaslwd.languageapi.language.ConfigLanguage;

public interface LanguageAPI {

    /**
     * Loads all languages of the language directory
     */
    void loadMessages( );

    /**
     * Adds a new message into the specified language file
     *
     * @param language This means the language of the message which will be add
     * @param message  This means the message which will be add
     * @param save     If it is true the language file will be saved directly
     */
    void addMessage( final ConfigLanguage language, final ConfigMessage message, final boolean save );

    /**
     * Gets the ConfigMessage object where the path is equals to the given path
     *
     * @param language This means the language of the message which will be returned
     * @param path This means the path of the language which will be returned
     * @return The ConfigMessage object which contains the given path and the responding message
     */
    ConfigMessage getMessage( final ConfigLanguage language, final String path );
}
