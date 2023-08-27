package de.codechallenge.maikproba.rockpaperscissors.config;

import de.codechallenge.maikproba.rockpaperscissors.services.GameStateManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GameStateManagerConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GameStateManager gameStateManager() {
        return new GameStateManager();
    }
}
