/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.core.session;

/**
 * Static utitlity class for working with {@link HttpSession}s.
 *
 * @author Willi Schoenborn
 */
public final class HttpSessions {
    
    private static final ThreadLocal<HttpSession> CURRENT_SESSION = new ThreadLocal<HttpSession>();

    private HttpSessions() {
        
    }
    
    public static HttpSession create(String sessionId) {
        return new DefaultHttpSession(sessionId);
    }
    
    public static boolean hasCurrentSession() {
        return CURRENT_SESSION.get() != null;
    }
    
    public static HttpSession getCurrentSession() {
        final HttpSession session = CURRENT_SESSION.get();
        if (session == null) throw new IllegalStateException("no session found");
        return session;
    }
    
}
