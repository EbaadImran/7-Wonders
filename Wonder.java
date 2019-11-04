import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player 
{
	int turn;
	Wonder w;
	int coins;
	ArrayList<Card> hand;
	HashMap<Integer, Integer> points;
	HashMap<String, Set<String>> oneCostRes;
	int mPower;
	HashMap<String, Integer> sci;
	public Player(String att, int t)
	{
		turn = t;
		w = new Wonder(att);
		hand = new ArrayList<>();
		coins = 3;
		oneCostRes = new HashMap<>();
		points = new HashMap<>();
		mPower = 0;
		sci = new HashMap<>();
		
		sci.put("sci1", 0);
		sci.put("sci2", 0);
		sci.put("sci3", 0);
		
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
	public int getColorAmt(String color)
	{
		return w.getStructure(color).size();
	}
	public int getNegs()
	{
		return points.get(-1);
	}
	public int[] getScore()
	{
		int[] total = new int[7];
		//military
		total[0] += (-1*points.get(-1)) + (points.get(1)) + (3*points.get(3)) + (5*points.get(5));
		//coins
		total[1] += coins/3;
		//wonder
		Card[] cards = w.getStages();
		total[2] += (cards[0] != null) ? 3:0;
		total[2] += (cards[2] != null) ? 7:0;
		//civic
		for(Card c : w.getStructure("blue"))
			total[3] += Integer.parseInt(c.getEffect());
		//commercial
		
		//guilds
		
		//science
		total[6] += 7 * (Math.min(sci.get("sci1"), Math.min(sci.get("sci2"), sci.get("sci3"))));
		total[6] += Math.pow(sci.get("sci1"), 2) + Math.pow(sci.get("sci2"), 2) + Math.pow(sci.get("sci3"), 2);
		return total;
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
