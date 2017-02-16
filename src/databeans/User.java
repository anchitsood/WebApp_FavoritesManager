/* ////////////////////////////////////////////////////////////

File Name: User.java
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

@PrimaryKey("userId")
public class User implements Comparable<User>
{
	private String  hashedPassword = "*";
	private int     salt           = 0;

	
	private int 	userId;
	private String  firstName      = null;
	private String  lastName       = null;
    private String 	email		   = null;
    private String 	password	   = null;
	
	
	public boolean checkPassword(String password)
	{
		return hashedPassword.equals(hash(password));
	}
	
	public void encodePassword(String s)
	{
		salt = newSalt();
		hashedPassword = hash(s);
	}

	public int compareTo(User other)
	{
		// Order first by lastName and then by firstName
		int c = lastName.compareTo(other.lastName);
		if (c != 0) return c;
		c = firstName.compareTo(other.firstName);
		if (c != 0) return c;
		return email.compareTo(other.email);
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof User)
		{
			User other = (User) obj;
			return email.equals(other.email);
		}
		return false;
	}

	public int     hashCode()          { return email.hashCode(); }
	
	
	// getters
	public String  getHashedPassword() { return hashedPassword; }
	public int     getSalt()           { return salt;           }

	public int     getUserId()         { return userId;         }
    public String  getFirstName()      { return firstName;      }
	public String  getLastName()       { return lastName;       }
	public String  getEmail()          { return email;          }
	public String  getPassword()       { return password;       }


	// setters
	public void setHashedPassword(String x)  { hashedPassword = x; }
	public void setSalt(int x)               { salt = x;           }

	public void setUserId(int i)             { userId = i;         }
    public void setFirstName(String s)       { firstName = s;      }
	public void setLastName(String s)        { lastName = s;       }
	public void setEmail(String s)           { email = s;          }
    public void setPassword(String s)        { password = s;       }

    
	public String toString()
	{
		return "User("+getEmail()+")";
	}

	private String hash(String clearPassword)
	{
		if (salt == 0) return null;

		MessageDigest md = null;
		try
		{
		  md = MessageDigest.getInstance("SHA1");
		}
		catch (NoSuchAlgorithmException e)
		{
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);
		
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i=0; i<digestBytes.length; i++)
		{
			  int lowNibble = digestBytes[i] & 0x0f;
			  int highNibble = (digestBytes[i]>>4) & 0x0f;
			  digestSB.append(Integer.toHexString(highNibble));
			  digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();

		return digestStr;
	}

	private int newSalt()
	{
		Random random = new Random();
		return random.nextInt(8192)+1;  // salt cannot be zero
	}
}
