package com.avatarduel.controller;

public class TestPlayerController {
    public static PlayerController[] makeControllers() {
        PlayerController[] playerControllers = new PlayerController[2];
        for (int i=0; i<1; ++i) {
            playerControllers[i] = new PlayerController(i);
        }
        return playerControllers;
    }
}