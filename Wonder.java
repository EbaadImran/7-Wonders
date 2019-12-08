import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Wonder {
	private String[] attributes;
	private Card[] stages;
	private ArrayList<String> chooseRes;
	private ArrayList<String> allChooseRes;
	private ArrayList<String> removeRes;
	private HashMap<String, Integer> usableRes;
	private ArrayList<String> tradableRes;
	private ArrayList<String> allRes;
	private HashMap<String, TreeSet<Card>> structures;
	
	public Wonder(String att) {
		//name|res|cost1|cost2|cost3|stage1|stage2|stage3
		attributes = att.split(",");
		stages = new Card[3];
		usableRes = new HashMap<>();
		tradableRes = new ArrayList<>();
		allRes = new ArrayList<>();
		removeRes = new ArrayList<>();
		chooseRes = new ArrayList<>();
		allChooseRes = new ArrayList<>();
		structures = new HashMap<>();
		
		structures.put("brown", new TreeSet<>());
		structures.put("silver", new TreeSet<>());
		structures.put("blue", new TreeSet<>());
		structures.put("gold", new TreeSet<>());
		structures.put("red", new TreeSet<>());
		structures.put("green", new TreeSet<>());
		structures.put("purple", new TreeSet<>());
		
		usableRes.put("wood", 0);
		usableRes.put("stone", 0);
		usableRes.put("ore", 0);
		usableRes.put("clay", 0);
		usableRes.put("loom", 0);
		usableRes.put("glass", 0);
		usableRes.put("papyrus", 0);
		
		usableRes.put(attributes[1], 1);
		tradableRes.add(attributes[1]);
		allRes.add(attributes[1]);
	}
	public String getName()
	{
		return attributes[0];
	}
	public void addUsable(String res)
	{
		usableRes.put(res, usableRes.get(res) + 1);
	}
	public void addTradable(String res)
	{
		tradableRes.add(res);
		allRes.add(res);
	}
	public boolean hasTradableRes(String res)
	{
		return tradableRes.contains(res);
	}
	public void addChoose(String choice)
	{
		chooseRes.add(choice);
		allChooseRes.add(choice);
	}
	public int getUsableRes(String res)
	{
		return usableRes.get(res);
	}
	public int getTradableAmt(String res)
	{
		int cnt = 0;
		for(String k : tradableRes)
			if(k.equals(res))
				cnt++;
		return cnt;
	}
	public HashMap<String, Integer> allUsableRes()
	{
		return usableRes;
	}
	public void addTradeResource(String res)
	{
		usableRes.put(res, usableRes.get(res) + 1);
		removeRes.add(res);
	}
	public void removeRes(int i, int choice)
	{
		String[] temp = chooseRes.remove(i).split("/");
		usableRes.put(temp[choice], usableRes.get(temp[choice]) + 1);
		removeRes.add(temp[choice]);
	}
	public void resetUsable()
	{
		while(!removeRes.isEmpty())
		{
			String temp = removeRes.remove(0);
			usableRes.put(temp, usableRes.get(temp) - 1);
		}
		for(String k : allChooseRes)
			chooseRes.add(k);
	}
	public void removeTrade(String res)
	{
		tradableRes.remove(tradableRes.indexOf(res));
	}
	public void resetTradable()
	{
		tradableRes.clear();
		for(String k : allRes)
			tradableRes.add(k);
	}
	public ArrayList<String> getChoose()
	{
		return chooseRes;
	}
	public ArrayList<String> getRemoveRes()
	{
		return removeRes;
	}
	public ArrayList<String> getTradableRes()
	{
		return tradableRes;
	}
	public String getCost1()
	{
		return attributes[2];
	}
	public String getCost2()
	{
		return attributes[3];
	}
	public String getCost3()
	{
		return attributes[4];
	}
	public String getStage1()
	{
		return attributes[5];
	}
	public String getStage2()
	{
		return attributes[6];
	}
	public String getStage3()
	{
		return attributes[7];
	}
	public TreeSet<Card> getStructure(String color)
	{
		return structures.get(color);
	}
	public Card[] getStages()
	{
		return stages;
	}
	public boolean hasStructure(String oth)
	{
		for(String k : structures.keySet())
			for(Card c : structures.get(k))
				if(c.getName() == oth)
					return true;
		return false;
	}
	public void activate(Card oth)
	{
		for(int i=0;i<stages.length;i++)
		{
			if(stages[i] == null)
			{
				stages[i] = oth;
				return;
			}
		}
	}
	public void build(Card c) {
		//ebaads mom btw LOL!
		TreeSet<Card> yourmom = structures.get(c.getColor());
		yourmom.add(c);
	}
	public String toString()
	{
		String temp = getName() + "\n";
		temp += "Brown Cards: " + getStructure("brown") + "\n";
		temp += "Silver Cards: " + getStructure("silver") + "\n";
		temp += "Blue Cards: " + getStructure("blue") + "\n";
		temp += "Gold Cards: " + getStructure("gold") + "\n";
		temp += "Red Cards: " + getStructure("red") + "\n";
		temp += "Green Cards: " + getStructure("green") + "\n";
		temp += "Purple Cards: " + getStructure("purple") + "\n";
		temp += "Usable Resources: " + usableRes + "\n";
		temp += "Tradable Resources" + tradableRes + "\n";
		return temp;
	}
	public HashMap<String, TreeSet<Card>> getStruc(){
		return structures;
	}
}
