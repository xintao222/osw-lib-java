/*
 *  Copyright 2010 Vodafone Group Services Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *    
 */
package org.onesocialweb.smack.packet.relation;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.onesocialweb.model.relation.Relation;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.onesocialweb.xml.xpp.imp.DefaultXppRelationReader;
import org.xmlpull.v1.XmlPullParser;

public class IQRelationProvider implements IQProvider {

	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		final IQRelationQuery iq = new IQRelationQuery();
		final DefaultXppRelationReader reader = new DefaultXppRelationReader();
		final List<Relation> relations = new ArrayList<Relation>();
		
		boolean done = false;

		while (!done) {
			int eventType = parser.next();
			if (eventType == XmlPullParser.START_TAG) {
				if (parser.getName().equals(Onesocialweb.RELATION_ELEMENT)) {
					relations.add(reader.parse(parser));
				}
			} else if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals("query")) {
                    done = true;
                }
            }
		}
		
		iq.setRelations(relations);
		
		return iq;
	}

}
