package com.daltonbaird.wnschat.event.eventhandlers;

import com.daltonbaird.wnschat.functional.TernaryAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/27/2016.
 */
public class TernaryEventHandler<T1, T2, T3>
{
    /** A list of registered event listeners */
    protected List<TernaryAction<T1, T2, T3>> listeners;

    public TernaryEventHandler()
    {
        this.listeners = new ArrayList<TernaryAction<T1, T2, T3>>();
    }

    /**
     * Fires the event, invoking all registered event listeners with the specified data
     * @param param1 The first parameter to pass to the event listeners
     * @param param2 The second parameter to pass to the event listeners
     * @param param3 The third parameter to pass to the event listeners
     */
    public void fire(T1 param1, T2 param2, T3 param3)
    {
        for (TernaryAction<T1, T2, T3> action : this.listeners)
            action.invoke(param1, param2, param3);
    }

    /**
     * Adds an event listener
     * @param listener The listener to add
     */
    public void add(TernaryAction<T1, T2, T3> listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Removes an event listener
     * @param listener The listener to remove
     * @return True if the listener existed
     */
    public boolean remove(TernaryAction<T1, T2, T3> listener)
    {
        return this.listeners.remove(listener);
    }

    /**
     * Clears all of the registered event listeners
     */
    public void clear()
    {
        this.listeners.clear();
    }
}
