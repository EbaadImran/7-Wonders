import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Wonder {
	private String[] attributes;
	private Card[] stages;
	private HashMap<String, Integer> usableRes;
	private HashSet<String> tradableRes;
	private HashMap<String, HashSet<Card>> structures;
	public Wonder(String att) {
		//name|res|cost1|cost2|cost3|stage1|stage2|stage3
		attributes = att.split("|");
		stages = new Card[3];
		usableRes = new HashMap<>();
		tradableRes = new HashSet<>();
		structures = new HashMap<>();
		
		structures.put("brown", new HashSet<>());
		structures.put("silver", new HashSet<>());
		structures.put("blue", new HashSet<>());
		structures.put("gold", new HashSet<>());
		structures.put("red", new HashSet<>());
		structures.put("green", new HashSet<>());
		structures.put("purple", new HashSet<>());
		
		usableRes.put("wood", 0);
		usableRes.put("stone", 0);
		usableRes.put("ore", 0);
		usableRes.put("clay", 0);
		usableRes.put("loom", 0);
		usableRes.put("glass", 0);
		usableRes.put("papyrus", 0);
		
		usableRes.put(attributes[1], 1);
		tradableRes.add(attributes[1]);
	}
	public void addUsable(String res)
	{
		usableRes.put(res, usableRes.get(res));
	}
	public void addTradable(String res)
	{
		tradableRes.add(res);
	}
	public int getUsableRes(String res)
	{
		return usableRes.get(res);
	}
	public Set<String> getTradableRes()
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
	public HashSet<Card> getStructure(String color)
	{
		return structures.get(color);
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
		HashSet<Card> yourmom = structures.get(c.getColor());
		yourmom.add(c);
	}
}
