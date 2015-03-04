package org.rapidoid.widget;

/*
 * #%L
 * rapidoid-widget
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski
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
import org.rapidoid.html.Tag;
import org.rapidoid.util.U;

@Authors("Nikolche Mihajlovski")
@Since("2.3.0")
public class StreamWidget extends AbstractWidget {

	private Tag template;

	private String dataUrl;

	@Override
	protected Tag render() {
		String url = U.or(dataUrl, defaultDataUrl());

		Tag loading = div("Loading data...").attr("ng-show", "stream.busy");

		Tag scroll = div(template, loading).attr("infinite-scroll", "stream.nextPage()");

		scroll = scroll.attr("infinite-scroll-disabled", "stream.busy");
		scroll = scroll.attr("infinite-scroll-distance", "1");

		Tag stream = div(scroll).attr("ng-controller", "StreamController").attr("data-url", url);
		return stream;
	}

	protected String defaultDataUrl() {
		String url = exchange().uri();

		if (url.endsWith("/")) {
			url = U.mid(url, 0, -1);
		}

		if (U.isEmpty(url)) {
			url = "/index";
		}

		if (url.endsWith(".html")) {
			url = U.mid(url, 0, -5);
		}

		return url + ".js";
	}

	public Tag template() {
		return template;
	}

	public StreamWidget template(Tag template) {
		this.template = template;
		return this;
	}

	public String dataUrl() {
		return dataUrl;
	}

	public StreamWidget dataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
		return this;
	}

}