package org.rapidoid.fluent;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
 * #%L
 * rapidoid-fluent
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

/**
 * @author Nikolche Mihajlovski
 * @since 5.0.0
 */
public class To {

	public static <T> Collector<T, ?, List<T>> list() {
		return Collectors.toList();
	}

	public static <T> Collector<T, ?, Set<T>> set() {
		return Collectors.toSet();
	}

	public static <T, K, U> Collector<T, ?, Map<K, U>> map(Function<? super T, ? extends K> keyMapper,
			Function<? super T, ? extends U> valueMapper) {
		return Collectors.toMap(keyMapper, valueMapper);
	}

	public static <T, K, V> Collector<Entry<K, V>, ?, Map<K, V>> map() {
		return To.map(Entry::getKey, Entry::getValue);
	}

}
