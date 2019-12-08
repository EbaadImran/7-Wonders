import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class WonderGraphics extends JPanel implements ActionListener{
	private ImageIcon bg, logo, arrow, jetson;
	private boolean isTitleScreen, isPlayScreen, isWinScreen, isPlayCardScreen, isTradeScreen, isHaliScreen;
	private int y, yVel, turn, handSize, currAction; //this turn is for the arrow, don't want to mess up the actual turn
	private Card currCard;
	private Timer tm;
	private ArrayList<Object> gs; 
	
	public WonderGraphics()
	{
		turn = 0;
		gs = new ArrayList<Object>();
		setSize(1920, 1080);
		isTitleScreen = true;
		isPlayScreen = false;
		isTradeScreen = false;
		isWinScreen = false;
		isHaliScreen = false;
		tm = new Timer(38, this);
		y = 1080/2 + 30;
		yVel = 1;
		bg = new ImageIcon("backg.png");
		logo = new ImageIcon("logo.png");
		arrow = new ImageIcon("chevron.png");
		jetson = new ImageIcon("jetson.jpg");
	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(isTitleScreen)
        {
            g.drawImage(bg.getImage(), 0, 0, 1920, 1080, null);
            g.drawImage(logo.getImage(), 1920/2 - 295, 1080/2 - 310, 570, 460, null);
            g.setColor(new Color(34, 161, 181));
            g.setFont(new Font("papyrus", Font.BOLD, 27));
            g.drawString("CLICK TO PLAY", 1920/2 - 120, y);
            tm.start();
        }
        else if(isPlayScreen)
        {
            g.drawImage(bg.getImage(), 0, 0, 1920, 1080, null);
           g.drawImage(logo.getImage(), 1695, -50, 225, 200, null);
            g.drawImage(arrow.getImage(), 512/4, 1080 - (512/3), -1*(512/4), 512/4, null);
            g.drawImage(arrow.getImage(), 129, 1080 - (512/3), 512/4, 512/4, null);
            Player[] ps = (Player[]) gs.get(0);
        
            paintCards(g, 535, 592, "brown");
            paintCards(g, 700, 400, "blue");
            paintCards(g, 870, 400, "gold");
            paintCards(g, 1040, 400, "red");
            paintCards(g, 1210, 400, "green");
            paintCards(g, 1380, 400, "purple");
            
            if((int)gs.get(2)==this.turn) {
                 ArrayList<Card> c = ps[(int)gs.get(2)].getHand();
                 handSize = c.size();
                 int x = 275, y = 25;
                 for(Card cd:c) {
                     x = x + 150;
                     g.drawImage(new ImageIcon(cd.getName()+".png").getImage(), x, y, 144, 220, null);
                 }
             }
            g.setColor(new Color(34, 161, 181));
            g.setFont(new Font("papyrus", Font.BOLD, 35));
            
            int coins = ps[turn].getCoins();
            int neg = ps[turn].getPoints().get(-1);
            int one = ps[turn].getPoints().get(1);
            int three = ps[turn].getPoints().get(3);
            int five = ps[turn].getPoints().get(5);
            
            if(turn!=(int)gs.get(2)) {
            	g.drawString("Viewing Player " + (turn+1), 1920/2 - 125, 1020);
            }
            g.drawImage(new ImageIcon("coin.png").getImage(), 1370, 640, 50, 50, null);
            g.drawString("x" + coins, 1430, 670);
            g.drawImage(new ImageIcon("victory1.png").getImage(), 1370, 690, 50, 50, null);
            g.drawString("x" + one, 1430, 720);
            g.drawImage(new ImageIcon("victory3.png").getImage(), 1370, 740, 50, 50, null);
            g.drawString("x" + three, 1430, 770);
            g.drawImage(new ImageIcon("victory5.png").getImage(), 1370, 790, 50, 50, null);
            g.drawString("x" + five, 1430, 820);
            g.drawImage(new ImageIcon("victoryminus1.png").getImage(), 1370, 840, 50, 50, null);
            g.drawString("x" + neg, 1430, 870);
            
            Card[] stages = ps[this.turn].getWonder().getStages();
        	if(stages[0] != null)
        		g.drawImage(new ImageIcon("age" + stages[0].getAge() + ".png").getImage(), 888-240, 1080/2 + 200, 144, 220, null);
        	if(stages[1] != null)
        		g.drawImage(new ImageIcon("age" + stages[1].getAge() + ".png").getImage(), 888, 1080/2 + 200, 144, 220, null);
        	if(stages[2] != null)
        		g.drawImage(new ImageIcon("age" + stages[2].getAge() + ".png").getImage(), 888+240, 1080/2 + 200, 144, 220, null);
            
            ImageIcon wndr = new ImageIcon(ps[this.turn].getWonder().getName()+".png");
            g.drawImage(wndr.getImage(), 1920/2 - 400, 1080/2 + 100, null);
            g.setFont(new Font("papyrus", Font.BOLD | Font.ITALIC, 75));
            g.drawString("Age " +  (int) gs.get(1), 10, 75);
            g.setFont(new Font("papyrus", Font.BOLD, 50));
            g.drawString("Round " +  (int) gs.get(3), 10, 175);
            g.drawString("Player " + ((int) gs.get(2) + 1) +"'s Turn", 10, 275);
        }
        else if(isPlayCardScreen) 
        {
        	int age = (int) gs.get(1);
        	Player[] ps = (Player[]) gs.get(0);
        	int turn = (int) gs.get(2);
            g.drawImage(bg.getImage(), 0, 0, 1920, 1080, null);
            g.drawImage(logo.getImage(), 1695, -50, 225, 200, null);
            ImageIcon build = new ImageIcon("card.png");
            ImageIcon wonder = new ImageIcon("pyramid-stage3.png");
            ImageIcon discard = new ImageIcon("trash.png");
            ImageIcon buildFree = new ImageIcon("free.png");
            g.drawImage(build.getImage(), 550, 440, 125, 100, null);
            g.drawImage(wonder.getImage(), 800, 440, 120, 100, null);
            g.drawImage(discard.getImage(), 1050, 440, 80, 100, null);
            if(age == 1 && ps[turn].hasFree1() || age == 2 && ps[turn].hasFree2() || age == 3 && ps[turn].hasFree3())
            	g.drawImage(buildFree.getImage(), 1300, 440, 100, 110, null);
            g.drawImage(new ImageIcon(currCard.getName()+".png").getImage(), 816, 600, 288, 440, null);
        }
        else if(isTradeScreen)
        {
        	g.drawImage(bg.getImage(), 0, 0, 1920, 1080, null);
            g.drawImage(logo.getImage(), 1695, -50, 225, 200, null);
            Player[] p = (Player[]) gs.get(0);
            int turn = (int) gs.get(2);
            ArrayList<String> missing = new ArrayList<>();
            if(currAction == 0)
            {
            	g.drawImage(new ImageIcon(currCard.getName() + ".png").getImage(), 888, 50, 144, 220, null);
            	missing = p[turn].missingRes(currCard);
            }
            else if(currAction == 1)
            {
            	g.drawImage(new ImageIcon(p[turn].getWonder().getName() + ".png").getImage(), 560, 50, null);
            	missing = p[turn].missingRes();
            }
            Collections.sort(missing);
            g.setColor(new Color(34, 161, 181));
            g.setFont(new Font("papyrus", Font.BOLD, 35));
            g.drawImage(new ImageIcon("coin.png").getImage(), 100, 90, 50, 50, null);
            g.drawString("x" + p[turn].getCoins(), 165, 125);
            g.drawString("You Need: ", 100, 175);
            for(int i = 0; i < missing.size(); i++)
            {
            	int st = i*55 + 100;
            	if(missing.get(i).equals("loom"))
            		missing.set(i, "linen");
            	else if(missing.get(i).equals("papyrus"))
            		missing.set(i, "paper");
            	g.drawImage(new ImageIcon(missing.get(0) + ".png").getImage(), st, 200, 50, 50, null);
            }
            g.drawImage(new ImageIcon("end.png").getImage(), 760, 800, 400, 170, null);
            Player left = p[(turn+2) % 3];
            Player right = p[(turn+1) % 3];
            ArrayList<String> leftRes = left.getWonder().getTradableRes();
            ArrayList<String> rightRes = right.getWonder().getTradableRes();
            g.drawString("Player " + ((turn+2) % 3 + 1) + "'s Resources", 100, 425);
            g.drawString("Player " + ((turn+1) % 3 + 1) + "'s Resources", 100, 575);
            for(int i = 0; i < leftRes.size(); i++)
            {
            	int stX = i*55 + 100;
            	String res = leftRes.get(i);
            	if(res.equals("loom"))
            		res = "linen";
            	else if(res.equals("papyrus"))
            		res = "paper";
            	g.drawImage(new ImageIcon(res + ".png").getImage(), stX, 450, 50, 50, null);
            }
            for(int i = 0; i < rightRes.size(); i++)
            {
            	int stX = i*55 + 100;
            	String res = rightRes.get(i);
            	if(res.equals("loom"))
            		res = "linen";
            	else if(res.equals("papyrus"))
            		res = "paper";
            	g.drawImage(new ImageIcon(res + ".png").getImage(), stX, 600, 50, 50, null);
            }
        }
        else if(isWinScreen)
        {
        	g.drawImage(bg.getImage(), 0, 0, 1920, 1080, null);
            g.drawImage(logo.getImage(), 1695, -50, 225, 200, null);
            ArrayList<Player> podium = (ArrayList<Player>) gs.get(5);
            g.setFont(new Font("papyrus", Font.BOLD, 100));
            g.setColor(new Color(222, 169, 22));
            g.drawString("1st: Player " + (podium.get(0).getTurn() + 1) + " - " + podium.get(0).getScore(), 560, 500);
            g.setColor(new Color(158, 158, 158));
            g.drawString("2nd: Player " + (podium.get(1).getTurn() + 1) + " - " + podium.get(1).getScore(), 560, 625);
            g.setColor(new Color(161, 97, 14));
            g.drawString("3rd: Player " + (podium.get(2).getTurn() + 1) + " - " + podium.get(2).getScore(), 560, 750);
        }
        else if(isHaliScreen)
        {
        	g.drawImage(bg.getImage(), 0, 0, 1920, 1080, null);
            g.drawImage(logo.getImage(), 1695, -50, 225, 200, null);
        	paintHaliEffect(g, 100, 100, ((Deck) gs.get(6)).getDiscard(1));
        	paintHaliEffect(g, 100, 350, ((Deck) gs.get(6)).getDiscard(2));
        	paintHaliEffect(g, 100, 600, ((Deck) gs.get(6)).getDiscard(3));
        }
    }
	public void paintHaliEffect(Graphics g, int startX, int startY, ArrayList<Card> pile)
	{
		for(Card c : pile)
		{
			g.drawImage(new ImageIcon(c.getName() + ".png").getImage(), startX, startY, 144, 220, null);
			startX += 155;
		}
	}
	public void paintCards(Graphics g, int startX, int startY, String color)
	{
		Player[] players = (Player[]) gs.get(0);
		TreeSet<Card> str = players[turn].getWonder().getStructure(color);
		if(color.equals("brown"))
			str.addAll(players[turn].getWonder().getStructure("silver"));
		for(Card c : str)
		{
			startX -= 30;
			startY -= 50;
		}
		for(Card c : str)
		{
			startX += 30;
			startY += 50;
			g.drawImage(new ImageIcon(c.getName() + ".png").getImage(), startX, startY, 144, 220, null);
		}
	}
	public int getHandSize() {
        return handSize;
    }
    public void setPlayCardScreen() {
        isPlayCardScreen = !isPlayCardScreen;
        isPlayScreen = !isPlayScreen;
    }
    public void setTradeScreen() {
        isTradeScreen = !isTradeScreen;
        isPlayScreen = !isPlayScreen;
    }
    public boolean isPlayCardScreen()
    {
    	return isPlayCardScreen;
    }
    public boolean isTradeScreen()
    {
    	return isTradeScreen;
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
	public void resetTurn()
	{
		this.turn = (int) gs.get(2);
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
	public boolean isWinScreen()
	{
		return isWinScreen;
	}
	public boolean isHaliScreen()
	{
		return isHaliScreen;
	}
	public void setHaliScreen()
	{
		isHaliScreen = !isHaliScreen;
        isPlayScreen = !isPlayScreen;
	}
	public void win()
	{
		isWinScreen = true;
		isPlayScreen = false;
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
	public void setCard(Card c)
	{
		currCard = c;
	}
	public void setAction(int a)
	{
		currAction = a;
	}
}
