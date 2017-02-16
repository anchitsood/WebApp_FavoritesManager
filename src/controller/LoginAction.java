/* ////////////////////////////////////////////////////////////

File Name: LoginAction.java
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
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.User;
import formbeans.LoginForm;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to manage.do to allow 
 * the user to manage his favorites.
 */
public class LoginAction extends Action
{
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	
	private UserDAO userDAO;

	public LoginAction(Model model)
	{
		userDAO = model.getUserDAO();
	}

	public String getName() { return "login.do"; }
    
    public String perform(HttpServletRequest request)
    {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try
        {
	    	LoginForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);

	        request.setAttribute("userList", userDAO.getUsers());
	        
	        // If no params were passed, return with no errors so that the form will be presented (we assume for the first time).
	        if (!form.isPresent())
	        {
	            return "login.jsp";
	        }

	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0)
	        {
	            return "login.jsp";
	        }

	        // Look up the user
	        User user = userDAO.read(form.getEmail());
	        
	        if (user == null)
	        {
	            errors.add("User Name not found");
	            return "login.jsp";
	        }

	        // Check the password
	        if (!user.checkPassword(form.getPassword()))
	        {
	            errors.add("Incorrect password");
	            return "login.jsp";
	        }
	
	        // Attach (this copy of) the user bean to the session
	        HttpSession session = request.getSession();
	        session.setAttribute("user",user);

	        return "manage.do";
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
