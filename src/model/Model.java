/* ////////////////////////////////////////////////////////////

File Name: Model.java
Copyright (c) 2016 Anchit Sood (sood.anchit@gmail.com).  All rights reserved.


Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, 
   this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, 
   this list of conditions and the following disclaimer in the documentation 
   and/or other materials provided with the distribution.


This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

//////////////////////////////////////////////////////////// */



package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import databeans.User;
import databeans.Favorite;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

public class Model
{
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

	public Model(ServletConfig config) throws ServletException
	{
		try
		{
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			userDAO  = new UserDAO("anchits_user", pool);
			favoriteDAO = new FavoriteDAO("anchits_favorite", pool);
			
			if (userDAO.getUsers().length < 3)
	        {
				populate();
	        }
		}
		
		catch (DAOException e)
		{
			throw new ServletException(e);
		}
		
		catch (RollbackException e)
		{
			// dummy
		}
	}
	
	public FavoriteDAO getFavoriteDAO() { return favoriteDAO; }
	public UserDAO  getUserDAO()		{ return userDAO;  }
	
	private void populate()
	{
		try
        {
        	User user = new User();
            user.setEmail("ironman@avengers.com");
            user.setFirstName("Tony");
            user.setLastName("Stark");
            user.encodePassword("pass");
            
            if (userDAO.read("ironman@avengers.com") == null)
        	userDAO.create(user);
            
            
            user.setEmail("spiderman@avengers.com");
            user.setFirstName("Peter");
            user.setLastName("Parker");
            user.encodePassword("pass");
            
            if (userDAO.read("spiderman@avengers.com") == null)
        	userDAO.create(user);
            
            
            user.setEmail("captainamerica@avengers.com");
            user.setFirstName("Steve");
            user.setLastName("Rogers");
            user.encodePassword("pass");
            
            if (userDAO.read("captainamerica@avengers.com") == null)
        	userDAO.create(user);
            
            int usr1 = userDAO.read("ironman@avengers.com").getUserId();
            int usr2 = userDAO.read("spiderman@avengers.com").getUserId();
            int usr3 = userDAO.read("captainamerica@avengers.com").getUserId();
            
            
            
            
            Favorite favorite = new Favorite();
            favorite.setUserId(usr1);
            favorite.setClicks(0);
            favorite.setUri("http://marvel.com/characters/29/iron_man");
            favorite.setComment("All about me!!!!!");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("https://www.youtube.com/watch?v=DTqa-NEwUbs");
            favorite.setComment("I'm the best...");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("https://en.wikipedia.org/wiki/Iron_Man");
            favorite.setComment("Facts time");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("https://www.facebook.com/ironman/");
            favorite.setComment("See! I'm everywhere!");
            favoriteDAO.create(favorite);
            
            
            
            
            
            favorite.setUserId(usr2);
            favorite.setUri("http://marvel.com/characters/54/spider-man");
            favorite.setComment("I look cool!");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("https://www.youtube.com/watch?v=7YlxDLIQWdI");
            favorite.setComment("Science wins");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("https://en.wikipedia.org/wiki/Spider-Man");
            favorite.setComment("Wow they know a lot about me, need to go undercover...");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("https://www.google.com/search?q=spiderman");
            favorite.setComment("Google knows about me!");
            favoriteDAO.create(favorite);


            
            
            
            favorite.setUserId(usr3);
            favorite.setUri("http://marvel.com/characters/8/captain_america");
            favorite.setComment("I need some new costume designs");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("http://comicvine.gamespot.com/vibranium/4055-40919/");
            favorite.setComment("All about vibranium");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("https://en.wikipedia.org/wiki/Captain_America");
            favorite.setComment("For justice and freedom!");
            favoriteDAO.create(favorite);
            
            
            favorite.setUri("http://marvel.com/captainamerica");
            favorite.setComment("Join the fight, and we will win together!");
            favoriteDAO.create(favorite);            
        }
		
        catch (RollbackException e)
        {
        	//nothing
        }
	}
}
