package org.rapidoid.http;

import org.rapidoid.util.U;
import org.rapidoid.var.Var;

/*
 * #%L
 * rapidoid-http
 * %%
 * Copyright (C) 2014 Nikolche Mihajlovski
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

public class HttpExchanges {

	private static final ThreadLocal<HttpExchange> EXCHANGES = new ThreadLocal<HttpExchange>();

	public static void setThreadLocalExchange(HttpExchange x) {
		EXCHANGES.set(x);
	}

	public static HttpExchange getThreadLocalExchange() {
		HttpExchange x = EXCHANGES.get();
		U.notNull(x, "HTTP exchange");
		return x;
	}

	public static HttpExchange getThreadLocalExchangeIfExists() {
		return EXCHANGES.get();
	}

	public static <T> Var<T> sessionVar(String name, T defaultValue) {
		return new SessionVar<T>(name, defaultValue);
	}

}