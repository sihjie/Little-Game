package application;


import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	Stage stage;
	Pane root;	
	Timeline gameLoop;
	Text Time, Score;
	Label BG;
	
	ImageView pl;
	ImageView pl1;
	ImageView pl2;
	ImageView pl3;

	ImageView pupu;
	ImageView pupu1;
	ImageView pupu2;
	ImageView pupu3;

	
	MediaPlayer BGM;
	MediaPlayer Gameover;


	int ah = 50, aw = 80, pw = 40, ph = 40, P=3;
	private final double width=500,height=500;  // set screen size
	double angleSpeed = 300;
	final double minX = 0 , maxX = width-130 ;  // move range
	int time=0, score = 0;
	int Timecount=0;
	int pupuID;
	int type=0;
	double FPS=150;
	boolean GameOver = false;
	
	ArrayList<pupu> listOfpupus = new ArrayList<>();
	

	Image img_pl = new Image(getClass().getResourceAsStream("pl.png"),ah,aw,false,false);
	Image img_pl1 = new Image(getClass().getResourceAsStream("pl1.png"),ah,aw+20,false,false);
	Image img_pl2 = new Image(getClass().getResourceAsStream("pl2.png"),ah,aw+20,false,false);
	Image img_pl3 = new Image(getClass().getResourceAsStream("pl3.png"),ah,aw+27,false,false);
	Image img_pupu = new Image(getClass().getResourceAsStream("pupu.png"),pw,ph,false,false);
	Image img_BG = new Image(getClass().getResourceAsStream("BG.jpg"),width,height,false,false);
	
	Media media_BGM = new Media(getClass().getResource("Super Mario Bros. Soundtrack.mp3").toString());
	Media media_PUU = new Media(getClass().getResource("Untitled1.mp3").toString());
	Media media_Gameover = new Media(getClass().getResource("gameover.mp3").toString());

	ImageView bg=new ImageView(img_BG);	

	public void start(Stage primaryStage) {
		stage = primaryStage;   	
    	root = new Pane(); 
    	
    	Time = new Text("Time:" + time);
    	Score = new Text("Score:" + score);
    	BG = new Label();
    	BG.setGraphic(bg);
    	pl = new ImageView(img_pl);
    	pl1 = new ImageView(img_pl1);
    	pl2 = new ImageView(img_pl2);
    	pl3 = new ImageView(img_pl3);
    	
    	pupu = new ImageView(img_pupu);
    	BGM = new MediaPlayer(media_BGM);
    	Gameover = new MediaPlayer(media_Gameover);


    	Scene scene = new Scene(root,width,height); 
    	
		primaryStage.setScene(scene);
		primaryStage.setTitle("火柴人躲大便");
		primaryStage.show();

		if(!GameOver) {
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode()==KeyCode.RIGHT) {
						if(pl.getTranslateX() > width-ah)
							pl.setTranslateX(width-ah);
						if(type==0) {
							pl.setTranslateX(pl.getTranslateX()+20);
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
						}
						if(type==1) {
							pl.setTranslateX(pl.getTranslateX()+5);
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
						}
						if(type==2) {
							pl.setTranslateX(pl.getTranslateX()+2);
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
						}
						if(type==3) {
							pl.setTranslateX(pl.getTranslateX());
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
						}
					}
					
					if (event.getCode()==KeyCode.LEFT) {
						if(type==0) {
							pl.setTranslateX(pl.getTranslateX()-20);
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
						}
						if(type==1) {
							pl.setTranslateX(pl.getTranslateX()-5);
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
							//animationpl1.play();
							//angleVelocity.set(-angleSpeed);
						}
						if(type==2) {
							pl.setTranslateX(pl.getTranslateX()-2);
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
							//animationpl2.play();
							//angleVelocity.set(-angleSpeed);
						}
						if(type==3) {
							pl.setTranslateX(pl.getTranslateX());
							pl1.setTranslateX(pl.getTranslateX());
							pl2.setTranslateX(pl.getTranslateX());
							pl3.setTranslateX(pl.getTranslateX());
						}
					}
					if  (event.getCode() == KeyCode.ENTER) {
						if(GameOver)
							initializeGame();
			        }
				}
			});
		}
		Game();
	}
	
	void Game() {
		gameLoop = new Timeline(new KeyFrame(Duration.millis(1000/FPS), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
				for (int i=0;i<listOfpupus.size();i++) {
					listOfpupus.get(i).positionY+=1;
					
					listOfpupus.get(i).pupu.setTranslateY(listOfpupus.get(i).positionY);
					
					if(listOfpupus.get(i).positionX+(pw/2)>=pl.getTranslateX() && listOfpupus.get(i).positionX+(pw/2)<=pl.getTranslateX()+ah-10 && listOfpupus.get(i).positionY>=pl.getTranslateY()-ph) {
				    	MediaPlayer PUU;
						PUU = new MediaPlayer(media_PUU);
						PUU.play();
						root.getChildren().remove(i+7);
						listOfpupus.remove(i);
						type++;
					}
					if(listOfpupus.get(i).positionY>=height) {
						root.getChildren().remove(i+7);
						listOfpupus.remove(i);
					}
				}
				if(pl.getTranslateX() > width-ah-5) 
					pl.setTranslateX(width-ah-5);

				if(pl.getTranslateX() < 5)
					pl.setTranslateX(5);
				
				switch(type) {
				case 0:
					break;
				case 1:
					pl.setVisible(false);
					pl1.setVisible(true);
					break;
				case 2:
					pl2.setVisible(true);
					pl3.setVisible(false);
					break;
				case 3:
					pl.setVisible(false);
					pl1.setVisible(false);
					pl2.setVisible(false);
					pl3.setVisible(true);
					break;
				}
				
				if(Timecount%(FPS)==0) {
					switch((int)Math.random()*3+1) {
					case 1:
						addpupu();
						break;
					case 2:
					case 3:
						addpupu();
						break;
					}
					time++;
					score++;
					Time.setText("Time:" + time);
					Score.setText("Score:" + score);

				}
				if(time>=60) {
					if(Timecount%(FPS)==0 || Timecount%(FPS)==75) {
						switch((int)Math.random()*3+1) {
						case 1:
							addpupu();
							break;
						case 2:
						case 3:
							addpupu();
							break;
						}
						score++;
						//Time.setText("Time:" + time);
						//Score.setText("Score:" + score);

					}
				}
				if(time>=120) {
					if(Timecount%(FPS)==0 || Timecount%(FPS)==25 || Timecount%(FPS)==75) {
						switch((int)Math.random()*3+1) {
						case 1:
							addpupu();
							break;
						case 2:
						case 3:
							addpupu();
							break;
						}
						score++;
						//Time.setText("Time:" + time);
						Score.setText("Score:" + score);

					}
				}
					
				if(type==3) {
					//animationpl.pause();
					Text gameoverLabel = new Text();
					gameoverLabel.setFill(Color.RED);
					gameoverLabel.setFont(Font.font(null, FontWeight.BOLD, 40));
	    	    	gameoverLabel.setTranslateX(width/2-130);
	    	   		gameoverLabel.setTranslateY(height/2);
	    	   		gameoverLabel.setText("Enter 重新開始");
	    	   		root.getChildren().add(gameoverLabel);
	    	   		Text Time = new Text();
	    	   		Time.setFill(Color.RED);
	    	   		Time.setFont(Font.font(null, FontWeight.BOLD, 20));
	    	   		Time.setTranslateX(width/2-38);
	    	   		Time.setTranslateY(height/2+90);
	    	   		Time.setText("Time:" + time);
	    	   		root.getChildren().add(Time);
					Text Score = new Text();
					Score.setFill(Color.RED);
					Score.setFont(Font.font(null, FontWeight.BOLD, 28));
					Score.setTranslateX(width/2-60);
					Score.setTranslateY(height/2+50);
					Score.setText("Score:" + score);
	    	   		root.getChildren().add(Score);
	    	   		GameOver = true;
	    	   		BGM.stop();
	    	   		Gameover.play();
	        		gameLoop.stop();
				}
				
				Timecount++;
				
				}
			}));
		
			gameLoop.setCycleCount(-1);
			
		
			initializeGame();
	}
	
	void initializeGame() {
		root.getChildren().clear();
		root.getChildren().add(BG);
		root.getChildren().add(Time);
		root.getChildren().add(Score);
		root.getChildren().add(pl);
		root.getChildren().add(pl1);
		root.getChildren().add(pl2);
		root.getChildren().add(pl3);

		pl.setVisible(true);
		pl1.setVisible(false);
		pl2.setVisible(false);
		pl3.setVisible(false);
		
		BGM.play();
		Gameover.stop();
		
		angleSpeed = 300 ;  //move speed
		type=0;
		Timecount=0;
		time=0;
		score = 0;
		listOfpupus.clear();
		pupuID=0;
		pl.setTranslateX(width/2-40);
		pl.setTranslateY(height-aw);
		pl1.setTranslateX(width/2-40);
		pl1.setTranslateY(height-aw-20);
		pl2.setTranslateX(width/2-40);
		pl2.setTranslateY(height-aw-20);
		pl3.setTranslateX(width/2-40);
		pl3.setTranslateY(height-aw-27);
		Time.setFill(Color.BLACK);
		Time.setFont(Font.font(null,FontWeight.BOLD,28));
		Time.setTranslateX(width/2-170);
		Time.setTranslateY(30);
		Score.setFill(Color.BLACK);
		Score.setFont(Font.font(null,FontWeight.BOLD,28));
		Score.setTranslateX(width/2+50);
		Score.setTranslateY(30);

		gameLoop.play();
	}
	
	void addpupu() {
		pupuID++;
    	pupu pupu = new pupu(pupuID,img_pupu,0,(Math.random()*(width-pw))+1,-10);
    	listOfpupus.add(pupu);
    	root.getChildren().add(pupu.pupu);
    }

	public static void main(String[] args) {
        launch(args);
    }
	
}