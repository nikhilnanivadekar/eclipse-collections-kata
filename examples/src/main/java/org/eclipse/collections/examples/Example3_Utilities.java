/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Examples of:
 * <p>
 * 1) Adapters
 * <p>
 * 2) {@link Iterate}
 * <p>
 * 3) {@link Functions}
 * <p>
 * 4) {@link Predicates}
 * <p>
 * 5) {@link Procedures}
 */
@SuppressWarnings("unchecked")
public class Example3_Utilities
{
    @Test
    public void adapters()
    {
        List<Integer> numbersAsList = Arrays.asList(1, 2, 3, 4, 5);

        Verify.assertInstanceOf(MutableList.class, numbersAsList);

        Set<Integer> numbersAsSet = new HashSet<>(numbersAsList);

        Verify.assertInstanceOf(MutableSet.class, numbersAsSet);
    }

    @Test
    public void iterate()
    {
        List<Integer> numbersAsList = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> evens = new ArrayList<>();
        for (Integer each : numbersAsList)
        {
            if (each % 2 == 0)
            {
                evens.add(each);
            }
        }

        Assert.assertEquals(Lists.mutable.of(2, 4), evens);
    }

    @Test
    public void functions()
    {
        MutableList<Pair<Integer, String>> pairs = Lists.mutable.of(Tuples.pair(1, "One"), Tuples.pair(2, "Two"), Tuples.pair(3, "Three"));
        MutableList<Pair<String, Integer>> swappedList = pairs.collect(each -> Tuples.pair(each.getTwo(), each.getOne()));
        Assert.assertEquals(Lists.mutable.of(Tuples.pair("One", 1), Tuples.pair("Two", 2), Tuples.pair("Three", 3)), swappedList);
    }

    @Test
    public void predicates()
    {
        MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Assert.assertEquals(Lists.mutable.of(4, 5, 6, 7, 8, 9, 10), numbers.select(each -> each > 3));
    }

    @Test
    public void procedures() throws Exception
    {
        Appendable appendable = new StringBuilder();

        MutableList<String> numbers = Lists.mutable.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        numbers.each(each ->
        {
            try
            {
                appendable.append(each);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });

        Assert.assertEquals("123456789", appendable.toString());
    }
}
