package org.rapidoid.http.fast;

/*
 * #%L
 * rapidoid-http-fast
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
import org.rapidoid.data.JSON;
import org.rapidoid.http.fast.handler.FastHttpHandler;
import org.rapidoid.http.fast.listener.FastHttpListener;
import org.rapidoid.job.Jobs;

@Authors("Nikolche Mihajlovski")
@Since("4.3.0")
public class On {

	private static final ServerSetup DEFAULT_SERVER_SETUP = new ServerSetup();

	private static boolean initialized = false;

	private static ServerSetup setup() {
		if (!initialized) {
			initialize();
			initialized = true;
		}

		return DEFAULT_SERVER_SETUP;
	}

	private static void initialize() {
		DEFAULT_SERVER_SETUP.listen();

		Jobs.execute(new Runnable() {
			@Override
			public void run() {
				JSON.warmup();
			}
		});
	}

	public static synchronized OnAction get(String path) {
		return setup().get(path);
	}

	public static synchronized OnAction post(String path) {
		return setup().post(path);
	}

	public static synchronized OnAction put(String path) {
		return setup().put(path);
	}

	public static synchronized OnAction delete(String path) {
		return setup().delete(path);
	}

	public static synchronized OnAction patch(String path) {
		return setup().patch(path);
	}

	public static synchronized OnAction options(String path) {
		return setup().options(path);
	}

	public static synchronized OnAction head(String path) {
		return setup().head(path);
	}

	public static synchronized OnAction trace(String path) {
		return setup().trace(path);
	}

	public static synchronized OnPage page(String path) {
		return setup().page(path);
	}

	public static synchronized ServerSetup req(ReqHandler handler) {
		return setup().req(handler);
	}

	public static synchronized ServerSetup req(FastHttpHandler handler) {
		return setup().req(handler);
	}

	public static synchronized ServerSetup controllers(Object... controllers) {
		return setup().controllers(controllers);
	}

	public static synchronized ServerSetup port(int port) {
		return DEFAULT_SERVER_SETUP.port(port);
	}

	public static synchronized ServerSetup address(String address) {
		return DEFAULT_SERVER_SETUP.address(address);
	}

	public static synchronized ServerSetup defaultWrap(HttpWrapper... wrappers) {
		return DEFAULT_SERVER_SETUP.defaultWrap(wrappers);
	}

	public static synchronized ServerSetup listener(FastHttpListener listener) {
		return DEFAULT_SERVER_SETUP.listener(listener);
	}

	public static synchronized ServerSetup getDefaultSetup() {
		return setup();
	}

	public static ServerSetup createCustomSetup() {
		return new ServerSetup();
	}

}
