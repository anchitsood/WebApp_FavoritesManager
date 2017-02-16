/* ////////////////////////////////////////////////////////////

File Name: AddFavoriteForm.java
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
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

//import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBean;

/**
 * Author: Anchit Sood
 * Andrew ID: anchits
 * Homeword 4
 * 08-672 M2 - J2EE Web App Development
 * 12/14/2016
 */

public class AddFavoriteForm extends FormBean {
	private String uri     = "";
	private String comment = "";
	
	public String getUri()		{ return uri;         }
	public String getComment()	{ return comment;     }

	public void setUri(String s)		{	uri = s;							}
	public void setComment(String s)	{	comment = trimAndConvert(s,"<>\""); }
	
	public ArrayList<String> getValidationErrors()
	{
		ArrayList<String> errors = new ArrayList<String>();
				
		if (uri == null || uri.length() == 0) 			{	errors.add("URI is required");			}
		if (comment == null || comment.length() == 0) 	{	errors.add("Comment is required");		}
		
		if (errors.size() > 0)                          {   return errors;                        	}
		
		if (uri.matches(".*[<>\"].*"))                	{	errors.add("URI may not contain angle brackets or quotes");          }
		
		return errors;		
	}
}
