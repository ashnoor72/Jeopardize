import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RoboPower extends JPanel 
{
	// Declaring and Initializing global variables, the JFRAME, the timers and the Rectangle2D boxes.
	private int xPlayer, yPlayer, xFire, yFire, FRAME_HEIGHT, FRAME_WIDTH, roboX, roboY;
	public int seconds, roboMovecheck;
	private ImageIcon Robot, player,gun;
	public boolean isFired, inRoom;
	private Ellipse2D fireMask;
	private Rectangle2D roboMask;
	public boolean intersects= false, gunIntersects = false ;
	private Font f;
	Random rand; 
	public int count, position; 



	public RoboPower()
	{
		xPlayer = 100; 
		yPlayer = 250; 
		
		count = 0; 
		
		roboX = 0; 
		roboY = 0; 
		
		xFire = xPlayer; 
		yFire = yPlayer;
		
		FRAME_HEIGHT = 500;
		FRAME_WIDTH = 800; 
		
		seconds = 30; 
		
		Robot = new ImageIcon("iRobot.png"); 
		player  = new ImageIcon("PlayerRight.png");
		gun = new ImageIcon("gun.png");

		roboMovecheck =0; 
	
		rand = new Random(); 
		
		roboMask = new Rectangle2D.Double(roboX, roboY, Robot.getIconWidth(), Robot.getIconHeight());
		fireMask = new Ellipse2D.Double(xFire, yFire, gun.getIconWidth(), gun.getIconHeight());
		
	}
	
	public void Up()
	{
		
		if (yPlayer<=0)
		{
			yPlayer -=0; 
		}
		else 
		{
			yPlayer-=10; 
		}
		//yFire = yPlayer;
	}
	
	public void Down()
	{
		if (((yPlayer+40)+player.getIconHeight())>=FRAME_HEIGHT)
		{	
			yPlayer+=0;  
		}	
		else 
		{
			yPlayer+=10; 
		}
		//yFire = yPlayer;
	}
	public void position()
	{
		yFire = yPlayer; 
	}
	
	public void shoot()
	{		
			
			isFired = true; 
			inRoom = true; 
		
			
			xFire+=50; 
		
			if (xFire>FRAME_WIDTH)
			{
				isFired = false; 
				inRoom = false; 
				xFire = xPlayer;
			}
			else 
			{
				inRoom = true; 
			}
			
	}
	public void RoboSpawn()
	{	
		if ((roboMovecheck/2) == 0 )
		{
			roboX = rand.nextInt(500)+200; 
			roboY = rand.nextInt(250)+100;
		}
	
	}
	
	public void collision()
	{
		if (fireMask.intersects(roboMask))
		{
			roboY = -50; 
			xFire = 1000; 
			count++; 
		}
	}

	
	public void draw(Graphics2D g2)
		{			
			//drawing the image of the player
			g2.drawImage(player.getImage(),xPlayer,yPlayer,this);
		
			//set the font and display the seconds count at the bottom of the screen
			f=new Font("BRITTANIC BOLD",Font.BOLD,20);
			g2.setFont(f);
			g2.drawString("Seconds remaining:" ,10,400);
			g2.drawString(Integer.toString(seconds), 220,400);
			
			// if the variables are true implying the user pressed the space key
			if (isFired==true && inRoom==true)
			{
				// draw the image of the fire at the specified coordinates
				g2.drawImage(gun.getImage(),xFire,yFire,this);
				
				fireMask = new Ellipse2D.Double(xFire, yFire, gun.getIconWidth(), gun.getIconHeight());
				
			}
			
			RoboSpawn(); 
			g2.drawImage(Robot.getImage(), roboX, roboY, this);
			roboMask = new Rectangle2D.Double(roboX, roboY, Robot.getIconWidth(), Robot.getIconHeight());
		}
				
}