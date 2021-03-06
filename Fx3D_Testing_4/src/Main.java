import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
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
    private static double anchorX, anchorY;
    private static double anchorAngleX = 0;
    private static double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);


    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        RotateableGroup group = new RotateableGroup();
        child = root.getChildren();
        Scene myScene = new Scene(root, width - 10, height - 10, true, SceneAntialiasing.BALANCED);
        myScene.setFill(backcolor);

        double y = -250;
        for (int i = 0; i < 360 * 6; i += 21)
        {
            Point2D location = Utils.endPoint(new Point2D(0, 0), i, 130);
            Sphere temp = new Sphere(10);
            temp.setTranslateX(location.getX());
            temp.setTranslateZ(location.getY());
            temp.setTranslateY(y);
            group.getChildren().add(temp);
            y += 5;
        }

        child.addAll(group);
        Camera camera = new PerspectiveCamera(true);
        myScene.setCamera(camera);

        initMouseControl(group, myScene, stage);

        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-800);
        camera.setNearClip(1);
        camera.setFarClip(10000);

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
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(myScene);
        stage.show();
        root.requestFocus();
    }

    private void initMouseControl(RotateableGroup group, Scene scene, Stage stage)
    {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);
        scene.setOnMousePressed(e -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
            //System.out.println("hello");
        });
        scene.setOnMouseDragged(e -> {
            angleX.set(anchorAngleX - (anchorY - e.getSceneY()) / 5);
            double ang = angleX.get() % 360;
            if (ang < 0)
            {
                while (ang < 0)
                {
                    ang += 360;
                }
            }
            if (ang > 90 && ang <= 270)
            {
                angleY.set(anchorAngleY - (anchorX - e.getSceneX()) / 5);
            } else
            {
                angleY.set(anchorAngleY + (anchorX - e.getSceneX()) / 5);
            }
            //System.out.printf("Angle: %3.2f, x:%3.2f, y:%3.2f\n",ang ,angleX.get(), angleY.get()); // DEBUG
        });
        stage.addEventHandler(ScrollEvent.SCROLL, e -> {
            group.translateZProperty().set(group.getTranslateZ() + e.getDeltaY());
        });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}