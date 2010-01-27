/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
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

package de.cosmocode.palava.bridge.command;

import com.google.common.base.Function;


/**
 * Static utility class for working with {@link Command}s.
 *
 * @author Willi Schoenborn
 */
public final class Commands {
    
    private static final Function<Command, Class<?>> GET_CLASS = new Function<Command, Class<?>>() {
        
        @Override
        public Class<?> apply(Command command) {
            if (command instanceof JobCommand) {
                return JobCommand.class.cast(command).getConcreteClass();
            } else {
                return command.getClass();
            }
        }
        
    };
    
    private Commands() {
        
    }

    /**
     * Returns the real class of a {@link Command}, which may differ from
     * {@link Object#getClass()} in case the given {@link Command} is
     * {@linkplain Filterable filterable}.
     * 
     * @param command the command
     * @return the underlying class
     */
    public static Class<?> getClass(Command command) {
        return GET_CLASS.apply(command);
    }

}
