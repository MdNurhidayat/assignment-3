package application;

import java.util.HashMap;

public class SecurityManager 
{
	private HashMap<String, User> studentMap;
	private HashMap<String, User> staffMap;
	
	
	
	public Boolean insertUser(User user)
	{
		return true;
	}
	
	public Boolean logInAuthentication(String userID, String password)
	{
		
		return true;
	}
}
