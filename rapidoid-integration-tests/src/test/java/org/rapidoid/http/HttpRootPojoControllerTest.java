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
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.Page;
import org.rapidoid.annotation.Param;
import org.rapidoid.annotation.Since;
import org.rapidoid.http.fast.On;
import org.rapidoid.u.U;

@Authors("Nikolche Mihajlovski")
@Since("5.0.10")
public class HttpRootPojoControllerTest extends HttpTestCommons {

	@Test
	public void testRootPojoHandler() {
		On.controllers(new Object() {

			@GET(uri = "/a")
			public Object theFoo() {
				return "foo";
			}

			@POST(uri = "/b")
			public Object theBar() {
				return "bar";
			}

			@Page(uri = "/the/page")
			public Object thePage() {
				return "the page";
			}

			@GET
			@SuppressWarnings("unchecked")
			public String num(@Param("a") byte a, @Param("b") short b, @Param("c") char c, @Param("d") int d,
					@Param("e") long e, @Param("f") float f, @Param("g") double g, @Param("h") boolean hh,
					@Param("i") boolean ii, @Param("j") String j) {
				return U.join(":", a, b, c, d, e, f, g, hh, ii, j);
			}

			@Page
			@SuppressWarnings("unchecked")
			public String num2(@Param("a") byte a, @Param("b") short b, @Param("c") char c, @Param("d") int d,
					@Param("e") long e, @Param("f") float f, @Param("g") double g, @Param("h") boolean hh,
					@Param("i") boolean ii, @Param("j") String j) {
				return U.join(":", a, b, c, d, e, f, g, hh, ii, j);
			}

		});

		onlyGet("/a");
		onlyPost("/b");
		getAndPost("/the/page");

		onlyGet("/num?a=1&b=2&c=3&d=4&e=5&f=12.345&g=9.81&h=true&i=false&j=abc");
		getAndPost("/num2?a=1&b=2&c=3&d=4&e=5&f=12.345&g=9.81&h=true&i=false&j=abc");
	}

}
