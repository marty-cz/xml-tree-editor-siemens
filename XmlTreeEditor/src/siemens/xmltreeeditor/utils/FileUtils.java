/*
 * Copyright 2014 Martin Brazdil <martin.brazdil at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package siemens.xmltreeeditor.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class contains utilities for file operations.
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class FileUtils {
    
    /**
     * Verifies if given file path is valid and if file is readable.
     * If is not readable exception is raised.
     * @param f the file path to verification
     * @throws IOException if path is <code>null</code> or file is not readable
     */
    public static void verifyFile(Path f) 
            throws IOException {
        if (f == null || Files.isDirectory(f) || Files.isReadable(f) == false ) {
          throw new IOException(((f != null) ? f.getFileName() : "\"\"") + ": File is not readable");
        }
    }
    
}
