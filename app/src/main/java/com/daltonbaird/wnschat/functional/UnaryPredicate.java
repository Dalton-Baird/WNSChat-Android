package com.daltonbaird.wnschat.functional;

/**
 * Functional interface, since this is Java 7 which doesn't have them yet :(
 *
 * Created by Dalton on 3/28/2016.
 */
public interface UnaryPredicate<TParam1>
{
    /**
     * Call this to get the result of the predicate
     * @param param1 The first parameter of the predicate
     * @return True or false, based on the predicate's implementation
     */
    boolean invoke(TParam1 param1);
}
