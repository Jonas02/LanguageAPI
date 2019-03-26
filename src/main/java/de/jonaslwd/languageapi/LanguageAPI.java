package de.jonaslwd.languageapi;

import de.jonaslwd.languageapi.config.ConfigMessage;
import de.jonaslwd.languageapi.language.ConfigLanguage;

public interface LanguageAPI {

    void loadMessages ();

    void addMessage( final ConfigLanguage language, final ConfigMessage message, final boolean save );

    ConfigMessage getMessage( final ConfigLanguage language, final String path );
}
