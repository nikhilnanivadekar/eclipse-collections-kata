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

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.DoubleList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.DoubleSet;
import org.eclipse.collections.api.set.primitive.IntSet;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.DoubleHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * Examples of:
 * <p>
 * 1) {@link IntList}, {@link DoubleList}
 * <p>
 * 2) {@link IntSet}, {@link DoubleSet}
 */
public class Example4_PrimitiveSpecialization
{
	@Test
	public void primitiveList()
	{
		MutableList<Integer> ints = Lists.mutable.of(1, 2, 3, 4, 5);
		IntList primitiveInts = ints.collectInt(each -> each);
		Assert.assertEquals(new IntArrayList(1, 2, 3, 4, 5), primitiveInts);

		MutableList<Double> doubles = Lists.mutable.of(1.0, 2.0, 3.0, 4.0, 5.0);
		DoubleList primitiveDoubles = doubles.collectDouble(PrimitiveFunctions.unboxDoubleToDouble());
		Assert.assertEquals(new DoubleArrayList(1.0, 2.0, 3.0, 4.0, 5.0), primitiveDoubles);
	}

	@Test
	public void primitiveSet()
	{
		MutableSet<Integer> ints = Sets.mutable.of(1, 2, 3, 4, 5);
		IntSet primitiveInts = ints.collectInt(PrimitiveFunctions.unboxIntegerToInt());
		Assert.assertEquals(new IntHashSet(1, 2, 3, 4, 5), primitiveInts);

		MutableSet<Double> doubles = Sets.mutable.of(1.0, 2.0, 3.0, 4.0, 5.0);
		DoubleSet primitiveDoubles = doubles.collectDouble(each -> each);
		Assert.assertEquals(new DoubleHashSet(1.0, 2.0, 3.0, 4.0, 5.0), primitiveDoubles);
	}
}
