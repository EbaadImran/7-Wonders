import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class WonderGraphics extends JPanel implements ActionListener{
	
	private ImageIcon bg, logo, arrow;
	private boolean isTitleScreen, isPlayScreen, isWinScreen;
	private int y, yVel, turn; //this turn is for the arrow, don't want to mess up the actual turn
	private Timer tm;
	private ArrayList<Object> gs; 
	
	public WonderGraphics()
	{
		turn = 1;
		gs = new ArrayList<Object>();
		setSize(1920, 1080);
		isTitleScreen = true;
		isPlayScreen = false;
		isWinScreen = false;
		tm = new Timer(38, this);
		y = 1080/2 + 30;
		yVel = 1;
		bg = new ImageIcon("bg.jpg");
		logo = new ImageIcon("logo.png");
		arrow = new ImageIcon("chevron.png");
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(isTitleScreen)
		{
			g.drawImage(bg.getImage(), 0, 0, 1920, 1080, this);
			g.drawImage(logo.getImage(), 1920/2 - 295, 1080/2 - 310, 570, 460, this);
			g.setColor(Color.WHITE);
			g.setFont(new Font("default", Font.BOLD, 27));
			g.drawString("CLICK TO PLAY", 1920/2 - 120, y);
			tm.start();
		}
		else if(isPlayScreen)
		{
			g.drawImage(bg.getImage(), 0, 0, 1920, 1080, this);
			g.drawImage(logo.getImage(), 1695, -50, 225, 200, this);
			g.drawImage(arrow.getImage(), 512/4, 1080 - (512/3), -1*(512/4), 512/4, this);
			g.drawImage(arrow.getImage(), 129, 1080 - (512/3), 512/4, 512/4, this);
			g.setColor(Color.WHITE);
			g.setFont(new Font("default", Font.BOLD, 27));
			g.drawString(turn+"", 1920/2, 1080/2);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (y < 550 || y > 575)
		{
			yVel = -yVel;
		}
		y = y + yVel;
		repaint();
	}
	public void setGS(ArrayList<Object> gs)
	{
		this.gs = gs;
		repaint();
	}
	public boolean isTitleScreen()
	{
		return isTitleScreen;
	}
	public void turnOffTitleScreen()
	{
		isTitleScreen = false;
		isPlayScreen = true;
	}
	public boolean isPlayScreen()
	{
		return isPlayScreen;
	}
	public int getTurn()
	{
		return turn;
	}
	public void setTurn(int t)
	{
		turn = t;
		repaint();
	}
}