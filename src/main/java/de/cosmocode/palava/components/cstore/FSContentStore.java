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

package de.cosmocode.palava.components.cstore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jdom.Element;

import de.cosmocode.palava.Component;
import de.cosmocode.palava.ComponentException;
import de.cosmocode.palava.ComponentManager;
import de.cosmocode.palava.MimeType;
import de.cosmocode.palava.core.protocol.content.Content;
import de.cosmocode.palava.core.protocol.content.FileContent;
import de.cosmocode.palava.core.protocol.content.StreamContent;
import de.cosmocode.palava.core.server.Server;

public class FSContentStore implements ContentStore, Component {
    
    private static final Logger log = Logger.getLogger(FSContentStore.class);
    
    private File dir;
    
    @Override
    public StreamContent load(String key) throws Exception {
        final File file = mkFile(key);

        if (!file.exists()) return null;
        return new FileContent(file);
    }
    
    private String generateFilename(MimeType mimeType) {
        return DigestUtils.md5Hex(Long.toString(System.currentTimeMillis())) + "." + mimeType.getMinor();
    }

    private File mkFile(String name) {
        return new File(dir, name);
    }

    @Override
    public String store(Content content) throws Exception {
        
        final String name = generateFilename(content.getMimeType());
        final File file = mkFile(name);
        
        final FileOutputStream out = new FileOutputStream(file);
        content.write(out);
        out.flush();
        out.close();
        return name;
    }
    
    public String store( InputStream in, MimeType mimeType ) throws Exception {
        final String name = generateFilename(mimeType);
        final File file = mkFile(name);
        
        final FileOutputStream out = new FileOutputStream(file);
        
        IOUtils.copy(in, out);
        out.flush();
        out.close();
        return name;
        
    }

    @Override
    public void compose(ComponentManager manager) throws ComponentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void configure(Element root, Server server)
            throws ComponentException {
        dir = new File(root.getChildText("root"));        
    }

    @Override
    public void initialize() {
        
    }
    @Override
    public void remove(String key) {
        File file = mkFile(key);
        
        if ( ! file.delete() )
            log.error("cannot delete " + file.getAbsolutePath());
        
        
    }

}
