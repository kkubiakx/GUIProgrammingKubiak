package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Main extends Application {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private GraphicsContext g;

    //time var
    private double t = 0.0;

    private final int windowWidth = 800;
    private final int windowHeight = 800;

    private final int btnWidth = 100;
    private final int btnHeight = 50;

    private double prevX = windowWidth/2;
    private double prevY = windowHeight/2;

        private final int sliderWidth = 500;
    private final int sliderHeight = 50;

    //parameters for lissajous curve func
    private double a = 1;
    private double b = 1;
    private double delta = 0.5;

    private boolean isPaused = true;

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getPrevX() {
        return prevX;
    }

    public void setPrevX(double prevX) {
        this.prevX = prevX;
    }

    public double getPrevY() {
        return prevY;
    }

    public void setPrevY(double prevY) {
        this.prevY = prevY;
    }

        public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    /////////////          Sliders
    final Slider aSlider = new Slider(0.0, 5, a);
    final Slider bSlider = new Slider(0.0, 5, b);
    final Slider deltaSlider = new Slider(0, 2, delta);

    List<Slider> sliders = new ArrayList<Slider>();

    //labels
    final Label aCaption = new Label("a: ");
    final Label bCaption = new Label("b: ");
    final Label deltaCaption = new Label("ð: ");

    //value labels
    final Label aValue = new Label(Double.toString(aSlider.getValue()));
    final Label bValue = new Label(Double.toString(bSlider.getValue()));
    final Label deltaValue = new Label(Double.toString(deltaSlider.getValue()) + "π");

    final Label xEquation = new Label("x = sin(" + aValue.getText() + "t + " + deltaValue.getText() + ")");
    final Label yEquation = new Label("y = sin(" + bValue.getText() + "t)");

    final static Color textColor = Color.BLACK;

    //buttons
    final Button pauseButton = new Button("Start");
    final Button resetButton = new Button("Reset");

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(windowWidth,windowHeight);
        root.setBackground(new Background(new BackgroundFill (Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        g = canvas.getGraphicsContext2D();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //smooth but visible drawing
                if(!isPaused) {
                    t += 0.035;
                    draw();
                }
            }
        };
        timer.start();

        setSliders();
        setLabels();
        setButtons();

        //bind labels to the center of the window
        xEquation.layoutXProperty().bind(root.widthProperty().subtract(xEquation.widthProperty()).divide(2));
        yEquation.layoutXProperty().bind(root.widthProperty().subtract(xEquation.widthProperty()).divide(2));

        root.getChildren().addAll(canvas, aSlider, bSlider, deltaSlider,
                aCaption, bCaption, deltaCaption, pauseButton, resetButton,
                aValue, bValue, deltaValue, xEquation, yEquation);
        return root;
    }

    private void draw() {
        Point2D p = curveFunction();

        g.setStroke(Color.RED);

        double newX = windowWidth/2 + p.getX();
        double newY = windowHeight/3*2 + p.getY();

        if(prevX != windowWidth/2 && prevY != windowHeight/2)
            g.strokeLine(prevX, prevY, newX, newY);

        //g.strokeOval(newX, newY, 1, 1);

        prevX = newX;
        prevY = newY;
    }

    private Point2D curveFunction() {
        double x = sin(a*t + delta*PI);
        double y = sin(b*t);

        return new Point2D(x, y).multiply(200);
    }

    private void pauseButton() {
        if(isPaused) {
            setPaused(false);
            pauseButton.setText("Pause");
        } else {
            setPaused(true);
            pauseButton.setText("Start");
        }
    }

    private void reset() {
        boolean wasRunning = false;
        if(!isPaused) {
            setPaused(true);
            wasRunning = true;
        }
        g.clearRect(0, 0, windowWidth, windowHeight);
        t = 0.0;
        if(wasRunning)
            setPaused(false);
        setPrevX(windowWidth/2);
        setPrevY(windowHeight/2);
        setEquations();
    }

    public void setEquations() {
        xEquation.setText("x = sin(" + aValue.getText() + "t + " + deltaValue.getText() + ")");
        yEquation.setText("y = sin(" + bValue.getText() + "t)");
    }

    public void setSliders() {
        aSlider.setPrefSize(sliderWidth, sliderHeight);
        bSlider.setPrefSize(sliderWidth, sliderHeight);
        deltaSlider.setPrefSize(sliderWidth, sliderHeight);

        aSlider.relocate((windowWidth - sliderWidth)/2, 10);
        bSlider.relocate((windowWidth - sliderWidth)/2, 60);
        deltaSlider.relocate((windowWidth - sliderWidth)/2, 110);

        aSlider.setShowTickLabels(true);
        aSlider.setShowTickMarks(true);
        aSlider.setMajorTickUnit(0.5);
        bSlider.setShowTickLabels(true);
        bSlider.setShowTickMarks(true);
        bSlider.setMajorTickUnit(0.5);
        deltaSlider.setShowTickLabels(true);
        deltaSlider.setShowTickMarks(true);
        deltaSlider.setMajorTickUnit(0.25);

        aSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                setA(new_val.doubleValue());
                aValue.setText(df2.format(new_val));
                reset();
            }
        });

        bSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                setB(new_val.doubleValue());
                bValue.setText(df2.format(new_val));
                reset();
            }
        });

        deltaSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                setB(new_val.doubleValue());
                deltaValue.setText(df2.format(new_val) + "π");
                reset();
            }
        });
    }

    public void setLabels() {
        aCaption.setFont(Font.font(30));
        aCaption.relocate((windowWidth - sliderWidth)/2 - 40, 5);
        bCaption.setFont(Font.font(30));
        bCaption.relocate((windowWidth - sliderWidth)/2 - 40, 55);
        deltaCaption.setFont(Font.font(30));
        deltaCaption.relocate((windowWidth - sliderWidth)/2 - 40, 105);

        aValue.setFont(Font.font(30));
        aValue.relocate((windowWidth + sliderWidth)/2 + 10, 5);
        bValue.setFont(Font.font(30));
        bValue.relocate((windowWidth + sliderWidth)/2 + 10, 55);
        deltaValue.setFont(Font.font(30));
        deltaValue.relocate((windowWidth + sliderWidth)/2 + 10, 105);

        xEquation.setFont(Font.font(30));
        xEquation.relocate(0, 160);

        yEquation.setFont(Font.font(30));
        yEquation.relocate(0, 195);
    }

    public void setButtons() {
        pauseButton.relocate(windowWidth/2 - btnWidth - 5, 250);
        resetButton.relocate(windowWidth/2 + 5, 250);

        pauseButton.setOnAction(value -> {
            pauseButton();
        });
        pauseButton.setPrefWidth(btnWidth);
        pauseButton.setPrefHeight(btnHeight);

        resetButton.setOnAction(value -> {
            reset();
        });
        resetButton.setPrefWidth(btnWidth);
        resetButton.setPrefHeight(btnHeight);

    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
