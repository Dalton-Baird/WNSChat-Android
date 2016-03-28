package com.daltonbaird.wnschat.functional;

/**
 * Functional interface, since this is Java 7 which doesn't have them yet :(
 *
 * Created by Dalton on 3/26/2016.
 */
public interface UnaryFunction<TResult, TParam1>
{
    /** Call this to get the result of the function with the specified argument */
    TResult invoke(TParam1 param1);
}
