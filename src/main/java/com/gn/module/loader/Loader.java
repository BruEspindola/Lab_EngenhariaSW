package com.gn.module.loader;/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Loader extends Preloader {

    private JFXProgressBar progressBar;
    private Parent view;
    private Stage stage;

    @Override
    public void init(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/module/loader/loader.fxml"));
            view = FXMLLoader.load(getClass().getResource("/module/loader/loader.fxml"));
//            view = FXMLLoader.load(getClass().getResource("/com/gn/module/loader/loader.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primary){
        stage = primary;
        primary.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(view);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/theme/css/fonts.css").toExternalForm());
        progressBar = (JFXProgressBar) scene.lookup("#progressBar");
        primary.getIcons().add(new Image("/icon.png"));
        primary.setScene(scene);
//        primary.setAlwaysOnTop(true);
        primary.show();

    }

    @Override
    public synchronized void handleApplicationNotification(Preloader.PreloaderNotification info) {
        // Handle application notification in this point (see MyApplication#Init).

        if (info instanceof Preloader.ProgressNotification) {
            double x = ((Preloader.ProgressNotification) info).getProgress();

            double percent = x / 100f;
//            progressBar.progressProperty().set(percent > 1 ? 1 : percent);
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        // Handle state change notifications.

        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD:
                break;
            case BEFORE_INIT:
                break;
            case BEFORE_START:
                stage.close();
        }
    }
}
