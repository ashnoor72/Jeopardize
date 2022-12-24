/*The aim of the game is to prevent the ball from hitting the bottom of the screen with the help of the paddle as it 
*bounces off of the paddle. The player can move the paddle right and left with the respective arrow keys. As soon as the ball
*touches the bottom of the screen, the game ends and the user doesn't get the points, is told the current score and is taken
*back to the category option.However if you survive the 20 seconds, you win and get the points! The user is then taken back to
*the categories option.		
*/

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class BouncingBallGame 
{
	// declaring variables and Masks
	private int xPos, yPos, xDir, yDir, paddleX, paddleY;
	private int FRAME_WIDTH=800, FRAME_HEIGHT=500;
	private ImageIcon background, ball, paddle;
	private Rectangle2D paddleMask;
	private Ellipse2D ballMask;
	public boolean lost;  
	
	// creating the constructor
	public BouncingBallGame()
	{
		// initializing the values of the variables
		xPos=200;
		yPos=100;
		xDir=11;
		yDir=13;
		paddleX = 400; 
		paddleY = 420; 
		lost = false; 
		
		// creating the backgrounds
		background= new ImageIcon ("ballbackground.jpg");
		ball= new ImageIcon ("Ball.png");
		paddle= new ImageIcon ("paddle.png");
	
		//creating the masks of the ball and the paddle
		ballMask= new Ellipse2D.Double(xPos,yPos,ball.getIconWidth(),ball.getIconHeight());
		paddleMask= new Rectangle2D.Double(paddleX,paddleY, paddle.getIconWidth(), paddle.getIconHeight());
	}
	

	// method move 
	public void move()
	{
		// moves the ball vertically and horizontally according to the specified values of xDir and yDir
		xPos += xDir; 
		yPos += yDir; 
		
		//checking to see if the ball touches the left of the screen
		if ((xPos-10)<=0)
		{
			// reversing the direction if it does to prevent the ball from going out of the screen
			xDir=-xDir;
		}
		
		//checking to see if the ball touches the right of the screen
		else if (xPos+ball.getIconWidth()>=FRAME_WIDTH)
		{
			// reversing the direction if it does to prevent the ball from going out of the screen

			xDir=-xDir;
		}
		
		//checking to see if the ball touches the top of the screen
		else if ((yPos-10)<=0)
		{
			// reversing the direction if it does to prevent the ball from going out of the screen

			yDir=-yDir;
		}
		
		// creating the mask of the ball according to the new coordinates as it moves across the screen
		ballMask= new Ellipse2D.Double(xPos,yPos, ball.getIconWidth(), ball.getIconHeight());
		
	}
	
	// method intersection
	public void intersection()
	{
		//checking to see if the ball intersects the paddle
		if (ballMask.intersects(paddleMask))
		{
			//reversing its direction to give a bouncing effect
			yDir=-yDir;
		}
	}
	
	//method lost
	public boolean lost()
	{
		lost = false; 
		
		// if the ball touches the bottom of the screen
		if ((yPos+28)+ball.getIconHeight()>=FRAME_HEIGHT)
		{
			//changing the value of the variable lost to true indicating that the player lost
			lost = true; 
			
			//resetting the x and y coordinates of the ball and paddle
			xPos=50;
			yPos=100;
			
			paddleX=400;
			paddleY=420;
		}
		
		// returning the variable lost 
		return lost; 
	}

	// method for moving the paddle right
	public void PaddleRight()
	{
		// checking to see if it touches the right side of the screen
		if (( paddleX+10)+paddle.getIconWidth() >=FRAME_WIDTH)
		{
			// stopping the paddle if it does to prevent it from going outside the screen
			paddleX+=0;
		}
		
		else
		{
			// else moving the paddle right towards at a speed of 16
			paddleX+=16; 
		}
		
		//creating the mask according to the new position of the paddle as it moves
		paddleMask= new Rectangle2D.Double(paddleX, paddleY, paddle.getIconWidth(), paddle.getIconHeight());
	}
	
	// method for moving the paddle left

	public void PaddleLeft()
	{
		// checking to see if it touches the left side of the screen

		if (paddleX<=0)
		{
			// stopping the paddle if it does to prevent it from going outside the screen
			paddleX-=0;
		}
		else
		{
			// else moving the paddle left towards at a speed of 16
			paddleX-=16;
		}
		
		//creating the mask according to the new position of the paddle as it moves

		paddleMask= new Rectangle2D.Double(paddleX, paddleY, paddle.getIconWidth(), paddle.getIconHeight());

	}
	
	public void draw(Graphics2D g2)
	{
		// creating the background and creating the image of the ball and the paddle
		g2.drawImage(background.getImage(),0,0,null);
		g2.drawImage(ball.getImage(), xPos,yPos,null);
		g2.drawImage(paddle.getImage(), paddleX, paddleY, null);
	}
	
	
}
