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

import java.util.Arrays;
import java.util.List;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.ObjectLongMap;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.mutable.ListAdapter;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectLongHashMap;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * /**
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
	public void collectFlatCollect()
	{
		MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);

		Assert.assertEquals(Lists.mutable.of("1", "2", "3", "4", "5"), numbers.collect(Object::toString));
		// Assert.assertEquals(Lists.mutable.of("1", "2", "3", "4", "5"), numbers.collect(each -> each.toString()));

		MutableList<List<Integer>> numbersByDivisibility = Lists.mutable.of(
		        Lists.mutable.of(2, 4, 6, 8, 10), // divisible by 2 (evens)
		        Lists.mutable.of(3, 6, 9), // divisible by 3
		        Lists.mutable.of(4, 8), // divisible by 4
		        Lists.mutable.of(5, 10)); // divisible by 5

		List<String> numbersByDivisibilityAsString = numbersByDivisibility.flatCollect(each -> ListAdapter.adapt(each).collect(Object::toString));

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
	public void selectRejectPartition()
	{
		MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);

		Predicate<Integer> evenPredicate = each -> each % 2 == 0;

		Assert.assertEquals(Lists.mutable.of(2, 4), numbers.select(evenPredicate));
		Assert.assertEquals(Lists.mutable.of(1, 3, 5), numbers.reject(evenPredicate));

		PartitionMutableList<Integer> partitionedList = numbers.partition(evenPredicate);
		List<Integer> evensInOnePass = partitionedList.getSelected();
		List<Integer> oddsInOnePass = partitionedList.getRejected();

		Assert.assertEquals(
		        Lists.immutable.of(Lists.fixedSize.of(2, 4), Lists.fixedSize.of(1, 3, 5)),
		        Lists.mutable.of(evensInOnePass, oddsInOnePass));
	}

	@Test
	public void countDetectAnySatisfyAllSatisfy()
	{
		MutableList<Integer> numbers = Lists.mutable.of(1, 2, 3, 4, 5);

		Predicate<? super Integer> evenPredicate = each -> each % 2 == 0;

		Assert.assertEquals(2, numbers.count(evenPredicate));
		Assert.assertEquals(Integer.valueOf(2), numbers.detect(evenPredicate));
		Assert.assertTrue(numbers.anySatisfy(evenPredicate));
		Assert.assertFalse(numbers.allSatisfy(evenPredicate));
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
		ObjectLongMap<String> sumByInt = numbers.sumByInt(each -> each % 2 == 0 ? "Even" : "Odd", each -> each);
		// ObjectLongMap<String> sumByInt = numbers.sumByInt(Functions.ifElse(each -> each % 2 == 0, Functions.getFixedValue("Even"), Functions.getFixedValue("Odd")), each -> each);

		Assert.assertEquals(ObjectLongHashMap.newWithKeysValues("Even", 6, "Odd", 9), sumByInt);

		Assert.assertEquals(30, numbers.sumOfInt(each -> each * 2));
	}
}
