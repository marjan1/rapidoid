package org.rapidoid.scan;

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

import java.util.List;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;

@Authors("Nikolche Mihajlovski")
@Since("2.5.0")
public class ScanParams {

    private String pkg = null;

    private String matching = null;

    private org.rapidoid.lambda.Predicate<Class<?>> filter = null;

    private Class<? extends java.lang.annotation.Annotation> annotated = null;

    private ClassLoader classLoader = null;

	public synchronized ScanParams pkg(String pkg) {
	    this.pkg = pkg;
	    return this;
	}

	public synchronized String pkg() {
		return this.pkg;
	}

	public synchronized ScanParams matching(String matching) {
	    this.matching = matching;
	    return this;
	}

	public synchronized String matching() {
		return this.matching;
	}

	public synchronized ScanParams filter(org.rapidoid.lambda.Predicate<Class<?>> filter) {
	    this.filter = filter;
	    return this;
	}

	public synchronized org.rapidoid.lambda.Predicate<Class<?>> filter() {
		return this.filter;
	}

	public synchronized ScanParams annotated(Class<? extends java.lang.annotation.Annotation> annotated) {
	    this.annotated = annotated;
	    return this;
	}

	public synchronized Class<? extends java.lang.annotation.Annotation> annotated() {
		return this.annotated;
	}

	public synchronized ScanParams classLoader(ClassLoader classLoader) {
	    this.classLoader = classLoader;
	    return this;
	}

	public synchronized ClassLoader classLoader() {
		return this.classLoader;
	}

	public synchronized List<Class<?>> getClasses() {
		return ClasspathUtil.getClasses(this);
	}

}
