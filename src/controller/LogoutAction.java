/* ////////////////////////////////////////////////////////////

File Name: LogoutAction.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.UserDAO;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't come much simpler than this.)
 */
public class LogoutAction extends Action
{
	private UserDAO userDAO;
	
	public LogoutAction(Model model) {	userDAO = model.getUserDAO();	}

	public String getName() { return "logout.do"; }

	public String perform(HttpServletRequest request)
	{
        HttpSession session = request.getSession(false);
        
        session.setAttribute("user",null);
        session.invalidate();

        try
        {
        	request.setAttribute("userList", userDAO.getUsers());
        }
        
        catch (RollbackException e)
        {
        	//errors.add(e.getMessage());
        	return "error.jsp";
        }
        
		request.setAttribute("message","You are now logged out");
        return "success.jsp";
    }
}
