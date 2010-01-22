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

package de.cosmocode.palava.core.protocol;

import java.io.InputStream;

import de.cosmocode.palava.core.command.Command;
import de.cosmocode.palava.core.request.HttpRequest;

/**
 * parses the content of a datarequest into a map.
 * 
 * @author Detlef Hüttemann
 * @author Willi Schoenborn
 * @deprecated use {@link JsonCall} instead
 */
@Deprecated
public final class DataCall extends JsonCall {
    
    DataCall(HttpRequest request, Command command, Header header, InputStream stream) {
        super(request, command, header, stream);
    }

}
