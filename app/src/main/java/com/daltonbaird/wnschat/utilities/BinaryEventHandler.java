package com.daltonbaird.wnschat.utilities;

import com.daltonbaird.wnschat.functional.BinaryAction;
import com.daltonbaird.wnschat.functional.UnaryAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/27/2016.
 */
public class BinaryEventHandler<T1, T2>
{
    /** A list of registered event listeners */
    protected List<BinaryAction<T1, T2>> listeners;

    public BinaryEventHandler()
    {
        this.listeners = new ArrayList<BinaryAction<T1, T2>>();
    }

    /**
     * Fires the event, invoking all registered event listeners with the specified data
     * @param param1 The first parameter to pass to the event listeners
     * @param param2 The second parameter to pass to the event listeners
     */
    public void fire(T1 param1, T2 param2)
    {
        for (BinaryAction<T1, T2> action : this.listeners)
            action.invoke(param1, param2);
    }

    /**
     * Adds an event listener
     * @param listener The listener to add
     */
    public void add(BinaryAction<T1, T2> listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Removes an event listener
     * @param listener The listener to remove
     * @return True if the listener existed
     */
    public boolean remove(BinaryAction<T1, T2> listener)
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
