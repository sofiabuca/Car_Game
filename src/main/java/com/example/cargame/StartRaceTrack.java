package com.example.cargame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class StartRaceTrack extends Application
{
    CarPane obTrack;
    CarPane obTrack2;
    Timeline obTimeRace;
    Timeline  obTimeRace2;
    ControlBox obControl;
    Stage obMainStage;
    MenuItem miStart ;
    MenuItem miPause;
    MenuItem miRestart;
    int iSpeedTrack;
    Timeline timeLine;



    @Override
    public void start(final Stage obStage) throws Exception
    {

        //Race car and Track
        obTrack = new CarPane();

        //Reference Car and Track
        obTrack2 = new CarPane();
        obTrack2.setStyle("-fx-background-color: lightgrey");

        //Set up the reference car's animation
        obTimeRace2 = new Timeline(new KeyFrame(Duration.millis(100), x -> //every 100 miliseconds do a frane
        {obTrack2.move();}));
        obTimeRace2.setCycleCount(Timeline.INDEFINITE);
        obTimeRace2.play();

        //Set up the race car's animation
        obTimeRace = new Timeline(new KeyFrame(Duration.millis(100), x -> obTrack.move()));
        obTimeRace.setCycleCount(Timeline.INDEFINITE);
        obTimeRace.play();


        VBox obCarBox = new VBox(40);

        obCarBox.setAlignment(Pos.TOP_CENTER);

        obCarBox.getChildren().addAll(obTrack2, obTrack);
        obCarBox.setPadding(new Insets(40, 0, 0, 0));


        //A border pane, top area for menu bar, bottom for the game control, center for tracks/cars
        BorderPane obPane = new BorderPane(obCarBox);
        obPane.setPadding(new Insets(0,0,10,0));


        // Create and add the game control, and the menu bar to the obPane
        obControl = new ControlBox();
        obPane.setBottom(obControl);

        //Set aligment for obControl in Center
        obControl.setAlignment(Pos.CENTER);

        timeLine = new Timeline(new KeyFrame(Duration.seconds(5), t->{
            System.out.println("5 seconds");
            obControl.generateRandom();
        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);

        //Create an Event when the user press ENTER
        obControl.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                //SpeedUp
                if(obControl.checkAnswer()){
                    obTrack.speedUp();


                }

                //SlownDown
                if(!obControl.checkAnswer()){
                    obTrack.slowDown();
                }

                //Generate new random numbers
                obControl.generateRandom();
            }
        });


        //Set menu
        obPane.setTop(createMenuBar());

        Scene obScene = new Scene(obPane, 1200, 400);

        //Bind tracks' width with the scene
        obScene.widthProperty().addListener(e -> {
            obTrack.setW(obScene.getWidth());
            obTrack2.setW(obScene.getWidth());
        });

        //Register the listener when the current game is finished INDICATE THE END OF THE GAME
        obTrack.raceFinishedProperty().addListener(e -> {
            obTrack.resetRace();
            obTrack2.resetRace();

            //Call the popUpWin
            popUpWin(obTrack);
        });
        obTrack2.raceFinishedProperty().addListener(e -> { // WHEN ONE ON THE CAR FINISH
            obTrack.resetRace();
            obTrack2.resetRace();

            //Call the popUpWin
            popUpWin(obTrack2);
        });

        obStage.setScene(obScene);
        obStage.setTitle("Racing in Saskatchewan");

        //Make obMainStage to be available to all the classes
        obMainStage = obStage;
        obMainStage.show();
    }

    private MenuBar createMenuBar()
    {
        MenuBar obBar = new MenuBar();
        //get menu is like getChildren and calls the methods
        obBar.getMenus().addAll(createFileMenu(), createHelpMenu());
        return obBar;
    }

    /**
     * Menu for Start, pause, Restart
     * @return
     */
    private Menu createFileMenu()
    {
        //Declare variables
        Menu mnFile = new Menu("File");
        miStart = new MenuItem("Start"); //New instance of menu item
        miPause = new MenuItem("Pause");
        miRestart = new MenuItem("Restart");

        //Hace una linea en el menu
        SeparatorMenuItem obSpacer = new SeparatorMenuItem();

        MenuItem miExit = new MenuItem("Exit");

        //Add items to the menu
        mnFile.getItems().addAll(miStart, miPause, miRestart, obSpacer, miExit);

        //Set items not visible and no disable
        miRestart.setVisible(false);
        miPause.setDisable(true);

        //Event for miStart
        miStart.setOnAction(event -> {
            obTrack2.setDx(2);
            miStart.setDisable(true);
            miPause.setDisable(false);
        });

        //Event for miPause
        miPause.setOnAction(event -> {
            iSpeedTrack = obTrack.getDx();
            System.out.println(iSpeedTrack);
            obTrack.setDx(0);
            obTrack2.setDx(0);

            miRestart.setVisible(true);
            miPause.setDisable(true);
            //miPause.setVisible(false);
        });

        //Event for miRestart
        miRestart.setOnAction(event ->{
            obTrack.setDx(iSpeedTrack);
            obTrack2.setDx(2);
            miPause.setDisable(false);
            miRestart.setVisible(false);
        });

        miExit.setOnAction( event -> obMainStage.close());
        return mnFile;
    }

    /**
     * Pane for show if you win or not the game
     * @param car
     */
    private void popUpWin(CarPane car){
        //Create the panes
        final Stage stage = new Stage();
        VBox vbox = new VBox(30);
        HBox hbox = new HBox(15);

        //Set the padding
        vbox.setPadding(new Insets(40, 40, 40, 40));

        //Buttons for continue and exist
        Button playAgain = new Button("Play again");
        Button exist = new Button("exist");

        //Check what car win
        if(obTrack == car){
            vbox.getChildren().add(new Text("CONGRATULATIONS \n" +"You winn!!"));
        }else {
            vbox.getChildren().add(new Text("SORRY  \n" +"You lose!!"));
        }

        //Add the children to the pane
        hbox.getChildren().addAll(playAgain,exist);
        vbox.getChildren().add(hbox);

        //Event for play again
        playAgain.setOnAction(e ->{
            obTrack.setDx(1/10);
            obTrack2.setDx(2);
            miStart.setDisable(true);
            miPause.setDisable(false);
            stage.close();
        });

        //event for exist
        exist.setOnAction(e ->{
            obMainStage.close();
            stage.close();
        });

        //stage
        stage.setTitle("Finish Game");
        stage.setScene(new Scene(vbox));
        stage.show();
    }

    private Menu createHelpMenu(){
        Menu mnHelp = new Menu("Help");
        MenuItem miAbout = new MenuItem("About");

        //Add the menu
        mnHelp.getItems().add(miAbout);

        //Set up the event handler
        miAbout.setOnAction(event -> {
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("About the author of the game");


            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(obMainStage);


            //Add to some container
            VBox dialogBox = new VBox(40);
            dialogBox.setPadding(new Insets(30, 30, 30, 30));
            dialogBox.getChildren().add(new Text("This game is developed by: \n\n       Laura Sofia Buitrago\n       " +
                    "SK, Canada"));

            HBox obHbox = new HBox(40);
            Button obButtonClose = new Button("Close");

            obHbox.getChildren().add(obButtonClose);

            dialogBox.getChildren().add(obHbox);

            //Si damos click en el boton de cerrar
            obButtonClose.setOnAction(e -> dialogStage.close());

            Scene dialogScene = new Scene(dialogBox, 350, 200);
            dialogStage.setScene(dialogScene);
            dialogStage.show();

        });

        return mnHelp;
    }



    public static void main(String[] args)
    {
        Application.launch(args);

    }

}

