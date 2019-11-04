import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player {
	Wonder w;
	int coins;
	ArrayList<Card> hand;
	HashMap<Integer, Integer> points;
	HashMap<String, Set<String>> oneCostRes;
	public Player(String att)
	{
		w = new Wonder(att);
		hand = new ArrayList<>();
		coins = 3;
		oneCostRes = new HashMap<>();
		points = new HashMap<>();
		
		points.put(-1, 0);
		points.put(1, 0);
		points.put(3, 0);
		points.put(5, 0);
	}
	public void addCoins(int i)
	{
		coins = coins + i;
	}
	public void addPoints(int name, int num)
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
	public HashMap<Integer, Integer> getPoints()
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
		if(w.hasStructure(oth.getName()))
			return false;
		
		boolean byCost = true;
		for(String k : oth.getCost().keySet())
			if(w.getUsableRes(k) < oth.getCost().get(k))
				byCost = false;
		if(byCost)
			return true;
		
		if(w.hasStructure(oth.getFree()))
			return true;
		
		return false;
	}
	public void setHand(ArrayList<Card> eshaimran)
	{
		hand = eshaimran;
	}
}
