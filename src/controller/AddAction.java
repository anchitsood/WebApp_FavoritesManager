/* ////////////////////////////////////////////////////////////

File Name: AddAction.java
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
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.FavoriteDAO;
import model.UserDAO;

import org.genericdao.RollbackException;
//import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Favorite;
import databeans.User;
import formbeans.AddFavoriteForm;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

/*
 * Adds a favorite from the user.  If successful, sets the "userList"
 * and "favorites" request attributes, creates a new Favorite bean with the
 * image, and forward (back) to manage.jsp.
 */
public class AddAction extends Action
{
    private FormBeanFactory<AddFavoriteForm> formBeanFactory = FormBeanFactory.getInstance(AddFavoriteForm.class);

    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;

    public AddAction(Model model)
    {
        favoriteDAO = model.getFavoriteDAO();
        userDAO = model.getUserDAO();
    }

    public String getName()
    {
        return "add.do";
    }

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

            AddFavoriteForm form = formBeanFactory.create(request);
            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0)
                return "error.jsp";

            
            Favorite favorite = new Favorite();
            favorite.setUri(form.getUri());
            favorite.setComment(form.getComment());
            favorite.setClicks(0);
            favorite.setUserId(user.getUserId());
            
            favoriteDAO.create(favorite);
            

            // Update favorites (there's now one more on the list)
            Favorite[] newFavorites = favoriteDAO.getUserFavorites(user.getUserId());
            request.setAttribute("favorites", newFavorites);
            return "manage.jsp";
        }
        
        catch (RollbackException e)
        {
            errors.add(e.getMessage());
            return "manage.jsp";
        }
        
        catch (FormBeanException e)
        {
            errors.add(e.getMessage());
            return "manage.jsp";
        }
    }
}
