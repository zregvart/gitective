/*
 * Copyright (c) 2011 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package org.gitective.tests;

import java.util.Arrays;

import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.gitective.core.filter.tree.CommitTreeFilter;
import org.gitective.core.filter.tree.TypeCountFilter;
import org.gitective.core.service.CommitFinder;
import org.junit.Test;

/**
 * Unit tests of {@link FileCountFilter}
 */
public class FileCountFilterTest extends GitTestCase {

	/**
	 * Count commit with one file
	 *
	 * @throws Exception
	 */
	@Test
	public void oneFile() throws Exception {
		add("file.txt", "content");
		TypeCountFilter filter = TypeCountFilter.file();
		new CommitFinder(testRepo).setFilter(new CommitTreeFilter(filter))
				.find();
		assertEquals(1, filter.getCount());
	}

	/**
	 * Count commit with two files
	 *
	 * @throws Exception
	 */
	@Test
	public void twoFiles() throws Exception {
		add(testRepo, Arrays.asList("a.txt", "c/b.txt"),
				Arrays.asList("c1", "c2"), "message");
		TypeCountFilter filter = TypeCountFilter.file();
		new CommitFinder(testRepo).setFilter(new CommitTreeFilter(filter))
				.find();
		assertEquals(2, filter.getCount());
	}

	/**
	 * Reset filter
	 *
	 * @throws Exception
	 */
	@Test
	public void resetFilter() throws Exception {
		add("f.txt", "content");
		TypeCountFilter filter = TypeCountFilter.file();
		new CommitFinder(testRepo).setFilter(new CommitTreeFilter(filter))
				.find();
		assertEquals(1, filter.getCount());
		filter.reset();
		assertEquals(0, filter.getCount());
	}

	/**
	 * Reset filter
	 *
	 * @throws Exception
	 */
	@Test
	public void cloneFilter() throws Exception {
		TypeCountFilter filter = TypeCountFilter.file();
		TreeFilter clone = filter.clone();
		assertNotNull(clone);
		assertNotSame(filter, clone);
		assertTrue(filter instanceof TypeCountFilter);
		assertEquals(filter.getType(), ((TypeCountFilter) clone).getType());
	}
}
