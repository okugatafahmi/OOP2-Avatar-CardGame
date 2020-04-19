package com.avatarduel;

import javafx.scene.control.Alert.AlertType;

public interface AlertShower {
    public void showAlert(AlertType alertType, String msg);
}