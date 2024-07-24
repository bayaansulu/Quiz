import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.io.*;
public class Quiz implements Runnable{
    private String name;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private ArrayList<Scene> Scenes = new ArrayList<Scene>();
    private Stage stage = new Stage();
    private  Button left=null;
    private Button right = null;
    private int size = 0;
    private Timeline timeline;
    Quiz(){
    }

//    public void start(){
//        System.out.println(this.toString());
//    }
//    public String toString(){
//        int count = 0;
//        System.out.println("====================================================\n");
//        System.out.println("WELCOME TO \"" +  this.getName() + "\" QUIZ! " + "\n");
//        System.out.println("____________________________________________________\n");
//        System.out.println("                                                    ");
//        Scanner sc = new Scanner(System.in);
//        Collections.shuffle(this.questions);
//
//        for (int i = 0; i < this.questions.size(); i++) {
//            System.out.println((i + 1) + ".  " + this.questions.get(i).toString());
//            System.out.println("-----------------------------------");
//            if (this.questions.get(i) instanceof Test){
//                Test test = (Test)this.questions.get(i);
//                System.out.print("Enter the correct choice: ");
//                String answer = sc.nextLine();
//                int a = answer.charAt(0);
//                if (a > 64 && a < 69 && answer.length() == 1 && Character.isAlphabetic(answer.charAt(0)) && Character.isUpperCase(answer.charAt(0))){
//                    int indexOfUsersAnswer = test.getIndexOfLabel(answer);
//                    if (test.getAnswer().equals(test.getOptionAt(indexOfUsersAnswer))){
//                        System.out.println("Correct!");
//                        count++;
//                    } else {
//                        System.out.println("Incorrect!");
//                    }
//                } else {
//                    answer = checkAns(answer);
//                    int indexOfUsersAnswer = test.getIndexOfLabel(answer);
//                    if (test.getAnswer().equals(test.getOptionAt(indexOfUsersAnswer))){
//                        System.out.println("Correct!");
//                        count++;
//                    } else {
//                        System.out.println("Incorrect!");
//                    }
//                }
//
//            } else if (this.questions.get(i) instanceof Fillin){
//                Fillin fillin = (Fillin)this.questions.get(i);
//                System.out.print("Type your answer: ");
//                String answer = sc.nextLine();
//                answer = answer.toLowerCase();
//                if (answer.equals(fillin.getAnswer().trim())){
//                    System.out.println("Correct!");
//                    count++;
//                } else {
//                    System.out.println("Incorrect!");
//                }
//            }
//            System.out.println();
//        }
//
//        String string = "Correct Answers: " + count + "/" + this.questions.size() + " " + "(" + (((double)count / (double)this.questions.size()) * 100.0d) + "%)";
//        return string;
//    }

    public Quiz loadFromFile(String ss){
        File file = new File(ss);
        Quiz q = new Quiz();
        try {
            Scanner sc = new Scanner(file);
            String allQuestionsInFile = "";
            while (sc.hasNext()){
                allQuestionsInFile += sc.nextLine() + "\n ";
            }
            allQuestionsInFile = allQuestionsInFile.trim();
            String[] questionss = allQuestionsInFile.split("\\n \\n");
            for (String s: questionss) {
                String[] thisQuestion = s.split("\n");

                if (thisQuestion.length == 2){
                    FillIn fillin = new FillIn();
                    for (int i = 0; i < 2; i++) {
                        if (i == 0){
                            thisQuestion[i] = thisQuestion[i].replace("{blank}","");
                            fillin.setDescription(thisQuestion[i]);
                            q.addQuestion(fillin);
                        } else {
                            thisQuestion[i] = thisQuestion[i].toLowerCase();
                            fillin.setAnswer(thisQuestion[i]);
                        }
                    }

                } else if (thisQuestion.length == 5){
                    Test test = new Test();
                    String[] options = new String[4];
                    for (int i = 0; i < 5; i++) {
                        if (i == 0){
                            test.setDescription(thisQuestion[i]);
                            q.addQuestion(test);
                        } else if (i == 1){
                            test.setAnswer(thisQuestion[i]);
                            options[i - 1] = thisQuestion[i];
                        } else {
                            options[i - 1] = thisQuestion[i];
                        }
                    }
                    test.setOptions(options);

                } else {
                    throw new InvalidQuizFormatException();
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File NOT FOUND. ");
        } catch (InvalidQuizFormatException e){
            e.printStackTrace();
            System.exit(1);
        }
        return q;
    }


    public void goTest() {
        for (int i = 0; i <questions.size() ; i++) {
            left =  new Button("<<");
            right =  new Button(">>");
            BorderPane FinalPane;
            if (questions.get(i) instanceof FillIn) {
                FinalPane = new BorderPane();
                Label labels = new Label((i+1)+") "+questions.get(i).getDescription());
                labels.setFont(Font.font("Times new Roman", FontWeight.BOLD, 20.0D));
                labels.setWrapText(true);
                ImageView k = new ImageView(new Image((new File("C:\\Users\\1\\IdeaProjects\\PROJECT2\\resources\\img\\k.png")).toURI().toString(), 50, 30, false, true));
                HBox hbox = new HBox();
                hbox.getChildren().addAll(k, labels);
                hbox.setAlignment(Pos.CENTER);
                FinalPane.setTop(hbox);
                TextField textField = new TextField();
                textField.setMinWidth(250.0D);
                textField.setMaxWidth(250.0D);
                VBox vbox = new VBox();
                vbox.getChildren().addAll(new ImageView(new Image((new File("C:\\Users\\1\\IdeaProjects\\PROJECT2\\resources\\img\\fillin.png")).toURI().toString(), 700, 550, false, true)),textField);
                vbox.setAlignment(Pos.CENTER);
                FinalPane.setCenter(vbox);

                Scene scene = new Scene(FinalPane,1200,700);
                Scenes.add(scene);
            } else {
                Test test = new Test();
                FinalPane = new BorderPane();
                Label labels = new Label((i+1)+") "+questions.get(i).getDescription());
                labels.setFont(Font.font("Times New Roman", FontWeight.BOLD, 22.0D));
                labels.setWrapText(true);

                FinalPane.setTop(new StackPane(labels));
                FinalPane.setCenter(new ImageView(new Image((new File("C:\\Users\\1\\IdeaProjects\\PROJECT2\\resources\\img\\kahoot.gif")).toURI().toString(),650 , 400, false, true)));

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
                Scene scene = new Scene(FinalPane,1200,700);
                Scenes.add(scene);
            }
            Button finish = new Button("✓");
            if (i==0){ FinalPane.setRight(new StackPane(right));
            }else if (i==3){
                FinalPane.setLeft(new StackPane(left));
                FinalPane.setRight(new StackPane(finish));
            }else {
                FinalPane.setRight(new StackPane(right));
                FinalPane.setLeft(new StackPane(left));
            }
            int finalI = i;
            right.setOnAction(event1 -> {
                stage.setScene(Scenes.get(finalI +1));
            });
            left.setOnAction(event1 -> {
                stage.setScene(Scenes.get(finalI-1));
            });
            finish.setOnAction(event -> {
                BorderPane borderPane =new BorderPane();
                Image image = new Image("file:///C:/Users/1/IdeaProjects/PROJECT2/resources/img/result.png");
                ImageView imageView = new ImageView(image);
                Text text = new Text("Your results:");
                text.setStyle("-fx-font-weight: bold;-fx-font-size: 22");
                Button show_answers = new Button("Show answers");
                Button close_test = new Button("Close Test");
                show_answers.setMaxWidth(500);
                close_test.setMaxWidth(500);
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
                imageView.setFitWidth(600);
                imageView.setFitHeight(400);
                borderPane.setTop(new StackPane(text));
                borderPane.setBottom(new StackPane(imageView));
                stage.setScene(new Scene(borderPane,1200,700));
            });
            stage.setScene(Scenes.get(0));
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
        return Scenes;
    }
    public void addQuestion(Question qq){
        this.questions.add(qq);
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
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
        return Scenes.get(i);
    }
    public Question getQuestionsAt(int i) {
        return questions.get(i);
    }
    @Override
    public void run() {
        int s = 0;
        int m =0;
    }
}


