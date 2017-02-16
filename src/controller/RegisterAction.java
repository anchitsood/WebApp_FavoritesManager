/* ////////////////////////////////////////////////////////////

File Name: RegisterAction.java
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
import formbeans.RegisterForm;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

/*
 * Processes the parameters from the form in register.jsp.
 * If successful:
 *   (1) creates a new User bean
 *   (2) sets the "user" session attribute to the new User bean
 *   (3) redirects to manage.do to allow the user to add some
 * favorites.
 */
public class RegisterAction extends Action
{
	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);

	private UserDAO userDAO;
	
	public RegisterAction(Model model)
	{
		userDAO = model.getUserDAO();
	}

	public String getName() { return "register.do"; }

    public String perform(HttpServletRequest request)
    {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try
        {
	        RegisterForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	
	        request.setAttribute("userList", userDAO.getUsers());
	        
	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent())
	        {
	            return "register.jsp";
	        }
	
	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0)
	        {
	            return "register.jsp";
	        }
	
	        
	        User user;
	        user = userDAO.read(form.getEmail());
            
            if (user!=null)
            {
                errors.add("This email address already exists! Please login via the login link; contact admin if you lost your password");
                return "register.jsp";
            }
	        
	        
	        // Create the user bean
	        user = new User();
	        user.setEmail(form.getEmail());
	        user.setFirstName(form.getFirstName());
	        user.setLastName(form.getLastName());
	        user.encodePassword(form.getPassword());
        	userDAO.create(user);
        
			// Attach (this copy of) the user bean to the session
	        HttpSession session = request.getSession(false);
	        session.setAttribute("user",user);
	        
			return "manage.do";
        }
        
        catch (RollbackException e)
        {
        	errors.add(e.getMessage());
        	return "register.jsp";
        }
        
        catch (FormBeanException e)
        {
        	errors.add(e.getMessage());
        	return "register.jsp";
        }
    }
}
