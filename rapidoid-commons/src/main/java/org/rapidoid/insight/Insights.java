package org.rapidoid.insight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

/**
 * @author Nikolche Mihajlovski
 * @since 4.1.0
 */
public class Insights {

	private static final Map<String, List<Insightful>> RESOURCES = U.autoExpandingMap(ArrayList.class);

	private static final Map<String, List<Object>> INFOS = U.autoExpandingMap(ArrayList.class);

	private static final Map<String, List<Object>> RESETABLE_INFOS = U.autoExpandingMap(ArrayList.class);

	public static void register(Insightful resource) {
		RESOURCES.get(resource.getKind()).add(resource);
	}

	public static String getInfo() {
		String info = RESETABLE_INFOS.toString() + " :: " + INFOS.toString() + " :: " + RESOURCES.toString();

		for (List<Object> list : RESETABLE_INFOS.values()) {
			for (Object item : list) {
				if (item instanceof Measure) {
					Measure m = (Measure) item;
					m.reset();
				}
			}
		}

		return info;
	}

	public static String getCpuMemStats() {
		Runtime rt = Runtime.getRuntime();

		long totalMem = rt.totalMemory();
		long maxMem = rt.maxMemory();
		long freeMem = rt.freeMemory();
		long usedMem = totalMem - freeMem;
		int megs = 1024 * 1024;

		String msg = "MEM [total=%s MB, used=%s MB, max=%s MB]";
		return String.format(msg, totalMem / megs, usedMem / megs, maxMem / megs);
	}

	public static void show() {
		new InsightsThread().start();
	}

	public static void register(String name, Object info) {
		INFOS.get(name).add(info);
	}

	public static StatsMeasure stats(String name) {
		StatsMeasure measure = new StatsMeasure();
		RESETABLE_INFOS.get(name).add(measure);
		return measure;
	}

	public static void reset() {
		RESOURCES.clear();
		INFOS.clear();
		RESETABLE_INFOS.clear();
	}

}
