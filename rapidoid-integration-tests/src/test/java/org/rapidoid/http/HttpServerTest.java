package org.rapidoid.http;

/*
 * #%L
 * rapidoid-integration-tests
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

import org.junit.Test;
import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.http.HTTP;
import org.rapidoid.http.Req;
import org.rapidoid.http.fast.On;
import org.rapidoid.http.fast.ReqHandler;

@Authors("Nikolche Mihajlovski")
@Since("4.1.0")
public class HttpServerTest extends HttpTestCommons {

	@Test
	public void testHttpServer() {
		On.get("/").html("home");

		On.req(new ReqHandler() {
			@Override
			public Object handle(Req req) throws Exception {
				return req.response().json("abc");
			}
		});

		eq(new String(HTTP.get("http://localhost:8888/")), "home");
		eq(new String(HTTP.post("http://localhost:8888/", null, new byte[0], null)), "\"abc\"");
	}

}
