/* ////////////////////////////////////////////////////////////

File Name: ChangePwdAction.java
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
import formbeans.ChangePwdForm;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

public class ChangePwdAction extends Action
{
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);

	private UserDAO userDAO;

	public ChangePwdAction(Model model)
	{
		userDAO = model.getUserDAO();
	}

	public String getName()
	{
		return "change-pwd.do";
	}

	public String perform(HttpServletRequest request)
	{
		// Set up error list
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try
		{
			// Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());

			// Load the form parameters into a form bean
			ChangePwdForm form = formBeanFactory.create(request);

			// If no params were passed, return with no errors so that the form will be presented (we assume for the first time).
			if (!form.isPresent())
			{
				return "change-pwd.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0)
			{
				return "change-pwd.jsp";
			}
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			// Change the password
			User updatedUser = userDAO.setPassword(user.getEmail(), form.getNewPassword());
			
			session.setAttribute("user", updatedUser);
			request.setAttribute("message", "Password changed for " + user.getEmail());
			return "success.jsp";
		}
		
		catch (RollbackException e)
		{
			errors.add(e.toString());
			return "error.jsp";
		}
		
		catch (FormBeanException e)
		{
			errors.add(e.toString());
			return "error.jsp";
		}
	}
}
