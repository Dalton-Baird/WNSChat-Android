package com.daltonbaird.wnschat.functional;

/**
 * Functional interface, since this is Java 7 which doesn't have them yet :(
 *
 * Created by Dalton on 3/27/2016.
 */
public interface Action
{
    /** Call this to run the action */
    void invoke();
}
