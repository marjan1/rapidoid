package org.rapidoid.gui.reqinfo;

/*
 * #%L
 * rapidoid-gui
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

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.cls.Cls;

@Authors("Nikolche Mihajlovski")
@Since("5.0.4")
public class ReqInfo {

	private static final NoReqInfo NO_REQ_INFO = new NoReqInfo();

	private static final String RAPIDOID_CTX = "org.rapidoid.ctx.Ctx";

	static volatile IReqInfo INFO;

	public static IReqInfo get() {
		if (INFO == null) {
			INFO = createInfo();
		}

		return INFO.exists() ? INFO : NO_REQ_INFO;
	}

	private static IReqInfo createInfo() {
		if (Cls.exists(RAPIDOID_CTX)) {
			return new RapidoidReqInfo();
		}

		return NO_REQ_INFO;
	}

}
