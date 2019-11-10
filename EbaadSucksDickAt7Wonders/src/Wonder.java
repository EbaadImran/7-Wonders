import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Wonder {
	private String[] attributes;
	private Card[] stages;
	private HashMap<String, Integer> usableRes;
	private HashSet<String> tradableRes;
	private HashMap<String, TreeSet<Card>> structures;
	public Wonder(String att) {
		//name|res|cost1|cost2|cost3|stage1|stage2|stage3
		attributes = att.split("|");
		stages = new Card[3];
		usableRes = new HashMap<>();
		tradableRes = new HashSet<>();
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
}