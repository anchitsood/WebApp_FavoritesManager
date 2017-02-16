/* ////////////////////////////////////////////////////////////

File Name: IdForm.java
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

public class IdForm extends FormBean
{
	private String favoriteId;

	public String getId() { return favoriteId;    }
	
	public int getIdAsInt()
	{
		try
		{
			return Integer.parseInt(favoriteId);
		}
		
		catch (NumberFormatException e)
		{
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	public void setId(String id) { this.favoriteId = id; }

	public List<String> getValidationErrors()
	{
		List<String> errors = new ArrayList<String>();
		
		if (favoriteId == null || favoriteId.length() == 0) {	errors.add("Id is required");		}
		
		if (errors.size() > 0)                          	{   return errors;                      }
		
		try
		{
			Integer.parseInt(favoriteId);
		}
		
		catch (NumberFormatException e)
		{
			errors.add("Id is not an integer");
		}
		
		return errors;
	}
}
