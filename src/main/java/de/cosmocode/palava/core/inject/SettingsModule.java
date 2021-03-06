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

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

/**
 * A {@link Module} which binds the given {@link Properties}
 * as {@link Settings}.
 *
 * @since 2.0
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
public final class SettingsModule implements Module {

    private static final Logger LOG = LoggerFactory.getLogger(SettingsModule.class);

    private static final String PALAVA_ENVIRONMENT = "PALAVA_ENVIRONMENT";

    private final Properties properties;

    public SettingsModule(Properties properties) {
        this.properties = Preconditions.checkNotNull(properties, "Properties");
    }

    @Override
    public void configure(Binder binder) {
        Names.bindProperties(binder, properties);
        LOG.debug("Binding properties {} as settings", properties);
        binder.bind(Properties.class).annotatedWith(Settings.class).toInstance(properties);

        final String environment = System.getenv(PALAVA_ENVIRONMENT);
        if (StringUtils.isNotBlank(environment)) {
            LOG.info("Palava Environment is '{}'", environment);
            binder.bind(String.class).annotatedWith(PalavaEnvironment.class).toInstance(environment);
        } else {
            LOG.warn("Palava Environment is not set");
        }
    }

}
