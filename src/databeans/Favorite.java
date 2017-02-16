/* ////////////////////////////////////////////////////////////

File Name: Favorite.java
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



package databeans;

//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

import org.genericdao.PrimaryKey;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

@PrimaryKey("favoriteId")
public class Favorite implements Comparable<Favorite>
{
	//public static final List<String> EXTENSIONS = Collections.unmodifiableList(Arrays.asList( new String[] {".jpg", ".gif", ".JPG"} ));

	private int favoriteId     = -1;
	private int userId;
	private String uri		   = null;
    private String comment	   = null;
    private int clicks;
    private int position	   = 0;

    public int compareTo(Favorite other)
	{
		// Order first by owner, then by position
		if (userId == -1 && other.userId != -1) return -1;
		if (userId != -1 && other.userId == -1) return 1;
		int c = userId - other.userId;
		if (c != 0) return c;
		return position - other.position;
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Favorite)
		{
			Favorite other = (Favorite) obj;
			return compareTo(other) == 0;
		}
		return false;
	}
	
	// getters
	public int      getFavoriteId()        { return favoriteId;   }
    public int      getUserId()            { return userId;       }
    public String   getUri()               { return uri;          }
    public String   getComment()           { return comment;      }
    public int      getClicks()        	   { return clicks;       }
    public int   	getPosition()    	   { return position;     }
    
    
    // setters
    public void   setFavoriteId(int i)   { favoriteId = i;      }
    public void   setUserId(int i)       { userId = i;          }
    public void   setUri(String s)       { uri = s;             }
    public void   setComment(String s)   { comment = s;         }
    public void   setClicks(int i)       { clicks = i;          }
    public void   setPosition(int p)     { position = p;        }
    
    
    public String toString()
    {
    	return "Favorite("+favoriteId+")";
    }
}
