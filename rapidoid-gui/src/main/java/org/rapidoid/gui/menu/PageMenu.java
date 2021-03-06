package org.rapidoid.gui.menu;

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

import java.util.Collections;
import java.util.List;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.cls.Cls;
import org.rapidoid.data.YAML;
import org.rapidoid.io.Res;
import org.rapidoid.u.U;

@Authors("Nikolche Mihajlovski")
@Since("4.1.0")
public class PageMenu {

	private final List<PageMenuItem> items;

	public PageMenu(List<PageMenuItem> items) {
		this.items = items;
	}

	public List<PageMenuItem> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return U.join("\n", items);
	}

	public static PageMenu parse(String filename) {
		byte[] yaml = Res.from(filename).getBytesOrNull();
		Object data = yaml != null ? YAML.parse(yaml, Object.class) : null;
		return from(data);
	}

	public static PageMenu from(Object data) {
		return Cls.struct(PageMenu.class, PageMenuItem.class, U.or(data, U.map()));
	}

	public List<PageMenuItem> leftItems() {
		List<PageMenuItem> left = U.list();

		for (PageMenuItem item : items) {
			if (!item.isRight()) {
				left.add(item);
			}
		}

		return left;
	}

	public List<PageMenuItem> rightItems() {
		List<PageMenuItem> right = U.list();

		for (PageMenuItem item : items) {
			if (item.isRight()) {
				right.add(item);
			}
		}

		return right;
	}

	public List<PageMenuItem> rightItemsReversed() {
		List<PageMenuItem> list = rightItems();
		Collections.reverse(list);
		return list;
	}

}
