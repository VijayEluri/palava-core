package de.cosmocode.palava;
/*
palava - a java-php-bridge
Copyright (C) 2007  CosmoCode GmbH

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.util.Map;


/**
 * every job has to have a process() function
 * @author Tobias Sarnowski
 */
public interface Job
{

	/**
	 * @param request the request of this job. contains the invoking args
	 * @param response the container for the results 
	 * @param session a session (may be null). available across different frontent/http requests
	 * @param server the server structure - basically for the components lookup
	 * @param caddy a container available across all jobs of the <strong>same</strong> frontend/http request
	 * @throws ConnectionLostException
	 * @throws Exception
	 */
	public void process(Request request, Response response, Session session, Server server, Map<String,Object> caddy ) throws ConnectionLostException, Exception;

}
