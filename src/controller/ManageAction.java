/* ////////////////////////////////////////////////////////////

File Name: ManageAction.java
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


package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.FavoriteDAO;
import model.UserDAO;

import org.genericdao.RollbackException;

import databeans.Favorite;
import databeans.User;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

/*
 * Sets up the request attributes for manage.jsp.
 * This is the way to enter "Manage Your Favorites"
 * from someplace else in the site.
 * 
 * Sets the "userList" request attribute in order to display
 * the list of users on the navbar.
 * 
 * Sets the "favorites" request attribute in order to display
 * the list of user's favorites for management.
 * 
 * Forwards to manage.jsp.
 */
public class ManageAction extends Action
{
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

	public ManageAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

	public String getName() { return "manage.do"; }

	public String perform(HttpServletRequest request)
	{
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        
		try
		{
            // Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());

			User user = (User) request.getSession(false).getAttribute("user");
        	Favorite[] favorites = favoriteDAO.getUserFavorites(user.getUserId());
	        request.setAttribute("favorites", favorites);

	        return "manage.jsp";
        }
		
		catch (RollbackException e)
		{
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
