package com.daltonbaird.wnschat.functional;

/**
 * Created by Dalton on 3/27/2016.
 */
public interface TernaryAction<TParam1, TParam2, TParam3>
{
    /** Invokes the action with the specified arguments */
    void invoke(TParam1 param1, TParam2 param2, TParam3 param3);
}
