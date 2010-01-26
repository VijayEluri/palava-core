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

package de.cosmocode.palava.legacy;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import de.cosmocode.palava.core.call.Call;
import de.cosmocode.palava.core.call.MissingArgumentException;
import de.cosmocode.palava.core.protocol.ConnectionLostException;
import de.cosmocode.palava.core.protocol.JsonCall;
import de.cosmocode.palava.core.protocol.Response;
import de.cosmocode.palava.core.server.Server;
import de.cosmocode.palava.core.session.HttpSession;

/**
 * 
 * @deprecated use Cache annotation
 *
 * @author Willi Schoenborn
 */
@Deprecated
public abstract class CachableJSONJob extends CachableJob {
    
    private JSONObject json;

    @Override
    public final void process(Call request, Response response, Server server, HttpSession session, 
        Map<String, Object> caddy) throws ConnectionLostException, Exception {

        JsonCall jRequest = (JsonCall) request;
        json = jRequest.getJSONObject();
        
        process(json, response, session, server, caddy);
    }
    
    protected abstract void process(JSONObject json, Response response, HttpSession session, Server server, 
        Map<String, Object> caddy) throws ConnectionLostException, Exception;
    
    @Override
    public final void require(String... keys) throws MissingArgumentException {
        require(json, keys);
    }
    
    protected final void require(JSONObject json, String... keys) throws MissingArgumentException {
        for (String key : keys) {
            if (!json.has(key)) throw new MissingArgumentException(key);
        }
    }
    

    // methods implemented from UtilityJob

    @Override
    public String getMandatory(String key) throws MissingArgumentException, JSONException {
        if (json.has(key)) return json.getString(key);
        else throw new MissingArgumentException(this, key);
    }

    @Override
    public String getMandatory(String key, String argumentType) throws MissingArgumentException, JSONException {
        if (json.has(key)) return json.getString(key);
        else throw new MissingArgumentException(this, key, argumentType);
    }

    @Override
    public String getOptional(String key) {
        if (json.has(key)) return json.optString(key);
        else return null;
    }

    @Override
    public String getOptional(String key, String defaultValue) {
        if (json.has(key)) return json.optString(key);
        else return defaultValue;
    }

    @Override
    public boolean hasArgument(String key) {
        return json.has(key);
    }

}