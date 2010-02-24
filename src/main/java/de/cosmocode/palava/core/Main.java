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

package de.cosmocode.palava.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Application entry point.
 *
 * @author Willi Schoenborn
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Option(name = "-c",  required = true, aliases = "--config", usage = "Path to settings file")
    private File settings;

    @Option(name = "-s", required = false, aliases = "--state-file", usage = "Path to state file")
    private File stateFile;

    @Option(name = "-n", required = false, aliases = "--no-auto-shutdown", usage = "If the framework should shut down as soon as possible after boot")
    private boolean noAutoShutdown;

    private final Framework framework;

    private Main(String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            parser.printUsage(System.err);
            throw new IllegalArgumentException(e);
        }

        Preconditions.checkNotNull(settings, "Settings file not set");
        Preconditions.checkState(settings.exists(), "Settings file %s does not exist", settings.getAbsolutePath());

        final Properties properties = new Properties();

        final Reader reader;

        try {
            reader = new FileReader(settings);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        framework = Palava.createFramework(properties);
    }

    private void start() {
        try {
            framework.start();
            persistState();
        /* CHECKSTYLE:OFF */
        } catch (RuntimeException e) {
        /* CHECKSTYLE:ON */
            LOG.error("startup failed", e);
            stop();
        }
    }

    private void stop() {
        try {
            framework.stop();
        } finally {
            persistState();
        }
    }

    private void persistState() {
        if (stateFile == null) return;

        try {
            final Writer writer = new FileWriter(stateFile);
            writer.write(framework.currentState().name() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot persist state to file", e);
        }
    }

    private boolean isNoAutoShutdown() {
        return noAutoShutdown;
    }

    /**
     * Application entry point.
     *
     * @param args command line arguments
     * @throws CmdLineException if command line parsing failed
     */
    public static void main(String[] args) throws CmdLineException {
        final Main main = new Main(args);

        main.start();

        LOG.debug("adding shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                main.stop();
            }

        }));

        if (main.isNoAutoShutdown()) {
            LOG.debug("automatic shutdown disabled; running until someone else triggers the shutdown");

            // block until someone else triggeres the jvm shutdown
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    // it's ok, shut down
                    LOG.debug("main thread interrupted", e);
                }
            }
        }
    }

}
