import java.util.HashMap;

public class Card implements Comparable
{
	private int age;
	private String color;
	private String effect;
	private String name;
	private HashMap<String, Integer> cost;
	private String free;
	private String chain1;
	private String chain2;
	
	public Card(String att)
	{
		String[] attArr = att.split(",");
		//System.out.println(att);
		age = Integer.parseInt(attArr[0]);
		color = attArr[1];
		name = attArr[2];
		effect = attArr[3];
		cost = new HashMap<>();
		free = attArr[5];
		chain1 = attArr[6];
		chain2 = attArr[7];
		
		String[] costs = attArr[4].split(" ");
		for(int i = 0; i < costs.length; i++)
		{
			if(!costs[i].equals("-"))
			{
				this.cost.putIfAbsent(costs[i], 0);
				this.cost.put(costs[i], this.cost.get(costs[i])+1);
			}
		}
	}
	public int compareTo(Object oth)
	{
		Card temp = (Card) oth;
		if(color.equals(temp.color) && age == temp.age)
			return name.compareTo(temp.name);
		else if(color.equals(temp.color))
			return age - temp.age;
		return color.compareTo(temp.color);
	}
	public boolean equals(Object oth) 
	{
		Card temp = (Card) oth;
		return name.equals(temp.name);
	}
	public int getAge() {return age;}
	public String getColor() {return color;}
	public String getEffect() {return effect;}
	public String getName() {return name;}
	public HashMap<String, Integer> getCost() {return cost;}
	public String getFree() {return free;}
	public String getChain1() {return chain1;}
	public String getChain2() {return chain2;}
	public String toString() {return name;}
}
