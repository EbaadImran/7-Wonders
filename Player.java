import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Player 
{
	private int turn;
	private Wonder w;
	private int coins;
	private ArrayList<Card> hand;
	private HashMap<Integer, Integer> points;
	private HashMap<Integer, HashSet<String>> oneCostRes;
	private int mPower;
	private HashMap<String, Integer> sci;
	private int chooseScience;
	private boolean hasFree;
	private boolean hasFreeDisc;
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
		chooseScience = 0;
		hasFree = false;
		hasFreeDisc = false;
		
		sci.put("sci1", 0);
		sci.put("sci2", 0);
		sci.put("sci3", 0);
		
		oneCostRes.put((turn+1) % 3, new HashSet<String>()); //right
		oneCostRes.put((turn+2) % 3, new HashSet<String>()); //left
		
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
	public Card getCard(int i)
	{
		return hand.get(i);
	}
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	public HashMap<Integer, HashSet<String>> getOneCost()
	{
		return oneCostRes;
	}
	public HashMap<Integer, Integer> getPoints()
	{
		return points;
	}
	public int getWonderAmt()
	{
		Card[] temp = w.getStages();
		if(temp[2] != null)
			return 4;
		else if(temp[1] != null)
			return 3;
		else if(temp[0] != null)
			return 2;
		return 1;
	}
	public int getPrice(int player, String res)
	{
		return (oneCostRes.get(player).contains(res)) ? 1 : 2;
	}
	public int getColorAmt(String color)
	{
		return w.getStructure(color).size();
	}
	public int getNegs()
	{
		return points.get(-1);
	}
	public boolean[] getPosActions(Card oth)
	{
		return new boolean[]{playable(oth), activatable()};
	}
	public int[] getScore(Player[] players)
	{
		int[] total = new int[7];
		int left = (turn + 2) % 3;
		int right = (turn + 1) % 3;
		for(int i = 0; i < 7; i++)
			total[i] = 0;
		//military
		total[0] += (-1*points.get(-1)) + (points.get(1)) + (3*points.get(3)) + (5*points.get(5));
		//coins
		total[1] += coins/3;
		//wonder
		Card[] cards = w.getStages();
		total[2] += (cards[0] != null) ? 3:0;
		total[2] += (cards[1] != null && w.getName().equals("gizahA")) ? 5:0;
		total[2] += (cards[2] != null) ? 7:0;
		//civic
		for(Card c : w.getStructure("blue"))
			total[3] += Integer.parseInt(c.getEffect());
		//commercial
		for(Card c : w.getStructure("gold"))
		{
			String[] parts = c.getEffect().split(" ");
			if(parts[0].equals("self"))
			{
				if(parts[1].equals("wonder"))
					total[4] += getWonderAmt();
				else if(parts[1].equals("brown"))
					total[4] += getColorAmt("brown");
				else if(parts[1].equals("silver"))
					total[4] += getColorAmt("silver")*2;
				else if(parts[1].equals("gold"))
					total[4] += getColorAmt("gold");
			}
		}
		//guilds
		for(Card c : w.getStructure("purple"))
		{
			String[] parts = c.getEffect().split(" ");
			if(parts[0].equals("both"))
			{
				if(parts[1].equals("red"))
					total[5] += players[left].getColorAmt("red") + players[right].getColorAmt("red");
				else if(parts[1].equals("blue"))
					total[5] += players[left].getColorAmt("blue") + players[right].getColorAmt("blue");
				else if(parts[1].equals("brown"))
					total[5] += players[left].getColorAmt("brown") + players[right].getColorAmt("brown");
				else if(parts[1].equals("silver"))
					total[5] += (players[left].getColorAmt("silver") + players[right].getColorAmt("silver"))*2;
				else if(parts[1].equals("gold"))
					total[5] += players[left].getColorAmt("gold") + players[right].getColorAmt("gold");
				else if(parts[1].equals("green"))
					total[5] += players[left].getColorAmt("green") + players[right].getColorAmt("green");
				else if(parts[1].equals("-1"))
					total[5] += players[left].getNegs() + players[right].getNegs();
			}
			else if(parts[0].equals("all"))
			{
				total[5] += players[0].getWonderAmt() + players[1].getWonderAmt() + players[2].getWonderAmt();
			}
			else if(parts[0].equals("self"))
			{
				total[5] += getColorAmt("brown") + getColorAmt("silver") + getColorAmt("purple");
			}
		}
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
		Card thing = hand.remove(joshleisajoke);
		w.build(thing);
		if(thing.getCost().containsKey("coin"))
			coins--;
		doEffect(thing);
	}
	public void doEffect(Card thing)
	{
		if(thing.getColor().equals("red"))
			mPower += Integer.parseInt(thing.getEffect());
		else if(thing.getColor().equals("green"))
			sci.put(thing.getEffect(), sci.get(thing.getEffect()) + 1);
		else if(thing.getColor().equals("silver"))
		{
			w.addTradable(thing.getEffect());
			w.addUsable(thing.getEffect());
		}
		else if(thing.getColor().equals("brown"))
		{
			String[] res = thing.getEffect().split(" ");
			for(int i = 0; i < res.length; i++)
			{
				if(res[i].contains("/"))
				{
					w.addChoose(res[i]);
					w.addTradable(res[i].substring(0, res[i].indexOf("/")));
					w.addTradable(res[i].substring(res[i].indexOf("/")+1));
				}
				else
				{
					w.addUsable(res[i]);
					w.addTradable(res[i]);
				}
			}
		}
		else if(thing.getColor().equals("gold"))
		{
			String[] eff = thing.getEffect().split(" ");
			if(eff[0].equals("left"))
				for(int i = 1; i < eff.length; i++)
					oneCostRes.get((turn + 2) % 3).add(eff[i]);
			else if(eff[0].equals("right"))
				for(int i = 1; i < eff.length; i++)
					oneCostRes.get((turn + 1) % 3).add(eff[i]);
			else if(eff[0].equals("both"))
			{
				for(int i = 1; i < eff.length; i++)
				{
					oneCostRes.get((turn + 1) % 3).add(eff[i]);
					oneCostRes.get((turn + 2) % 3).add(eff[i]);
				}
			}
		}
	}
	public void doWonderEffect()
	{
		String temp[] = w.getStage2().split(" ");
		if(temp[0].equals("m"))
			mPower += temp.length;
		else if(temp[0].equals("clay/ore/wood/stone"))
			w.addChoose("clay/ore/wood/stone");
		else if(temp[0].equals("sci1/sci2/sci3"))
			chooseScience++;
		else if(temp[0].equals("coin"))
			coins += temp.length;
		else if(temp[0].equals("freedisc"))
			hasFreeDisc = true;
		else if(temp[0].equals("free"))
			hasFree = true;
	}
	public void buildWonder(int i)
	{
		w.activate(hand.remove(i));
	}
	public Card discard(int i)
	{
		coins += 3;
		return hand.remove(i);
	}
	public boolean playable(Card oth)
	{
		System.out.println(oth.getCost());
		
		if(w.hasStructure(oth.getName()))
			return false;
		
		if(oth.getCost().get("coin") != null)
		{
			if(coins >= 1)
				return true;
		}
		else
		{
			boolean byCost = true;
			for(String k : oth.getCost().keySet())
				if(w.getUsableRes(k) < oth.getCost().get(k))
				byCost = false;
			if(byCost)
				return true;
		}
		if(w.hasStructure(oth.getFree()))
			return true;
		
		return false;
	}
	public boolean activatable()
	{
		String[] arr;
		int stage = getWonderAmt();
		if(stage == 1)
			arr = w.getCost1().split(" ");
		else if(stage == 2)
			arr = w.getCost2().split(" ");
		else if(stage == 3)
			arr = w.getCost3().split(" ");
		else
			return false;
		String res = arr[0];
		int amt = arr.length;
		if(w.getUsableRes(res) >= amt)
			return true;
		return false;
	}
	public void setHand(ArrayList<Card> eshaimran)
	{
		hand = eshaimran;
	}
	public String toString()
	{
		String temp = "Hand: " + hand + "\n";
		temp += "Coins: " + coins + "\n";
		temp += "M -1: " + points.get(-1) + "\n";
		temp += "M 1: " + points.get(1) + "\n";
		temp += "M 3: " + points.get(3) + "\n";
		temp += "M 5: " + points.get(5) + "\n";
		return temp;
	}
}
