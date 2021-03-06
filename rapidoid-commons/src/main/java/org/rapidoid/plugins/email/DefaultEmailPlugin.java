package org.rapidoid.plugins.email;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.concurrent.Callback;
import org.rapidoid.concurrent.Callbacks;
import org.rapidoid.log.Log;
import org.rapidoid.u.U;
import org.rapidoid.util.Constants;

/*
 * #%L
 * rapidoid-commons
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski and contributors
 * %%
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
 * #L%
 */

@Authors("Nikolche Mihajlovski")
@Since("4.1.0")
public class DefaultEmailPlugin extends AbstractEmailPlugin {

	private static final String MAIL_DESC = "Email plugin implementation hasn't been registered, so cannot send e-mail:\n"
			+ "To: %s\nCc: %s\nBcc: %s\nSubject: %s\nBody:\n%s" + Constants.SEPARATOR_LINE;

	public DefaultEmailPlugin() {
		super("default");
	}

	@Override
	public void send(Iterable<String> to, Iterable<String> cc, Iterable<String> bcc, String subject, String body,
			Callback<Void> callback) {

		Log.error(U.frmt(MAIL_DESC, to, cc, bcc, subject, body));

		Callbacks.error(callback, U.rte("Email plugin implementation hasn't been registered, so cannot send e-mail!"));
	}

}
