/* ////////////////////////////////////////////////////////////

File Name: FavoriteDAO.java
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

import databeans.Favorite;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

public class FavoriteDAO extends GenericDAO<Favorite>
{
    public FavoriteDAO(String tableName, ConnectionPool pool) throws DAOException
    {
        super(Favorite.class, tableName, pool);
    }

    public void create(Favorite newFavorite) throws RollbackException
    {
        try
        {
            Transaction.begin();

            Favorite[] oldList = match(MatchArg.equals("userId", newFavorite.getUserId()));
            int maxPos = 0;
            for (Favorite p : oldList)
            {
                if (p.getPosition() > maxPos)
                {
                    maxPos = p.getPosition();
                }
            }

            newFavorite.setPosition(maxPos + 1);
            
            super.create(newFavorite);
            Transaction.commit();
        }
        
        finally
        {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    public void delete(int favoriteId, int userId) throws RollbackException
    {
        try
        {
            Transaction.begin();
            Favorite p = read(favoriteId);

            if (p == null)
            {
                throw new RollbackException("Favorite does not exist: ID=" + favoriteId);
            }

            if (userId != p.getUserId())
            {
                throw new RollbackException("Favorite not owned by user");
            }

            delete(favoriteId);
            Transaction.commit();
        }
        
        finally
        {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    public void clickIncrement(int favoriteId) throws RollbackException
    {
        try
        {
            Transaction.begin();
            Favorite p = read(favoriteId);

            if (p == null)
            {
                throw new RollbackException("Favorite does not exist: ID=" + favoriteId);
            }

//            if (userId != p.getUserId())
//            {
//                throw new RollbackException("Favorite not owned by user");
//            }

            p.setClicks(p.getClicks() + 1);
            update(p);
            Transaction.commit();
        }
        
        finally
        {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
    
    public Favorite[] getUserFavorites(int userId) throws RollbackException
    {
        try
        {
            Transaction.begin();
            Favorite[] favorites = match(MatchArg.equals("userId", userId));
            
            if (favorites!=null)
            {
                if(favorites.length != 0)
                {
                    Arrays.sort(favorites);
                	return favorites;
                }
            }
            Transaction.commit();
            
            return new Favorite[0];
        }
        
        finally
        {
            if (Transaction.isActive())
                Transaction.rollback();
        }
    }
    
    public Favorite[] getUserFavoritesByFavId(int favoriteId) throws RollbackException
    {
        try
        {
            Transaction.begin();
            Favorite[] favorites = match(MatchArg.equals("favoriteId", favoriteId));
            int userId = -1;
            
            if (favorites!=null)
            {
                if(favorites.length == 1)
                {
                	userId = favorites[0].getUserId();
                }
            }
            
            if (userId != -1)
            {
            	Favorite[] realFavorites = match(MatchArg.equals("userId", userId));
                Arrays.sort(realFavorites);
            	return realFavorites;
            }
            
            Transaction.commit();
            
            return new Favorite[0];
        }
        
        finally
        {
            if (Transaction.isActive())
                Transaction.rollback();
        }
    }

    
    public Favorite[] getUserFavorites(String email) throws RollbackException
    {
        try
        {
            Transaction.begin();
            Favorite[] favorites = match(MatchArg.equals("email", email));
            
            if (favorites!=null)
            {
                if(favorites.length != 0)
                {
                    return favorites;
                }
            }
            Transaction.commit();
            
            return new Favorite[0];
        }
        
        finally
        {
            if (Transaction.isActive())
                Transaction.rollback();
        }
    }

    
    
    public Favorite[] moveDown(int favoriteId, int userId) throws RollbackException
    {
        try
        {
            Transaction.begin();
            Favorite[] list = match(MatchArg.equals("userId", userId));
            Arrays.sort(list);

            int index = -1;
            for (int i = 0; i < list.length; i++)
            {
                if (list[i].getFavoriteId() == favoriteId)
                    index = i;
            }

            if (index == -1) throw new RollbackException("Favorite not owned by user");
            if (index == list.length - 1) throw new RollbackException("Favorite already at bottom of list");

            swapPositions(list[index], list[index + 1]);
            update(list[index]);
            update(list[index + 1]);

            Transaction.commit();

            // Resort and return
            Arrays.sort(list);
            return list;
        }
        
        finally
        {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    public Favorite[] moveUp(int favoriteId, int userId) throws RollbackException
    {
        try
        {
            Transaction.begin();
            Favorite[] list = match(MatchArg.equals("userId", userId));
            Arrays.sort(list);

            int index = -1;
            for (int i = 0; i < list.length; i++)
            {
                if (list[i].getFavoriteId() == favoriteId)
                    index = i;
            }

            if (index == -1)  throw new RollbackException("Favorite not owned by user");
            if (index == 0) throw new RollbackException("Favorite already at top of list");

            swapPositions(list[index], list[index - 1]);
            update(list[index - 1]);
            update(list[index]);

            Transaction.commit();

            // Resort and return
            Arrays.sort(list);
            return list;
        }
        
        finally
        {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }    
    
    private void swapPositions(Favorite f1, Favorite f2)
    {
        int temp = f1.getPosition();
        f1.setPosition(f2.getPosition());
        f2.setPosition(temp);
    }
}
