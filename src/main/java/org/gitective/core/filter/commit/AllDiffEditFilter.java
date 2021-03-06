/*
 * Copyright (c) 2012 Kevin Sawicki <kevinsawicki@gmail.com>
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
package org.gitective.core.filter.commit;

import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;

/**
 * Parent filter that invokes
 * {@link #include(org.eclipse.jgit.revwalk.RevCommit, java.util.Collection)} on
 * all child filters ignoring the return value
 */
public class AllDiffEditFilter extends CompositeDiffEditFilter {

	/**
	 * Create all diff edit filter
	 *
	 * @param detectRenames
	 * @param filters
	 */
	public AllDiffEditFilter(final boolean detectRenames,
			final CommitDiffEditFilter... filters) {
		super(detectRenames, filters);
	}

	/**
	 * Create all diff edit filter
	 *
	 * @param filters
	 */
	public AllDiffEditFilter(final CommitDiffEditFilter... filters) {
		this(false, filters);
	}

	@Override
	protected boolean include(final RevCommit commit, final DiffEntry diff,
			final Collection<Edit> edits) {
		final int length = filters.length;
		for (int i = 0; i < length; i++)
			filters[i].include(commit, diff, edits);
		return true;
	}

	@Override
	public RevFilter clone() {
		return new AllDiffFilter(detectRenames, cloneFilters());
	}
}
