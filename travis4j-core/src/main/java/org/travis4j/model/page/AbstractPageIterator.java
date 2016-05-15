/**
 *    Copyright 2015-2016 Thomas Rausch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.travis4j.model.page;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.travis4j.model.PageIterator;

/**
 * AbstractPageIterator.
 */
public abstract class AbstractPageIterator<T> implements PageIterator<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractPageIterator.class);

    private List<T> currentPage;

    @Override
    public boolean hasNext() {
        if (currentPage == null) {
            return true;
        }

        return getNextOffset() > 1;
    }

    @Override
    public List<T> next() {
        if (currentPage == null) {
            LOG.debug("Loading first page");
            currentPage = loadFirstPage();

            if(currentPage == null) {
                throw new IllegalStateException("First page could not be loaded");
            }
        } else {
            long offset = getNextOffset();
            LOG.debug("Loading next page from offset {}", offset);
            currentPage = loadNextPage(offset);
        }
        return currentPage;
    }

    public List<T> getCurrentPage() {
        return currentPage;
    }

    protected long getNextOffset() {
        return getCurrentOffset() - (currentPage.size() - 1);
    }

    protected abstract long getCurrentOffset();

    protected abstract List<T> loadFirstPage();

    protected abstract List<T> loadNextPage(long offset);

}
