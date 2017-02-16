/* ////////////////////////////////////////////////////////////

File Name: ListAction.java
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
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Favorite;
import databeans.User;
import formbeans.UserForm;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

/*
 * Looks up the favorites for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "favorites" request attribute in order to display
 *       the list of given user's favorites for selection.
 *   (3) Forwards to list.jsp.
 */
public class ListAction extends Action
{
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory.getInstance(UserForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public ListAction(Model model)
	{
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName()
	{
		return "list.do";
	}

	public String perform(HttpServletRequest request)
	{
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try
		{
			// Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());

			UserForm form = formBeanFactory.create(request);

			String userName = form.getUserName();
			if (userName == null || userName.length() == 0)
			{
				errors.add("User must be specified");
				return "error.jsp";
			}

			// Set up favorites list
			User user = userDAO.read(userName);
			if (user == null)
			{
				errors.add("Invalid User: " + userName);
				return "error.jsp";
			}

			Favorite[] favorites = favoriteDAO.getUserFavorites(user.getUserId());
			request.setAttribute("favorites", favorites);
			
			return "list.jsp";
		}
		
		catch (RollbackException e)
		{
			errors.add(e.getMessage());
			return "error.jsp";
		}
		
		catch (FormBeanException e)
		{
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
