public class Card implements Comparable
{
	private int age;
	private String color;
	private String effect;
	private String name;
	private String cost;
	private String free;
	private String chain1;
	private String chain2;
	
	public Card(String att)
	{
		String[] attArr = att.split("|");
		
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
	public int getAge() {return age;}
	public String getColor() {return color;}
	public String getEffect() {return effect;}
	public String getName() {return name;}
	public String getCost() {return cost;}
	public String getFree() {return free;}
	public String getChain1() {return chain1;}
	public String getChain2() {return chain2;}
}
