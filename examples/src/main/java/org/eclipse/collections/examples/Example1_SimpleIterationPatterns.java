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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectLongHashMap;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * Examples of:
 * <p>
 * 1) <b>Transform/Map</b>: collect, flatCollect
 * <p>
 * 2) <b>Filter/Boolean logic</b>: select, reject, partition
 * <p>
 * 3) <b>Filter/Boolean logic</b>: count, detect, anySatisfy, allSatisfy
 * <p>
 * 4) <b>Math Operations</b>: sumByInt, sumOfInt
 */
@SuppressWarnings("unchecked")
public class Example1_SimpleIterationPatterns
{
    /*
     **************************
     * Patterns with Functions*
     **************************
     */
    @Test
    public void collect()
    {
        MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);

        List<String> numbersAsString = new ArrayList<>();
        for (Integer number : numbers)
        {
            numbersAsString.add(number.toString());
        }

        Assert.assertEquals(Lists.mutable.of("1", "2", "3", "4", "5"), numbersAsString);
    }

    @Test
    public void flatCollect()
    {
        MutableList<List<Integer>> numbersByDivisibility = Lists.mutable.of(
                Lists.mutable.of(2, 4, 6, 8, 10), // divisible by 2 (evens)
                Lists.mutable.of(3, 6, 9), // divisible by 3
                Lists.mutable.of(4, 8), // divisible by 4
                Lists.mutable.of(5, 10)); // divisible by 5

        List<String> numbersByDivisibilityAsString = new ArrayList<>();
        for (List<Integer> innerList : numbersByDivisibility)
        {
            for (Integer number : innerList)
            {
                numbersByDivisibilityAsString.add(number.toString());

            }
        }

        Verify.assertListsEqual(
                Arrays.asList("2", "4", "6", "8", "10", "3", "6", "9", "4", "8", "5", "10"),
                numbersByDivisibilityAsString);
    }

    /*
     ***************************
     * Patterns with Predicates*
     ***************************
     */

    @Test
    public void selectReject()
    {
        MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);

        Stream<Integer> numbersAsStream = numbers.stream();
        List<Integer> evens = numbersAsStream.filter(each -> each % 2 == 0).collect(Collectors.toList());
        Assert.assertEquals(Lists.mutable.of(2, 4), evens);

        List<Integer> odds = numbers.stream().filter(each -> each % 2 != 0).collect(Collectors.toList());
        Assert.assertEquals(Lists.mutable.of(1, 3, 5), odds);
    }

    @Test
    public void partition()
    {
        MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);

        Map<Boolean, List<Integer>> evenOddsPartitioned = numbers.stream().collect(Collectors.partitioningBy(each -> each % 2 == 0));

        List<Integer> evens = evenOddsPartitioned.get(true);
        List<Integer> odds = evenOddsPartitioned.get(false);

        Assert.assertEquals(Lists.immutable.of(2, 4), evens);
        Assert.assertEquals(Lists.fixedSize.of(1, 3, 5), odds);
    }

    @Test
    public void countDetectAnySatisfyAllSatisfy()
    {
        MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);

        java.util.function.Predicate<? super Integer> evenPredicate = each -> each % 2 == 0;

        Assert.assertEquals(2, numbers.stream().filter(evenPredicate).count());
        Assert.assertEquals(Integer.valueOf(2), numbers.stream().filter(evenPredicate).findFirst().get());
        Assert.assertTrue(numbers.stream().anyMatch(evenPredicate));
        Assert.assertFalse(numbers.stream().allMatch(evenPredicate));
    }

    /*
     *****************
     * Math Functions*
     *****************
     */
    @Test
    public void sumByIntSumOfInt()
    {
        MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);
        MutableObjectLongMap<String> sumByInt = new ObjectLongHashMap<String>();
        numbers.each(each ->
        {
            if (each % 2 == 0)
            {
                sumByInt.addToValue("Even", each);
            }
            if (each % 2 != 0)
            {
                sumByInt.addToValue("Odd", each);
            }
        });

        Assert.assertEquals(ObjectLongHashMap.newWithKeysValues("Even", 6, "Odd", 9), sumByInt);

        int sum = 0;
        for (Integer each : numbers)
        {
            sum += (2 * each);
        }
        Assert.assertEquals(30, sum);
    }
}
