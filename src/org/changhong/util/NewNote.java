/*
 * Tomdroid
 * Tomboy on Android
 * http://www.launchpad.net/tomdroid
 * 
 * Copyright 2010 Olivier Bilodeau <olivier@bottomlesspit.org>
 * 
 * This file is part of Tomdroid.
 * 
 * Tomdroid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Tomdroid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Tomdroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.changhong.util;

import android.content.Context;
import org.changhong.Note;

import java.util.UUID;


/**
 * Creates a new note object 
 *
 */
public class NewNote {

	// Logging info
	private static final String	TAG = "NewNote";
	// indicates, if note was never saved before (for dismiss dialogue)
	
	public static Note createNewNote(Context context, String title, String xmlContent) {
		TLog.v(TAG, "Creating new note");
		
		Note note = new Note();
		
		note.setTitle(title);

		UUID newid = UUID.randomUUID();
        String uu = newid.toString() /*+ "_" + String.valueOf(System.currentTimeMillis())*/;
        note.setGuid(uu);


		note.setLastChangeDate();
		note.setXmlContent(xmlContent);
		
		return note;
	}

    public static void updateuuid(Note note) {
        String uu = note.getGuid();
        int i = uu.lastIndexOf("_");
        uu = uu.substring(0, i);
        uu = uu + String.valueOf(System.currentTimeMillis());
        note.setGuid(uu);

    }

}
