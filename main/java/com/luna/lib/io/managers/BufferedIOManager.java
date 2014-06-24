/*
 * Isis modified client for Minecraft.
 * Copyright (C) 2014-2015  godshawk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ===========================================================================
 *
 * ALL SOURCE CODE WITHOUT THIS COPYRIGHT IS THE PROPERTY OF ITS RESPECTIVE
 * OWNER(S). I CLAIM NO RIGHT TO OR OWNERSHIP OF ANY OF IT.
 *
 * Minecraft is owned by Mojang AB.
 * Java itself is owned by Oracle.
 * All other code is not owned by me.
 * Thank you, and have a good day!
 */

package com.luna.lib.io.managers;

import com.luna.lib.io.IOManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads/Writes files, using {@link java.io.BufferedReader} and
 * {@link java.io.BufferedWriter}
 *
 * @author godshawk
 */
public class BufferedIOManager implements IOManager {

    /**
     * The file that gets read/written from/to.
     */
    protected final File file;

    protected BufferedReader reader;
    protected BufferedWriter writer;

    protected BufferedIOManager(final File file) {
        this.file = file;
    }

    public static BufferedIOManager getInstance(final File file) {
        return new BufferedIOManager(file);
    }

    @Override
    public synchronized void setupRead() {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void setupWrite() {
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void closeStream() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (final IOException e1) {
            e1.printStackTrace();
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized List<String> read() {
        final List<String> readLines = new ArrayList<String>();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                readLines.add(line);
            }

            return readLines;
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public synchronized void write(final List<String> data) {
        for (final String e : data) {
            try {
                writer.write(e);
            } catch (final IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
