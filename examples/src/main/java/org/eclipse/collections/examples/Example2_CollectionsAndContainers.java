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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * Examples of (including inter-op, Mutable and Immutable):
 * 
 * <p>
 * 1) {@link FastList}
 * <p>
 * 2) {@link UnifiedSet}
 * <p>
 * 3) {@link UnifiedMap}
 * <p>
 * 4) {@link HashBag}
 * <p>
 * 5) {@link Multimap}
 */
public class Example2_CollectionsAndContainers
{
    @Test
    public void list()
    {
        List<String> jdkList = Arrays.asList("Cat", "Dog", "Bat", "Mouse", "Duck", "Goose", "Turkey", "Cricket", "Fly");

        MutableList<String> mutableList = null;
        Assert.assertEquals(jdkList, mutableList);

        List<String> interOp = null;
        Assert.assertEquals(jdkList, interOp);

        List<String> mutableToUnmodifiableList = null;
        Assert.assertEquals(jdkList, mutableToUnmodifiableList);
        Verify.assertThrows(UnsupportedOperationException.class, () -> mutableToUnmodifiableList.add("Tiger"));

        ImmutableList<String> immutableList = null;
        Assert.assertEquals(jdkList, immutableList);
    }

    @Test
    public void set()
    {
        // Arrays.asSet does not exist. Can add each element separately or create a list first.
        Set<String> jdkSet = new HashSet<>(Arrays.asList("Cat", "Dog", "Dog", "Duck", "Duck", "Goose"));

        MutableSet<String> mutableSet = null;
        Assert.assertEquals(jdkSet, mutableSet);

        Set<String> interOp = null;
        Assert.assertEquals(jdkSet, interOp);

        Set<String> mutableToUnmodifiableSet = null;
        Assert.assertEquals(jdkSet, mutableToUnmodifiableSet);
        Verify.assertThrows(UnsupportedOperationException.class, () -> mutableToUnmodifiableSet.add("Tiger"));

        ImmutableSet<String> immutableSet = null;
        Assert.assertEquals(jdkSet, immutableSet);
    }

    @Test
    public void map()
    {
        // No easy way to create a Map.
        Map<String, String> jdkMap = new HashMap<>();
        jdkMap.put("Animal", "Cat");
        jdkMap.put("Bird", "Duck");
        jdkMap.put("Insect", "Cricket");

        MutableMap<String, String> mutableMap = null;
        Assert.assertEquals(jdkMap, mutableMap);

        Map<String, String> interOp = null;
        Assert.assertEquals(jdkMap, interOp);

        Map<String, String> mutableToUnmodifiableMap = null;
        Assert.assertEquals(jdkMap, mutableToUnmodifiableMap);
        Verify.assertThrows(UnsupportedOperationException.class, () -> mutableToUnmodifiableMap.put("Fish", "Dolphin"));

        ImmutableMap<String, String> immutableMap = null;
        Assert.assertEquals(jdkMap, immutableMap);
    }

    @Test
    public void bag()
    {
        // No Bag in JDK.
        // Bag like behavior in JDK can be achieved by Map<T, Integer>
        // Bag in Eclipse Collections is implemented as ObjectIntMap (which is primitive)
        // Create bag with elements: "Cat", "Dog", "Dog", "Dog", "Duck", "Duck", "Goose"
        ListIterable<String> animals = Lists.mutable.of("Cat", "Dog", "Dog", "Dog", "Duck", "Duck", "Goose");
        MutableBag<String> bag = null;
        Assert.assertEquals(1, bag.occurrencesOf("Cat"));
        Assert.assertEquals(3, bag.occurrencesOf("Dog"));
        Assert.assertEquals(2, bag.occurrencesOf("Duck"));
        Assert.assertEquals(1, bag.occurrencesOf("Goose"));

        // Add more cats.
        bag.addOccurrences("Cat", 2);
        Assert.assertEquals(3, bag.occurrencesOf("Cat"));
    }

    @Test
    public void multimap()
    {
        // No Multimap in JDK.
        // Multimap like behavior in JDK can be achieved by using Map<K, Collection<V>>
        // Multimap in Eclipse Collections is implemented as MutableMap<K, RichIterable<V>>

        ListIterable<Integer> listIterable = Lists.mutable.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5);
        ListMultimap<String, Integer> listMultimap = null;
        Assert.assertEquals(Lists.mutable.of(1, 3, 3, 3, 5, 5, 5, 5, 5), listMultimap.get("Odd"));
        Assert.assertEquals(Lists.mutable.of(2, 2, 4, 4, 4, 4), listMultimap.get("Even"));

        MutableSetMultimap<String, Integer> setMultimap = null;
        Verify.assertSetsEqual(Sets.mutable.of(1, 3, 3, 3, 5, 5, 5, 5, 5), setMultimap.get("Odd"));
        Verify.assertSetsEqual(Sets.mutable.of(2, 2, 4, 4, 4, 4), setMultimap.get("Even"));

        MutableBagMultimap<String, Integer> bagMultimap = null;
        Verify.assertBagsEqual(Bags.mutable.of(1, 3, 3, 3, 5, 5, 5, 5, 5), bagMultimap.get("Odd"));
        Verify.assertBagsEqual(Bags.mutable.of(2, 2, 4, 4, 4, 4), bagMultimap.get("Even"));
    }
}