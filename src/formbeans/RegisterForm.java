/* ////////////////////////////////////////////////////////////

File Name: RegisterForm.java
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


package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

public class RegisterForm extends FormBean {
	private String firstName;
	private String lastName;
	private String email   ;
	private String password;
	private String confirm ;
	
	public String getFirstName() { return firstName; }
	public String getLastName()  { return lastName;  }
	public String getEmail()  	 { return email;     }
	public String getPassword()  { return password;  }
	public String getConfirm()   { return confirm;   }
	
	public void setFirstName(String s) { firstName = trimAndConvert(s,"<>\"");  }
	public void setLastName(String s)  { lastName  = trimAndConvert(s,"<>\"");  }
	public void setEmail(String s)     { email     = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s)  { password  = s.trim();                  }
	public void setConfirm(String s)   { confirm   = s.trim();                  }

	public List<String> getValidationErrors()
	{
		List<String> errors = new ArrayList<String>();

		if (firstName == null || firstName.length() == 0)	{	errors.add("First Name is required");		}
		if (lastName == null || lastName.length() == 0)		{	errors.add("Last Name is required");		}
		if (email == null || email.length() == 0)			{	errors.add("Email is required");			}
		if (password == null || password.length() == 0)		{	errors.add("Password is required");			}
		if (confirm == null || confirm.length() == 0)		{	errors.add("Confirm Password is required");	}
		
		if (errors.size() > 0)								{	return errors;								}
		
		if (email.matches(".*[<>\"].*"))                    {   errors.add("Email may not contain angle brackets or quotes");          		}
        if (firstName.matches(".*[<>\"].*"))                {   errors.add("First Name may not contain angle brackets or quotes");     		}
        if (lastName.matches(".*[<>\"].*"))                 {   errors.add("Last Name may not contain angle brackets or quotes");      		}
        if (!password.equals(confirm))						{	errors.add("Passwords are not the same");									}
        if (password.matches(".*[<>\"].*"))                 {   errors.add("Password may not contain angle brackets or quotes");      		}
        if (confirm.matches(".*[<>\"].*"))                  {   errors.add("Confirm password may not contain angle brackets or quotes");    }
        
		return errors;
	}
}
