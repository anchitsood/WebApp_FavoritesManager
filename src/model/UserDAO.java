/* ////////////////////////////////////////////////////////////

File Name: UserDAO.java
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

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.User;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

public class UserDAO extends GenericDAO<User>
{

    public UserDAO(String tableName, ConnectionPool pool) throws DAOException
    {
        super(User.class, tableName, pool);
    }

    public User read(String email) throws RollbackException
    {
    
        User user = null;
        User[] users = match(MatchArg.equals("email", email));
        
        if (users.length == 0)
        {
            user = null;
        }
        
        else
        {
            user = users[0];
        }
        
        return user;        
    }
    
    public User[] getUsers() throws RollbackException
    {
        User[] users = match();
        Arrays.sort(users); // We want them sorted by last and first names (as per User.compareTo());
        return users;
    }

    public User setPassword(String email, String password) throws RollbackException
    {
        try
        {
            Transaction.begin();
            User user = read(email);

            if (user == null)
            {
                throw new RollbackException("User " + email + " no longer exists");
            }

            user.encodePassword(password);

            update(user);
            Transaction.commit();
            
            return user;
        }
        
        finally
        {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
}
