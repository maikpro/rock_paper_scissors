package de.codechallenge.maikproba.rockpaperscissors.controllers;

import de.codechallenge.maikproba.rockpaperscissors.models.GameState;
import de.codechallenge.maikproba.rockpaperscissors.models.HandSign;
import de.codechallenge.maikproba.rockpaperscissors.services.GameStateManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {
    private GameStateManager gameStateManager;

    public GameController(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @GetMapping("/start")
    public String start(Model model) {
        this.gameStateManager.startGame(model);
        return "round";
    }

    @PostMapping("/uuid/{gameUUID}/round/{roundIndex}/send")
    public String simulation(@PathVariable("gameUUID") String gameUUID, @PathVariable("roundIndex") int roundIndex, @RequestParam HandSign handSign, Model model) {
        boolean isNextRound = this.gameStateManager.isNextRound(gameUUID, roundIndex, handSign, model);
        if (isNextRound) {
            return "round";
        }
        return "result";
    }

    @GetMapping("/uuid/{gameUUID}/result")
    public ResponseEntity<GameState> getJSONResult(@PathVariable("gameUUID") String gameUUID) {
        GameState gameState = this.gameStateManager.getGameState(gameUUID);

        if (gameState == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(gameState);
    }
}
