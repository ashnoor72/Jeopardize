/*There are two levels in the game and the car can only move up and down using the respective arrow keys. There are a lot 
 * of obstacles on the track and you have to navigate your way around the obstacles and reach the end. Once you reach the 
 * end, the second level begins where the speed of the car is faster and the goal is again to reach the end without crashing 
 * into any obstacles. If you are able to do it, your score increases by 300 and if not, you are told the current score
 * and are taken back to selecting the categories.
 */
			
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class CarMiniGame extends JPanel {

	//declaring variables and initializing it where needed
	private ImageIcon car, road, cone, stop, brokenCar; 
	private int xPos, yPos, speed, round; 
	public Rectangle2D carMask;
	public Ellipse2D coneMask, cone2Mask,cone3Mask,cone4Mask,cone5Mask, stopMask, stop2Mask, stop3Mask, stop4Mask, stop5Mask; 
	public Rectangle2D Mask; 
	private boolean win = false; 
	public boolean lost = false; 

 
//constructor	
	public  CarMiniGame()
	{	
		round = 0; 
		
		//images 
		car = new ImageIcon("car.png");
		road = new ImageIcon("road.png");
		cone = new ImageIcon("cone.png"); 
		stop = new ImageIcon("stop.png");
		brokenCar = new ImageIcon("BrokenCare.png");
		speed = 10; 
		xPos = 0; 
		yPos = 150; 
		
		//masks for all the objects 
		carMask = new Rectangle2D.Double(xPos, yPos, car.getIconWidth(), car.getIconHeight());

		coneMask = new Ellipse2D.Double(900, 80, cone.getIconWidth(), cone.getIconHeight());
		cone2Mask = new Ellipse2D.Double(1100, 220, cone.getIconWidth(), cone.getIconHeight());
		cone3Mask = new Ellipse2D.Double(900, 170, cone.getIconWidth(), cone.getIconHeight());
		cone4Mask = new Ellipse2D.Double(250, 200, cone.getIconWidth(), cone.getIconHeight());
		cone5Mask = new Ellipse2D.Double(500, 20, cone.getIconWidth(), cone.getIconHeight());
		
		stopMask = new Ellipse2D.Double(800, 120, stop.getIconWidth(), stop.getIconHeight());
		stop2Mask = new Ellipse2D.Double(400, 150, stop.getIconWidth(), stop.getIconHeight());
		stop3Mask = new Ellipse2D.Double(1000, 30, stop.getIconWidth(), stop.getIconHeight());
		stop4Mask = new Ellipse2D.Double(200, 70, stop.getIconWidth(), stop.getIconHeight());
		stop5Mask = new Ellipse2D.Double(700, 150, stop.getIconWidth(), stop.getIconHeight());

		
	}

	
	//moves the car up 
	public void up()
	{
		//checking to see if the car touches the top of the screen

			if (yPos<=0)
			{
				// stopping it to prevent the car from going out of the screen

				yPos-=0; 
			}
			else 
			{
				//otherwise moving it up at a speed of 10
				yPos -=10;
			}
	}
	
	//moves the car down 
	public void down()
	{
		//checking to see if the car touches the bottom of the screen

			if (yPos+car.getIconHeight()>260)
			{
				// stopping it to prevent the car from going out of the screen

				yPos += 0; 
			}
			else 
			{
				//otherwise moving it down at a speed of 10

				yPos +=10; 
			}
	}
		
	//moves the car to the right
	public void MoveCar()	
	{
		//moving the car right at the speed specified
			xPos += speed; 
			
			//creating the mask of the car according to the new coordinates 
			carMask = new Rectangle2D.Double(xPos, yPos, car.getIconWidth(), car.getIconHeight());
		
			// if the car moves out of the screen (indicating the user was able to cross level 1)
			if (xPos + car.getIconWidth() >= 1200)
			{
				//create the mask of the car
				carMask = new Rectangle2D.Double(xPos, yPos, car.getIconWidth(), car.getIconHeight());
				//set the x-position and increase the number of round
				xPos = 0 - car.getIconWidth(); 
				round++;	
			}	
			// if the user is on the second round, then speed is 15
			if (round ==1)
			{
				speed = 15; 
			}
	}
	
	//checks if the car has crashed or if it has done 2 rounds
	public boolean crash()
	{
		//set lost to false if t hasn't crashed
		lost = false; 
		
		//checking if the car intersects with any of the obstacles 
		if (coneMask.intersects(carMask) || cone2Mask.intersects(carMask) || cone3Mask.intersects(carMask) || cone4Mask.intersects(carMask)|| cone5Mask.intersects(carMask)
				|| stopMask.intersects(carMask)||  stop2Mask.intersects(carMask)|| stop3Mask.intersects(carMask)	|| stop4Mask.intersects(carMask)|| stop5Mask.intersects(carMask))
		{
			
			//reset the values and set lost to true indicating the car crashed
				win = false; 
				lost = true; 
				xPos = 0; 
				yPos = 150; 
				speed = 10; 
				round = 0; 
		}
		
		//return the value of lost
		return lost; 
	}
	
	//check to see if the user won
	public boolean win()
	{
		win = false; 
		// if round is 2 then reset the variables to their starting values
		if (round==2)
		{
			lost = false; 
			xPos = 0; 
			yPos = 150; 
			win = true; 
			speed = 10; 
			round = 0; 
		}
		
		//return the value of win
		return win; 
	}
	
	public void draw(Graphics2D g2)
	{
		//drawing background
		g2.drawImage(road.getImage(), 0, -10, this); 
		
		//drawing a mask around the car 
		carMask = new Rectangle2D.Double(xPos, yPos, car.getIconWidth(), car.getIconHeight());
		
		//drawing the car 
		g2.drawImage(car.getImage(), xPos, yPos, this) ; 
		
		// if its the first round
		if (round ==0)
		{		
			//drawing the cones
			g2.drawImage(cone.getImage(), 900, 80, this); 
			g2.drawImage(cone.getImage(), 1100, 220, this); 
			g2.drawImage(cone.getImage(), 900, 170, this);
			g2.drawImage(cone.getImage(), 250, 200, this);
			g2.drawImage(cone.getImage(), 500, 20, this);
		
			//drawing the other obstacle
			g2.drawImage(stop.getImage(), 800, 120, this);
			g2.drawImage(stop.getImage(), 400, 150, this);
			g2.drawImage(stop.getImage(), 1000, 30, this);
			g2.drawImage(stop.getImage(), 200, 70, this);
			g2.drawImage(stop.getImage(), 700, 150, this);
			
			//creating the masks of all the obstacles
			coneMask = new Ellipse2D.Double(900, 80, cone.getIconWidth(), cone.getIconHeight());
			cone2Mask = new Ellipse2D.Double(1100, 220, cone.getIconWidth(), cone.getIconHeight());
			cone3Mask = new Ellipse2D.Double(900, 170, cone.getIconWidth(), cone.getIconHeight());
			cone4Mask = new Ellipse2D.Double(250, 200, cone.getIconWidth(), cone.getIconHeight());
			cone5Mask = new Ellipse2D.Double(500, 20, cone.getIconWidth(), cone.getIconHeight());
			
			stopMask = new Ellipse2D.Double(800, 120, stop.getIconWidth(), stop.getIconHeight());
			stop2Mask = new Ellipse2D.Double(400, 150, stop.getIconWidth(), stop.getIconHeight());
			stop3Mask = new Ellipse2D.Double(1000, 30, stop.getIconWidth(), stop.getIconHeight());
			stop4Mask = new Ellipse2D.Double(200, 70, stop.getIconWidth(), stop.getIconHeight());
			stop5Mask = new Ellipse2D.Double(700, 150, stop.getIconWidth(), stop.getIconHeight());
			
			Mask = new Rectangle2D.Double(-10, 400 , stop.getIconWidth(), stop.getIconHeight());
		}
		
		//changing the positions of the obstacles if its the second round
		else if (round ==1)
		{
			//drawing the cones
			g2.drawImage(cone.getImage(), 250, 200, this);
			g2.drawImage(cone.getImage(), 250, 150, this); 
			g2.drawImage(cone.getImage(), 259, 85, this); 
			g2.drawImage(cone.getImage(), 750, 150, this);	
			g2.drawImage(cone.getImage(), 750, 90, this);
		
			//drawing the other obstacle
			g2.drawImage(stop.getImage(), 500, 20, this);
			g2.drawImage(stop.getImage(), 580, 20, this);
			g2.drawImage(stop.getImage(), 1000, 25, this);
			g2.drawImage(stop.getImage(), 550, 200, this);
			g2.drawImage(stop.getImage(), 1100, 25, this);
			g2.drawImage(brokenCar.getImage(),900  ,100, this);
			
			//creating the masks
			coneMask = new Ellipse2D.Double(250, 200, cone.getIconWidth(), cone.getIconHeight());
			cone2Mask = new Ellipse2D.Double(250, 150, cone.getIconWidth(), cone.getIconHeight());
			cone3Mask = new Ellipse2D.Double(259, 85, cone.getIconWidth(), cone.getIconHeight());
			cone4Mask = new Ellipse2D.Double(750, 150, cone.getIconWidth(), cone.getIconHeight());
			cone5Mask = new Ellipse2D.Double(750, 90, cone.getIconWidth(), cone.getIconHeight());
			
			stopMask = new Ellipse2D.Double(500, 20, stop.getIconWidth(), stop.getIconHeight());
			stop2Mask = new Ellipse2D.Double(580, 20, stop.getIconWidth(), stop.getIconHeight());
			stop3Mask = new Ellipse2D.Double(1000, 25, stop.getIconWidth(), stop.getIconHeight());
			stop4Mask = new Ellipse2D.Double(550, 200, stop.getIconWidth(), stop.getIconHeight());
			stop5Mask = new Ellipse2D.Double(1100, 25, stop.getIconWidth(), stop.getIconHeight());
			
			Mask = new Rectangle2D.Double(900, 100, stop.getIconWidth(), stop.getIconHeight());
		}
		
		
		
	}	
	
}

