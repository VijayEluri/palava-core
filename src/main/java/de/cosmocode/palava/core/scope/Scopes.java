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

package de.cosmocode.palava.core.scope;

import com.google.common.base.Preconditions;

import de.cosmocode.palava.core.call.Call;
import de.cosmocode.palava.core.request.HttpRequest;
import de.cosmocode.palava.core.session.HttpSession;

/**
 * Keeps track of thread bound {@link Call}s, {@link HttpRequest}s and {@link HttpSession}s.
 *
 * @author Willi Schoenborn
 */
public final class Scopes {
    
    private static final ThreadLocal<Call> CALLS = new ThreadLocal<Call>();
    private static final ThreadLocal<HttpRequest> REQUESTS = new ThreadLocal<HttpRequest>();

    private Scopes() {
        
    }
    
    /**
     * Sets the current call.
     * 
     * @param call the new current call
     */
    static void setCurrentCall(Call call) {
        CALLS.set(call);
    }
    
    /**
     * Provides access to the current call.
     * 
     * @return the current call.
     */
    public static Call getCurrentCall() {
        final Call call = CALLS.get();
        Preconditions.checkState(call != null, "No call found");
        return call;
    }
    
    /**
     * Sets the current request.
     * 
     * @param request the new current request
     */
    static void setCurrentRequest(HttpRequest request) {
        REQUESTS.set(request);
    }
    
    /**
     * Provides access to the current request.
     * 
     * @return the current request
     */
    public static HttpRequest getCurrentRequest() {
        final HttpRequest request = REQUESTS.get();
        Preconditions.checkState(request != null, "No request found");
        return request;
    }
    
    public static HttpSession getCurrentSession() {
        return getCurrentRequest().getHttpSession();
    }
    
}