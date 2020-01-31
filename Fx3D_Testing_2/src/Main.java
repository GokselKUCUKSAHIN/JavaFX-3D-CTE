import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci";
    public static final int width = 800;
    public static final int height = 800;
    private static Color backcolor = Color.rgb(51, 51, 51);

    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        Group group = new Group();
        child = root.getChildren();
        Scene myScene = new Scene(root, width - 10, height - 10, backcolor);


        Box box = new Box(100,20,50);
        group.getChildren().addAll(box);
        child.addAll(group);


        Camera camera = new PerspectiveCamera(true);
        myScene.setCamera(camera);

        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-500);

        camera.setNearClip(1);
        camera.setFarClip(1000);

        //
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //PLAY
                    update.play();
                    break;
                }
                case F2:
                {
                    //PAUSE
                    update.pause();
                    break;
                }
                case F3:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
                case W:
                {
                    camera.translateZProperty().set(camera.getTranslateZ() + 25);
                    break;
                }
                case S:
                {
                    camera.translateZProperty().set(camera.getTranslateZ() - 25);
                    break;
                }
                case UP:
                {
                    box.rotateProperty().set(box.)
                    break;
                }
            }
        });
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            System.out.println("loop test");
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        //update.play(); //uncomment for play when start
        //
        stage.setTitle(title);
        //stage.setResizable(false);
        stage.setScene(myScene);
        stage.show();
        root.requestFocus();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
