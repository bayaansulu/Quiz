import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizMaker extends Application {
    static Stage window;
    static ArrayList<String> getAnswer = new ArrayList();
    static int index = 0;
    private ArrayList<Scene> scenes =  new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Stage stage = new Stage();
    private  Button left=null;
    private Button right = null;
    private int size = 0;
    private Timeline timeline;


    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        primaryStage.setTitle("Welcome to quiz!");
        primaryStage.setScene(new Scene(run(), 1200,700));
        primaryStage.show();
    }

    public static BorderPane run() {
        BorderPane borderPane = new BorderPane();
        Button button = new Button("Choose a file.");
        button.setStyle("-fx-font-weight: bold");
        button.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(window);
            Quiz quiz = new Quiz();
            quiz = quiz.loadFromFile(file.getPath());
            quiz.goTest();
            window.close();
        });

        Image image = new Image("file:///C:/Users/1/IdeaProjects/PROJECT2/resources/img/background.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(700);
        imageView.setFitWidth(1200);
        borderPane.setCenter(new StackPane(imageView, button));
        return borderPane;
    }

    public void goTest() {
        for (int i = 0; i <questions.size() ; i++) {
            left =  new Button("<");
            right =  new Button(">");
            BorderPane FinalPane;
            if (questions.get(i) instanceof FillIn) {
                FinalPane = new BorderPane();
                Label labels = new Label((i+1)+") "+questions.get(i).getDescription());
                labels.setFont(Font.font("Times new Roman", FontWeight.BOLD, 20.0D));
                labels.setWrapText(true);
                ImageView k = new ImageView(new Image((new File("C:\\Users\\1\\IdeaProjects\\PROJECT2\\resources\\img\\k.png")).toURI().toString(), 25.0D, 25.0D, false, true));
                HBox hbox = new HBox();
                hbox.getChildren().addAll(k, labels);
                hbox.setAlignment(Pos.CENTER);
                FinalPane.setTop(hbox);
                TextField textField = new TextField();
                textField.setMinWidth(250.0D);
                textField.setMaxWidth(250.0D);
                VBox vbox = new VBox();
                vbox.getChildren().addAll(new ImageView(new Image((new File("C:\\Users\\1\\IdeaProjects\\PROJECT2\\resources\\img\\fillin.png")).toURI().toString(), 250.0D, 300.0D, false, true)),textField);
                vbox.setAlignment(Pos.CENTER);
                FinalPane.setCenter(vbox);

                Scene scene = new Scene(FinalPane,800,500);
                scenes.add(scene);
            } else {
                Test test = new Test();
                FinalPane = new BorderPane();
                Label labels = new Label((i+1)+") "+questions.get(i).getDescription());
                labels.setFont(Font.font("Times New Roman", FontWeight.BOLD, 22.0D));
                labels.setWrapText(true);

                FinalPane.setTop(new StackPane(labels));
                FinalPane.setCenter(new ImageView(new Image((new File("C:\\Users\\1\\IdeaProjects\\PROJECT2\\resources\\img\\kahoot.gif")).toURI().toString(), 400.0D, 200.0D, false, true)));


                ToggleGroup g = new ToggleGroup();

                RadioButton red = new RadioButton();
                red.setToggleGroup(g);
                RadioButton blue = new RadioButton();
                blue.setToggleGroup(g);
                RadioButton orange = new RadioButton();
                orange.setToggleGroup(g);
                RadioButton green = new RadioButton();
                green.setToggleGroup(g);

                red.setMinHeight(50.0D);
                red.setMaxHeight(50.0D);
                red.setMaxWidth(250.0D);
                red.setMinWidth(250.0D);
                red.setWrapText(true);
                red.setText("A");
                red.setTextFill(Color.WHITE);
                red.setStyle("-fx-background-color: red");

                orange.setMinHeight(50.0D);
                orange.setMaxHeight(50.0D);
                orange.setMaxWidth(250.0D);
                orange.setMinWidth(250.0D);
                orange.setWrapText(true);
                orange.setText("B");
                orange.setTextFill(Color.WHITE);
                orange.setStyle("-fx-background-color: orange");

                blue.setMinHeight(50.0D);
                blue.setMaxHeight(50.0D);
                blue.setMaxWidth(250.0D);
                blue.setMinWidth(250.0D);
                blue.setWrapText(true);
                blue.setText("C");
                blue.setTextFill(Color.WHITE);
                blue.setStyle("-fx-background-color: blue");

                green.setMinHeight(50.0D);
                green.setMaxHeight(50.0D);
                green.setMaxWidth(250.0D);
                green.setMinWidth(250.0D);
                green.setWrapText(true);
                green.setText("D");
                green.setTextFill(Color.WHITE);
                green.setStyle("-fx-background-color: green");

                HBox hBox1 = new HBox(5.0D);
                hBox1.setAlignment(Pos.CENTER);
                hBox1.getChildren().addAll(red, blue);

                HBox hBox2 = new HBox(5.0D);
                hBox2.setAlignment(Pos.CENTER);
                hBox2.getChildren().addAll(orange, green);

                VBox vBox = new VBox(5.0D);
                vBox.getChildren().addAll(hBox1, hBox2);
                FinalPane.setBottom(vBox);
                Scene scene = new Scene(FinalPane,800,500);
                scenes.add(scene);
            }
            Button finish = new Button("✓");
            if (i==0){ FinalPane.setRight(new StackPane(right));
            }else if (i==5){
                FinalPane.setLeft(new StackPane(left));
                FinalPane.setRight(new StackPane(finish));
            }else {
                FinalPane.setRight(new StackPane(right));
                FinalPane.setLeft(new StackPane(left));
            }
            int finalI = i;
            right.setOnAction(event1 -> {
                stage.setScene(scenes.get(finalI +1));
            });
            left.setOnAction(event1 -> {
                stage.setScene(scenes.get(finalI-1));
            });
            finish.setOnAction(event -> {
                BorderPane borderPane =new BorderPane();
                Image image = new Image("file:///C:/Users/1/IdeaProjects/PROJECT2/resources/img/result.png");
                ImageView imageView = new ImageView(image);
                Text text = new Text("Your results:");
                text.setStyle("-fx-font-weight: bold;-fx-font-size: 22");
                Button show_answers = new Button("Show answers");
                Button close_test = new Button("Close Test");
                show_answers.setMaxWidth(300);
                close_test.setMaxWidth(300);
                close_test.setTextFill(Color.WHITE);
                show_answers.setTextFill(Color.WHITE);
                show_answers.setStyle("-fx-background-color: blue;-fx-font-weight: bold");
                close_test.setStyle("-fx-background-color: red;-fx-font-weight: bold");

                close_test.setOnAction(event1 -> {
                    stage.close();
                });

                VBox vBox = new VBox(show_answers,close_test);
                vBox.setSpacing(5);
                vBox.setAlignment(Pos.CENTER);

                borderPane.setCenter(vBox);
                imageView.setFitWidth(400);
                imageView.setFitHeight(300);
                borderPane.setTop(new StackPane(text));
                borderPane.setBottom(new StackPane(imageView));
                stage.setScene(new Scene(borderPane,800,500));
            });
            stage.setScene(scenes.get(0));
            stage.setTitle("Questions");
            stage.show();
        }
    }

    public Button getRight() {
        return right;
    }
    public Button getLeft() {
        return left;
    }
    public ArrayList<Scene> getScenes() {
        return scenes;
    }
    public void addQuestion(Question qq){
        this.questions.add(qq);
    }

    public String checkAns(String ss){
        Scanner sc = new Scanner(System.in);
        int a = ss.charAt(0);
        if (a < 69 && ss.length() == 1 && Character.isAlphabetic(ss.charAt(0)) && Character.isUpperCase(ss.charAt(0))){
            return ss;
        } else {
            System.out.print("Invalid choice! Try again (Ex: A, B, ...): ");
            String s = sc.nextLine();
            return checkAns(s);
        }
    }
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    public Scene getScenesAt(int i) {
        return scenes.get(i);
    }
    public Question getQuestionsAt(int i) {
        return questions.get(i);
    }
}

