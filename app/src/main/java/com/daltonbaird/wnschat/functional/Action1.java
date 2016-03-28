package com.daltonbaird.wnschat.functional;

/**
 * Created by Dalton on 3/27/2016.
 */
public interface Action1<TParam>
{
    /** Invokes the action with the specified argument */
    void invoke(TParam param);
}
