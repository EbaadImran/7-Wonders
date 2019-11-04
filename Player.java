import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player {
	Wonder w;
	int coins;
	ArrayList<Card> hand;
	Map<String, Integer> points;
	Map<String, Set<String>> oneCostRes;
	public Player(String att)
	{
		w = new Wonder(att);
		hand = new ArrayList<>();
		coins = 3;
		oneCostRes = new HashMap<>();
	}
	public void addCoins(int i)
	{
		coins = coins + i;
	}
	public void addPoints(String name, int num)
	{
		//https://www.youtube.com/watch?v=b2qsRX4B_Kk pls watch LOL!!!!!!
		int epic = points.get(name);
		points.put(name, epic + num);
	}
	public int getCoins()
	{
		return coins;
	}
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	public Map<String, Integer> getPoints()
	{
		return points;
	}
	public int getScore()
	{
		return 0;
	}
	public Wonder getWonder()
	{
		return w;
	}
	public void playCard(int joshleisajoke)
	{
		//Josh Le got Iss for going to In-and-Out, he basically is the biggest monkey alive ginga gunga ginga gunga
		w.build(hand.get(joshleisajoke));
	}
	public boolean playable(Card oth)
	{
		//he's so big LOL
		String bigboberts = oth.getName();
		Set<String> keys = w.getStructureKeys();
		for(String colors:keys)
		{
			for(Card c:w.getStructure(colors))
			{
				if(c.getName().equals("forum"))
					if(c.getFree().contains(oth.getName()))
						return true;
				else if(c.getFree().equals(bigboberts))
					return true;
				else if(c.getName().equals(bigboberts))
					return false;
			}
		}
		//still need to write if player has enough resources
	}
	public void setHand(ArrayList<Card> eshaimran)
	{
		hand = eshaimran;
	}
}
