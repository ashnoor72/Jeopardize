import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Jeopardy2021 extends JPanel implements  ActionListener, KeyListener, Runnable
{
	//creating fields(Global variables)
	public JFrame frame;    
	private String[] buttons;
	private static ImageIcon start;
	private static int choice=3, option=0, choicepoints=5;
	private String[] Netflix100, Netflix100ans, Netflix200, Netflix200ans, Netflix300, Netflix300ans; 
	private int randomnum, score, seconds, jeopardySecCount, ballseconds, count=1, count2=1;
	private Font font,f, fontball;
	private String answer; 
	private Timer ansTimer, carMove, fireTimer, jeopardyTimer, BallTimer, BallgameTimer, robogameTimer; 
	private boolean check, enterchecker, isActive;
	private Thread ques;
	private boolean b, checkpane, checkrobopane, checkballpane, postercheck;
	public Timer RoboMoveTimer, robotTimer;
	RoboPower robogame= new RoboPower();
	CarMiniGame carGame = new CarMiniGame(); 
	BouncingBallGame ballgame= new BouncingBallGame();
	
	private ImageIcon questionbackground, Robobackground;
	JOptionPane pane = new JOptionPane();
	private  ImageIcon startbackground; 
	
	public Jeopardy2021()
	{
		postercheck=true;
		
		//JFrame setup 
		frame = new JFrame(); 
		frame.setVisible(true);
		frame.setContentPane(this); 
		frame.setSize(440, 620);
		frame.setTitle("FINAL JEOPARDY 2021");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		

		//JPanel setup 
		setLayout(null); 
		setBackground(Color.WHITE); 

		addKeyListener(this); 
		setFocusable(true); 
		requestFocus(); 
		
		//setting the backgrounds
		questionbackground = new ImageIcon("background.jpg");
		Robobackground= new ImageIcon("Robobackground.jpg");
		start = new ImageIcon("start.png");
		
			
		//setting the value of all the boolean variables
		
		check=false;
		enterchecker=false;
		isActive=false;
		b=false;
		
		checkpane=true;
		checkrobopane=true;
		checkballpane=true;
	
		// initializing the length of the question and answer arrays
		Netflix100= new String[3];
		Netflix200 = new String[3]; 
		Netflix300 = new String[3];

		Netflix100ans= new String[3];
		Netflix200ans = new String[3]; 
		Netflix300ans= new String[3];

		//setting the values of the question and answer arrays

		Netflix100[0] = "AT THE END OF 2020, WHAT SHOW WILL NOT   \"BE THERE FOR YOU\" ON NETFLIX ANYMORE?";
		Netflix100[1] = "WHAT IS A CW SERIES THAT FOCUSES ON       SMALL TOWN MYSTERIES AND FEATURED ARCHIE IN THE ORIGINAL COMIC?";
		Netflix100[2] = "WHAT SHOW (NOW ON NBC) WAS SAVED BY        FANS AFTER AN OUTCRY ON SOCIAL MEDIA?";

		Netflix100ans[0]="FRIENDS";
		Netflix100ans[1]="RIVERDALE";
		Netflix100ans[2]="BROOKLYN 99";

		Netflix200[0]= "SHE WRITES LETTERS TO 5 DIFFERENT    GUYS IN \"TO ALL THE BOYS I HAVE EVER LOVED BEFORE\"";
		Netflix200[1]="BORED WITH BEING LORD OF HELL, HE    RELOCATES TO LOS ANGELES, WHERE HE OPENS A NIGHTCLUB";
		Netflix200[2]="EIGHT THIEVES TAKE HOSTAGES AND      LOCK THEMSELVES IN THE ROYAL MINT OF SPAIN";

		Netflix200ans[0]="LARA JEAN";
		Netflix200ans[1]="LUCIFER";
		Netflix200ans[2]="MONEY HEIST";

		Netflix300[0]="WHAT SHOW IS BASED ON A COMIC     BOOK, AND PORTRAYS LIFE AFTER A ZOMBIE APOCALYPSE?";
		Netflix300[1]="WHAT IS A TIMELESS CLASSIC SHOW    ABOUT A STRONG BOND BETWEEN THE MOTHER AND DAUGHTER?";
		Netflix300[2]="WHO IS THE FOUNDER OF NETFLIX";

		Netflix300ans[0]="THE WALKING DEAD";
		Netflix300ans[1]="GILMORE GIRLS";
		Netflix300ans[2]="REED HASTINGS";

		//setting the value of all integer variables
		randomnum=0;
		score=0;
		seconds = 30;
		ballseconds=20;
		
		//setting the fonts
		f = new Font("Britanic Bold", Font.PLAIN, 96);
		fontball= new Font ("Brittanic Bold", Font.PLAIN, 45);
		
		//setting the timers
		fireTimer= new Timer(100, this);
		robogameTimer= new Timer(1000, this);
		robotTimer= new Timer (10,this);
		carMove = new Timer(100, this); 
		ansTimer = new Timer(1000, this);
		RoboMoveTimer= new Timer(1000, this);
		jeopardyTimer= new Timer(1000, this);
		BallTimer= new Timer(50, this);
		BallgameTimer= new Timer(1000, this);
		
		//creating the question thread
		ques = new Thread(this);
		
	}

	public static void main(String[] args)
	{
		//calling on the main constructor
		new Jeopardy2021();	
	}

	public void keyPressed(KeyEvent f)
	{
		// if the user presses the enter key
			if(f.getKeyCode() == KeyEvent.VK_ENTER)
			{
				//set postercheck to false (this variable is used to make sure that the poster is being drawn only till the time the user doesn't press the enter key)
				postercheck=false;
				
				// check to see if enterchecker is false (this is to make sure that pressing enter will take the user to selecting the categories only once so that in case the user presses enter by mistake during the game, he/she is not taken back to the categories option)
				if (enterchecker==false)
				{
					//start the timer of the full game
					jeopardyTimer.start();
					
					// go to the method category
					category();
				}
			}
			
			// checking to see if the user pressed shift
			if (f.getKeyCode()==KeyEvent.VK_SHIFT)
			{
				// creating a for loop that will only run once
				for (int i=0; i<1;i++)
				{
					//setting the frame visibility to false and displaying the instructions
					frame.setVisible(false);	
					String name= JOptionPane.showInputDialog(null, "We are delighted to have you! Please enter your name");
					JOptionPane.showMessageDialog(null, "Hey " + name + " !! \n\nHere are the Instructions for you: \n");
				}
				
				//setting postercheck to true so that it can be drawn again, setting the frame size and visibility 
				postercheck=true;
				frame.setSize(440,620);
				frame.setVisible(true);
				
				//setting enterchecker to true so that the user can still press enter and go to the categories option
				enterchecker=false;
				
			}
		
		
		//checking if up key is pressed 
		else if (f.getKeyCode() == KeyEvent.VK_UP)
				{
				// if the user selected to play for 200 points under mini games
					if (choice==1 && choicepoints==1)
					{
						//call on the setYup method from the RoboPower class (which contains the code for moving the player up)
						robogame.Up();
					
					}
					
					// if the user selected to play for 300 points under mini games
					else if (choice==1 && choicepoints==2 )
					{
						// call on the up method from the CarMiniGame class (which contains the code for moving the car up)
						carGame.up(); 
						
					}
					//repaint the Frame to avoid images being drawn repeatedly
					repaint();
				}
		
			// if the user pressed the down arrow
		else if (f.getKeyCode()==KeyEvent.VK_DOWN)
			{
			// if the user selected to play for 200 points under mini games

				if (choice==1 && choicepoints==1)
				{
					//call on the setYdown method from the RoboPower class (which contains the code for moving the player down)

					robogame.Down();
				}
				// if the user selected to play for 300 points under mini games

				else if (choice==1 && choicepoints==2 )
				{
					// call on the down method from the CarMiniGame class (which contains the code for moving the car down)

					carGame.down(); 
				}
				//repaint the Frame to avoid images being drawn repeatedly

				repaint();
			}
		
			// if the user pressed the left key
		else if (f.getKeyCode()==KeyEvent.VK_LEFT)
			{
				
				// if the user selected to play for 100 points under mini games

				 if (choice == 1 && choicepoints==0)
				{
					//call on the PaddleLeft method from the Bouncingball class (which contains the code for moving the Paddle left)

					ballgame.PaddleLeft();
				}
				//repaint the Frame to avoid images being drawn repeatedly

				repaint();
				
			}
		
			// if the user pressed the right key
		else if (f.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				// if the user selected to play for 100 points under mini games
				if (choice == 1 && choicepoints==0)
				{
					//call on the PaddleRight method from the Bouncingball class (which contains the code for moving the Paddle right)

					ballgame.PaddleRight();
				}
				//repaint the Frame to avoid images being drawn repeatedly

				repaint();
			}
		
			// if the user pressed the space key
		else if (f.getKeyCode()==KeyEvent.VK_SPACE)
			{
			// if the user selected to play for 200 points under mini games

				if (choice==1 && choicepoints==1)
				{
					// start the fireTimer and go to the shoot method from the RoboPower class
					fireTimer.start(); 
					robogame.position(); 
				}
				//repaint the Frame to avoid images being drawn repeatedly

				repaint();
			}
				
	}
		
	public void keyReleased (KeyEvent e) {}
	public void keyTyped (KeyEvent e) {}

	//checks for the timers 
	public void actionPerformed(ActionEvent e)
	{
		//checking if the answer timer is the one that's working
		if (e.getSource() == ansTimer)
		{	
			//decreasing the seconds (initialized to 30) each second by 1  (It is the total time they have to answer the question)
			seconds --;    
			
			// if seconds elapse
			if (seconds==0)
			{
				//stop the timer, show that they couldn't answer, display their current score 
				ansTimer.stop();
				
				JOptionPane.showMessageDialog(null, "The time is over! You could not answer.", "Time over", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null, "Your current score is " + score, "Score", JOptionPane.OK_OPTION);
			 	
				//set answer to null
				answer=null;
				
				// dispose of the answer pane and go to the category method
				 if (answer == null)
					{
					 	pane.getRootFrame().dispose();	  
					 	category();
					    answer=" ";
					}
				 
				//check = false;
			}
			
			//till the time seconds are still above 0
			else if(seconds>0)
			{
				//check = true;
				
				//repaint the frame to avoid seconds being drawn on top of each other
				repaint();
				
			}
		}
		
		// if the fire timer is the one that's working
		if (e.getSource()== fireTimer)
		{	
			robogame.shoot();
			robogame.collision(); 
			if (robogame.inRoom == false)
			{
				fireTimer.stop(); 
			}
			repaint();
		}
		
		// if the Robot game timer is working 
		if (e.getSource()==robogameTimer)
		{
			robogame.seconds--;
			robogame.roboMovecheck++;
			
			
			if (robogame.count == 5)
			{
				score=score+200;
				RoboMoveTimer.stop();
				JOptionPane.showMessageDialog(null, "Congrats you killed 5 robots and got 200 points. " ,"WON", JOptionPane.PLAIN_MESSAGE);
				JOptionPane.showMessageDialog(null, "Your current score is " + score, "Score", JOptionPane.PLAIN_MESSAGE);
				robogame.roboMovecheck = 0;  
				robogame.count = 0; 
				category();
			}
			else if (robogame.seconds==0 && robogame.count!=5)
			{
				RoboMoveTimer.stop();
				JOptionPane.showMessageDialog(null, "Sorry you only killed " + robogame.count+" robot(s)" ,"LOST", JOptionPane.PLAIN_MESSAGE);
				JOptionPane.showMessageDialog(null, "Your current score is " + score, "Score", JOptionPane.PLAIN_MESSAGE);
				robogame.roboMovecheck = 0; 
				robogame.count = 0; 
				category();
			}
			
			// repaint the Frame
			repaint();
		}
		
		// if the carMove timer is the one that's working
		if (e.getSource() == carMove)
		{
			// call upon the MoveCar method (which automatically moves the car to the right and checks to see if it was able to pass round 1)
			carGame.MoveCar();	
			
			// if the value of the boolean method crash is true
			if (carGame.crash() == true)
			{
				// stop the car from moving by stopping its timer
				carMove.stop();
				
				//show to the user that they lost, show the current score and take them back to the categories option
				JOptionPane.showMessageDialog(null, "The car crashed! You Lost");
				JOptionPane.showMessageDialog(null,"Your current score is " + score);
				category(); 
				
				//change its value to false so that it resets if the the user chooses to play again
				carGame.lost = false; 
	
			}
			
			// if the value of the boolean method win is true
			else if (carGame.win()==true)
			{
				// stop the car by stopping its timer
				carMove.stop();
				
				// show that they won, increase their score by 300 and show their current score
				JOptionPane.showMessageDialog(null, "You win 300 points");
				score=score+300;
				JOptionPane.showMessageDialog(null,"Your current score is " + score);
				
				//call upon the category method
				category(); 
			}
			
			// repaint the Frame
			repaint();
		}
		
		// if the RoboMoveTimer is the one that's working
		if (e.getSource()==RoboMoveTimer)
		{
			robogame.roboMovecheck++;
			
			if (robogame.roboMovecheck==30)
			{
				RoboMoveTimer.stop();
			}
	
		}
		
		// if the Jeopardy Timer is the one that's working (this keeps track of how much time has elapsed since the user started playing)
		if (e.getSource()==jeopardyTimer)
		{
			// increase the value by 1 each second
			jeopardySecCount++;
						
			// once 300 seconds (5 minutes) have passed
			if (jeopardySecCount==300)
			{
				// show them their current score and tell them that they can't play any longer
				JOptionPane.showMessageDialog(null, "Time's UP!!! Can't play any more! Your final score was " + score);
				
				//exit the game
				System.exit(0);
			}
			
			//repaint 
			repaint();
		}
		
		// if the ballTimer is working (which makes the ball move)
		if (e.getSource()==BallTimer)
		{
			//call upon the move method and the intersection method from the Bouncing Ball class  (which contain the code for moving the ball and checking to see if it intersects with the paddle)
			ballgame.move();
			ballgame.intersection();
			
			// if the value of the lost variable is true
			if(ballgame.lost()==true)
			{
				// stop the timer of the game so that the seconds don't keep on decreasing and stop the ball from moving as well by stopping its timer
				BallgameTimer.stop();
				BallTimer.stop();
				
				// show that they lost and display their current score and call upon the method category
				JOptionPane.showMessageDialog(null, "You lost! The ball touched the ground!");
				JOptionPane.showMessageDialog(null, "Your current score is " + score, "Score", JOptionPane.PLAIN_MESSAGE);
				category(); 
				
				//reset the value of the variable to false so that the loop doesn't run immediately again if the user chooses to play again
				ballgame.lost=false; 
			}
				
			//repaint
			repaint();
		}
		
		// if the BallGameTimer is running
		if (e.getSource()==BallgameTimer)
		{
			//decrease the value of ballseconds by 1 each second (which is initialized to 20 as they have to survive in the game for 20 seconds)
			ballseconds--;	
			
			// if the seconds have elapsed
			if (ballseconds==0)
			{
				//stop the ball and the timer of the game and increase the score by 100
				BallTimer.stop();
				BallgameTimer.stop();
				score=score+100;
				
				//show that they were successful, show their current score and call upon the method category
				JOptionPane.showMessageDialog(null,"Great Job! You were able to save the ball from touching the floor");
				JOptionPane.showMessageDialog(null, "Your current score is " + score, "Score", JOptionPane.PLAIN_MESSAGE);
				category();
			}
			
			//repaint
			repaint();
		
		}
	}

	
	//calling upon the method category which asks the user for the category they wanna play and for how many points
	public void category()
	{
		
		// if the variable count is 2
		if (count==2)
		{
			//set the value of 'b' and 'isActive' to false
			b = false;
			isActive=false;
			
		}
		
		//set the visibility of the frame to false so that we can only see the JOptionPanes
		frame.setVisible(false);
		
		//personalizing the options and storing them in an array
		String[] buttons= {"Netflix", "Mini Games","Quit"};

		// keep prompting the user for an input 
		do
		{
			//storing their choice of category in the variable choice
			choice = JOptionPane.showOptionDialog(null, "Choose a category to play:", "FINAL JEOPARDY",JOptionPane.INFORMATION_MESSAGE, 0,start, buttons, null);
			seconds=30;
			
			// if the user clicks the quit option
			if (choice!=0 && choice!=1)
			{
				// ask for confirmation to quit
				option= JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","Quit",JOptionPane.YES_NO_OPTION);

				// if the user selects yes
				if (option==JOptionPane.YES_OPTION)
				{
					//exit the game and stop the game timer
					jeopardyTimer.stop();
					System.exit(0);
				}
			}
		
		}
		//till the time the choice entered is not 0 or 1 (its quit)
		while (choice!=0&&choice!=1);


		//checking if any other option is chosen
		if (choice==0||choice==1 )
		{
			// personalizing the options available
			String[] points= {"100", "200","300"};

			//storing their option selected in the variable choicepoints
			choicepoints=JOptionPane.showOptionDialog(null, "How many points would you like to play for?", "FINAL JEOPARDY",JOptionPane.INFORMATION_MESSAGE, 0,start, points, null);			
			
			//check=true; 
			
			//generating a random number between 0 and 2
			randomnum = (int) (Math.random()*3);
			
			//calling on the method level which creates the frame
			level();
			
			if (count==2)
			{
				question(); 
			}
			
			//checking to see if 'b' and 'isActive' are false and if the user selected Netflix from the categories available
			if (b==false && isActive==false && choice==0)
			{
				System.out.println("bbbb"); //THIS IS PRINT
				
				// if count is 1 (this is what it is initialized to implying that the game is running for the first time)
				if (count==1) 
				{
					System.out.println("cccc");
					//change the value of count to 2 so that this loop only runs once
					count=2;
					
					//starts the question thread
					ques.start(); 
				}
				
				// if the choice is Netflix
				if (choice==0)
				{
					//set 'b' to true and 'isActive' to true as well as they need to be true for the question method to work later on in the program
					System.out.println("ccc"); 
					b=true;
					isActive=true;
				}
				
			}		
			
						
		}
		
		// if Netflix is chosen for however many points
		if (choice==0 && choicepoints==0 ||choicepoints==1|| choicepoints==2)
		{
			//set the value of seconds to 30 as this is the time they have to answer the questions from any of the points and this will also reset thier value every time the user chooses to play
			seconds=30;
		}
		
		// if mini games is chosen for 100 points
		if (choice==1 && choicepoints==0 )
		{
			//set the value of ball seconds to 20 as this is the time that they have to survive for
			ballseconds=20;
			
			// if the value of checkballpane is true (it is initialized to true as well)
			if (checkballpane==true)
			{
				//display the welcome message and the instructions
				JOptionPane.showMessageDialog(null,"Welcome to Bouncing Ball!! \n\nINSTRUCTIONS: The aim of the game is to prevent the ball from hitting the bottom \nof the screen with the help of the paddle as it bounces off of the paddle. The player can move the paddle right \nand left with the respective arrow keys. As soon as the ball touches the bottom of the screen, the game \nends and the user doesn't get the points and is taken back to the category option. However if you survive the 20 seconds, \nyou win and get the points!");
				
				//change the value to false so that the message is only displayed once
				checkballpane=false;
			}
			
			//start the ballTimer which moves the ball and the game timer which starts the 20 seconds
			BallTimer.start();
			BallgameTimer.start();
			
		}
		
		//if mini games is chosen for 200 points 
		
		if (choice==1 && choicepoints==1)
		{
			// if the value of checkrobopane is true (it is initialized to true as well)
			if (checkrobopane==true)
			{
				//display the welcome message and the instructions
				JOptionPane.showMessageDialog(null,"Welcome to Robo Power! You are in the middle of a robot apocalypse and you have to survive for \n30 seconds to win the game and get 200 points. The player can only move up/down however \ncan face either left or right and shoot a gun based on his position and the gun can kill the robots. The player\n only has 1 life which means that once the robot collides with the player, the game is over and the user\n is taken to select a category option.");
				
				//change the value to false so that the message is only displayed once
				checkrobopane=false;
			}
			
			//set the value of the seconds to 30 as this is the time that they have to survive for and this also resets it every time the suer chooses to play for 200 points under the mini games option
			robogame.seconds=30;
			
			//start the robogame timer which starts the seconds countdown as well as the roboMove timer which increases the value of how many robots have been killed
			robogameTimer.start();		
			RoboMoveTimer.start();
		}
		
		// if mini games is chosen for 300 points
		if (choice==1 && choicepoints==2)
		{
			// check if checkpane is set to true (this is to make sure the instructions pane is only displayed once to the user)
				if (checkpane==true)
				{
					// show the instructions pane and change the value of checkpane to false so that the loop can't run again
					JOptionPane.showMessageDialog(null,  "WELCOME TO MINI CARE GAME: \n\nInstructions: There are two levels in the game and the car\n can only move up and down using the respective arrow keys.\n There are a lot of obstacles on the track and you\n have to navigate your way around the obstacles and \nreach the end. Once you reach the end, the second level\n begins and the goal is again to reach the end without crashing into\n any obstacles. If you are able to do it, your score increases by 300,\n if not, you are shown your current score and taken back to selecting the categories.");
					checkpane=false;
				
				}
			//start the carMove timer which automatically starts moving the car
			carMove.start(); 
		}
		
		//change the value of enterchecker to true so that the enter key can only be used once to go to the categories option (when the game first loads) and that the jeopardy game timer also can only be started once
		enterchecker=true;
	}
	
	//paint method (draws stuff for us)
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g; 
		
		// checking to see if the value of posterceck is true (to make sure it only comes once as its set to false once the user presses enter)
		if (postercheck==true)
		{
			//storing the image of the background and creating the image
			startbackground = new ImageIcon ("Jeopardize.png");
			g2.drawImage(startbackground.getImage(),0,0, this);
		}
		
		// if 100 points were selected for Netflix
		if (choice==0 && choicepoints==0) //&& check==true)
		{
			//check=false; 

			//draw the background, set the font, display the text (which is split up in half using the substring method from the String class)
			g2.drawImage(questionbackground.getImage(), 0, 0, this); 
			g2.setFont(font);				
			g2.setColor(Color.WHITE);
			g2.drawString(Netflix100[randomnum].substring(0,35), 10, 100);
			g2.drawString(Netflix100[randomnum].substring(36), 10, 120);
			g2.setFont(f);
			
			//display the seconds and start the answer Timer
			g2.drawString(Integer.toString(seconds), (frame.getWidth() / 2)-50 ,400);
			ansTimer.start(); 
			
		}
		
		// if 200 points were chosen for Netflix
		else if (choice==0 && choicepoints==1)// && check==true)
		{
			//check=false; 

			
			//draw the background, set the font, display the text (which is split up in half using the substring method from the String class)

			g2.drawImage(questionbackground.getImage(), 0, 0, this); 
			g2.setFont(font);				
			g2.setColor(Color.WHITE);
			g2.drawString(Netflix200[randomnum].substring(0,35), 10, 100);
			g2.drawString(Netflix200[randomnum].substring(36), 10, 120);
			g2.setFont(f);
			
			//display the seconds and start the answer Timer

			g2.drawString(Integer.toString(seconds), (frame.getWidth() / 2)-50 ,400);
			ansTimer.start(); 
		}
		
		//if 300 points were chosen for Netflix
		if (choice==0 && choicepoints==2)// && check==true)
		{
			//check=false; 

			//draw the background, set the font, display the text (which is split up in half using the substring method from the String class)

			g2.drawImage(questionbackground.getImage(), 0, 0, this); 
			g2.setFont(font);				
			g2.setColor(Color.WHITE);
			g2.drawString(Netflix300[randomnum].substring(0,35), 10, 100);
			g2.drawString(Netflix300[randomnum].substring(36), 10, 120);
			g2.setFont(f);
			
			//display the seconds and start the answer Timer

			g2.drawString(Integer.toString(seconds), (frame.getWidth() / 2)-50 ,400);
			ansTimer.start(); 	 

		}
		
		// if mini games was chosen for 100 points
		else if (choice==1 && choicepoints==0)
		{
			//call upon the draw method from the Bouncing Ball class which draws the background, the ball and the paddle
			ballgame.draw(g2);	
			
			//set the font and color and display the seconds at the top of the screen
			g2.setFont(fontball);
			g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(ballseconds), 350,100);
			
		}
		
		// if mini games was chosen for 200 points
		else if (choice==1 && choicepoints==1 )
		{
			//draw the background and call upon the draw method from the RoboPower class
			g2.drawImage(Robobackground.getImage(),0,0, this);
			robogame.draw(g2);
		}
		
		// if mini games was chosen for 300 points
		else if (choice==1 && choicepoints==2)
		{		
			//reset the size of the frame and centre it
			frame.setSize(1200,300);
			frame.setLocationRelativeTo(null);
			
			// call upon the draw method from the CarMiniGame class which draws other objects
			carGame.draw(g2); 
		}

	}
	
	//method level (creats the JFrame for us)
	public void level()
	{
		// if the user selects 100, 200 or 300 points
		if (choicepoints==0 || choicepoints==1||choicepoints==2  )
		{
			// sets the size of the new frame, its location, and visibility
			frame.setSize(800,500);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			//setting the font
			font=new Font ("Century Gothic",Font.BOLD,17);
			
			//repainting the frame
			repaint();
		}


	}
	
	// question method which checks how many points the user chose to play for from the category Netflix
	public void question()
	{
		// if the user chose netflix for 100 points
		if (choice==0 && choicepoints==0)
		{
			
		//delay the appearance of the answer pane by 2 seconds to give the player the time to read the question
			try 
			{
				Thread.sleep(2000); //delaying it by 2 seconds (value is 2000 as it counts in milliseconds)
			}
			catch(InterruptedException e){}
			
			//showing the answer pane that asks for input
			answer = pane.showInputDialog(null,"Enter in your answer (You only have 30 seconds)", "Answer", JOptionPane.QUESTION_MESSAGE);
			
			// if the user does not put anything, they are taken to the category method
				if (answer==null)
				{
					category();
				}
				
			// if the answer the user put matches the correct answer
				if (answer.equalsIgnoreCase(Netflix100ans[randomnum]) )
				{
					//stop the answer Timer (so that it doesn't go up till 0 and show that they lost even though they have already answered)
					ansTimer.stop();
					
					//show the congratulations message, increase the score by 100, show the current score and call upon the category method
					JOptionPane.showMessageDialog(null,"Congratulations! Your answer was correct! You got 100 points!");
					score=score+100;
					JOptionPane.showMessageDialog(null, "Your current score is " + score);
					category();
				}
		
			// if the user's answer does not match with the correct answer
				else 
				{
					//stop the answer timer
					ansTimer.stop();
					
					// show a message saying they couldn't answer, their current score and call upon the category method
					JOptionPane.showMessageDialog(null,"Sorry, your answer was incorrect.");
					JOptionPane.showMessageDialog(null, "Your current score is " + score);
					category();
				}
			
			
		}
		
		// if the user chose Netflix for 200 points
		else if (choice==0 && choicepoints==1)
		{
	
			//delay the appearance of the answer pane by 2 seconds to give the player the time to read the question

			try 
				{
					Thread.sleep(2000); //delaying it by 2 seconds (value is 2000 as it counts in milliseconds)
				}
				catch(InterruptedException e){} 

				//showing the answer pane that asks for input

				answer= pane.showInputDialog(null,"Enter in your answer (You only have 30 seconds)", "Answer", JOptionPane.QUESTION_MESSAGE);
			
				// if the user does not put anything, they are taken to the category method

				if (answer==null)
				{
					category();
				}
					
				// if the answer the user put matches the correct answer

				if (answer.equalsIgnoreCase(Netflix200ans[randomnum]))
				 {
					//stop the answer Timer (so that it doesn't go up till 0 and show that they lost even though they have already answered)

					 ansTimer.stop();
					 
					//show the congratulations message, increase the score by 200, show the current score and call upon the category method

					 JOptionPane.showMessageDialog(null,"Congratulations! Your answer was correct! You got 200 points!");
					 score=score+200; 
					 JOptionPane.showMessageDialog(null, "Your current score is " + score);
					 category();
				 }	
				
				// if the user's answer does not match with the correct answer

				 else
				 {
					//stop the answer timer

					 ansTimer.stop();
					 
					// show a message saying they couldn't answer, their current score and call upon the category method

					 JOptionPane.showMessageDialog(null,"Sorry, your answer was incorrect.");
					 JOptionPane.showMessageDialog(null, "Your current score is " + score);
					 category();
				 }
				
				
		}
		
		// if the user chooses Netflix for 300 points
		else if (choice==0 &&choicepoints==2)
		{
			//delay the appearance of the answer pane by 2 seconds to give the player the time to read the question

			try 
			{
				Thread.sleep(2000); //delaying it by 2 seconds (value is 2000 as it counts in milliseconds)
			}
			catch(InterruptedException e){}

			//showing the answer pane that asks for input
			answer= pane.showInputDialog(null,"Enter in your answer (You only have 30 seconds)", "Answer", JOptionPane.QUESTION_MESSAGE);
			
			// if the user does not put anything, they are taken to the category method
			if (answer==null)
			{
				category();
			}
			
			// if the answer the user put matches the correct answer

			 if (answer.equalsIgnoreCase(Netflix300ans[randomnum]))
			 {
				//stop the answer Timer (so that it doesn't go uptill 0 and show that they lost even though they have already answered)
				 ansTimer.stop();
				 
				//show the congratulations message, increase the score by 300, show the current score and call upon the category method

				 JOptionPane.showMessageDialog(null,"Congratulations! Your answer was correct! You got 300 points!");
				 score=score+300;
				 JOptionPane.showMessageDialog(null, "Your current score is " + score);
				 category();
			 }
			
			 // if it doesn't
			 else
			 {
				//stop the answer timer

				 ansTimer.stop();
				 
				// show a message saying they couldn't answer, their current score and call upon the category method
				 JOptionPane.showMessageDialog(null,"Sorry, your answer was incorrect.");
				 JOptionPane.showMessageDialog(null, "Your current score is " + score);
				 category();
			}	
			
		}
		
		
	}
	
	// run method which is connected to the Thread
		public void run()
		{
			System.out.println("nnnnnnnnnn");
			// creating an infinite loop
			while (true)
			{
				// if the value of 'b' is true
				if (b == true)
				{
					//call upon the question method 
					System.out.println("AAAAAA");
					question();
					
				}
				
			}
		}		
	
}
