package com.daltonbaird.wnschat.utilities;

import com.daltonbaird.wnschat.functional.Action1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/27/2016.
 */
public class EventHandler1<A extends Action1<T>, T>
{
    /** A list of registered event listeners */
    protected List<A> listeners;

    public EventHandler1()
    {
        this.listeners = new ArrayList<A>();
    }

    /**
     * Fires the event, invoking all registered event listeners with the specified data
     * @param data The data to pass to the event listeners
     */
    public void fire(T data)
    {
        for (A action : this.listeners)
            action.invoke(data);
    }

    /**
     * Adds an event listener
     * @param listener The listener to add
     */
    public void add(A listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Removes an event listener
     * @param listener The listener to remove
     * @return True if the listener existed
     */
    public boolean remove(A listener)
    {
        return this.listeners.remove(listener);
    }
}
