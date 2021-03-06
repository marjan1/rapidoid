package org.rapidoid.ctx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.job.Jobs;
import org.rapidoid.lambda.Lmbd;
import org.rapidoid.log.Log;
import org.rapidoid.u.U;

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
@Since("2.0.0")
public class Ctx implements CtxMetadata {

	private static final AtomicLong ID_COUNTER = new AtomicLong();

	private final long id = ID_COUNTER.incrementAndGet();

	private final String tag;

	private volatile UserInfo user;

	private volatile Object exchange;

	private volatile Object app;

	private volatile int referenceCounter = 1;

	private volatile ThreadLocal<Object> persisters = new ThreadLocal<Object>();

	private volatile boolean closed = false;

	private final List<Object> allPersisters = Collections.synchronizedList(new ArrayList<Object>(5));

	private final Map<Object, Object> extras = U.synchronizedMap();

	Ctx(String tag) {
		this.tag = tag;
	}

	public UserInfo user() {
		ensureNotClosed();
		return user;
	}

	public void setUser(UserInfo user) {
		ensureNotClosed();
		this.user = user;
	}

	@SuppressWarnings("unchecked")
	public <T> T exchange() {
		ensureNotClosed();
		return (T) exchange;
	}

	public void setExchange(Object exchange) {
		ensureNotClosed();
		this.exchange = exchange;
	}

	@SuppressWarnings("unchecked")
	public <T> T app() {
		ensureNotClosed();
		return (T) app;
	}

	public void setApp(Object app) {
		ensureNotClosed();
		this.app = app;
	}

	@SuppressWarnings("unchecked")
	public synchronized <P> P persister() {
		ensureNotClosed();

		Object persister = this.persisters.get();

		if (persister == null) {
			persister = Ctxs.createPersister();
			this.persisters.set(persister);
			allPersisters.add(persister);
		}

		return (P) persister;
	}

	public synchronized Ctx span() {
		ensureNotClosed();
		referenceCounter++;
		Log.debug("Spanning context", "ctx", this);
		return this;
	}

	synchronized void close() {
		ensureNotClosed();

		Log.debug("Closing context", "ctx", this);

		referenceCounter--;

		if (referenceCounter == 0) {
			clear();

		} else if (referenceCounter < 0) {
			throw new IllegalStateException("Reference counter < 0 for context: " + this);
		}
	}

	private synchronized void clear() {
		ensureNotClosed();

		Log.debug("Clearing context", "ctx", this);

		this.referenceCounter = 0;
		this.user = null;
		this.exchange = null;
		this.app = null;
		this.persisters = null;

		for (Object persister : allPersisters) {
			Ctxs.closePersister(persister);
		}

		allPersisters.clear();
		extras.clear();

		closed = true;
	}

	private void ensureNotClosed() {
		if (closed) {
			throw new RuntimeException("The context is closed!");
		}
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return prefixed("Ctx [id=" + id + ", tag=" + tag + ", user=" + user + ", exchange=" + exchange + ", app=" + app
				+ ", referenceCounter=" + referenceCounter + ", persisters=" + persisters + ", closed=" + closed
				+ ", allPersisters=" + (allPersisters != null ? toString(allPersisters, maxLen) : null) + ", extras="
				+ (extras != null ? toString(extras.entrySet(), maxLen) : null) + "]");
	}

	private String prefixed(String asStr) {
		String isClosed = closed ? " <CLOSED>" : "";
		String prefix = "Ctx#" + id + ":" + tag + isClosed;
		return prefix + " " + asStr;
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public boolean isClosed() {
		return closed;
	}

	public static synchronized <T> T executeInCtx(CtxData cd, Callable<T> action) {
		Ctx ctx = Ctxs.open("call");

		ctx.setApp(cd.app());
		ctx.setExchange(null);
		ctx.setUser(new UserInfo(cd.username(), cd.roles()));

		U.assign(ctx.extras(), cd.extras());

		try {
			return Lmbd.call(action);
		} finally {
			Ctxs.close();
		}
	}

	public static synchronized void executeInCtx(String tag, Runnable action) {
		Ctxs.open(tag);
		try {
			Jobs.execute(action);
		} finally {
			Ctxs.close();
		}
	}

	public Map<Object, Object> extras() {
		ensureNotClosed();
		return extras;
	}

	public boolean isLoggedIn() {
		return user() != null;
	}

	public String username() {
		return isLoggedIn() ? user().username : null;
	}

	public String tag() {
		return tag;
	}

}
