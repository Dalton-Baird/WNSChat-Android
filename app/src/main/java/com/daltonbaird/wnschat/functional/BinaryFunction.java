package com.daltonbaird.wnschat.functional;

/**
 * Functional interface, since this is Java 7 which doesn't have them yet :(
 *
 * Created by Dalton on 3/26/2016.
 */
public interface BinaryFunction<TResult, TParam1, TParam2>
{
    /** Call this to get the result of the function with the specified arguments */
    TResult invoke(TParam1 param1, TParam2 param2);
}
