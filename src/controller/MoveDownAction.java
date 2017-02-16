/* ////////////////////////////////////////////////////////////

File Name: MoveDownAction.java
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
import formbeans.IdForm;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

/*
 * Move a favorite up in the list.  Given an "id" parameter.
 * The DAO checks to see that id is valid number for a favorite and that
 * the logged user is the owner.
 * 
 * Sets up the "userList" and "favorites" request attributes
 * and if successful, forwards back to to "manage.jsp".
 */
public class MoveDownAction extends Action
{
	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

    public MoveDownAction(Model model)
    {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

    public String getName() { return "move-down.do"; }

    public String perform(HttpServletRequest request)
    {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try
		{
            // Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());

			IdForm form = formBeanFactory.create(request);

			User user = (User) request.getSession().getAttribute("user");

			int id = form.getIdAsInt();
	    	favoriteDAO.moveDown(id, user.getUserId());

    		// Be sure to get the favorites after the delete
        	Favorite[] favorites = favoriteDAO.getUserFavorites(user.getUserId());
	        request.setAttribute("favorites", favorites);

	        return "manage.jsp";
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
