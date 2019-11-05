import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class WondersGraphics extends JPanel{
	
	private BufferedImage bg, logo;
	private boolean isTitleScreen;
	
	public WondersGraphics()
	{
		setSize(1920, 1080);
		isTitleScreen = true;
		try {
			bg = ImageIO.read(getClass().getResource("bg.jpg"));
			logo = ImageIO.read(getClass().getResource("logo.png"));
		}
		catch(IOException e)
		{
			System.out.println("Error");
		}
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, 1920, 1080, null);
		g.drawImage(logo, 1695, -55, 225, 200, null);
		if(isTitleScreen)
		{
			g.setColor(Color.RED);
			g.setFont(new Font("default", Font.BOLD, 30));
			g.drawString("CLICK TO PLAY", 1920/2 - 100, 1080/2 + 30);
		}
		else
		{
			
		}
	}
}
