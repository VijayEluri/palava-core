/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.core.inject;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverter;

/**
 * A {@link TypeConverter} for {@link InetAddress}es.
 *
 * @since 2.4
 * @author Willi Schoenborn
 */
public final class InetAddressConverter implements TypeConverter {

    @Override
    public InetAddress convert(String value, TypeLiteral<?> toType) {
        try {
            return InetAddress.getByName(value);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
