package com.daltonbaird.wnschat.utilities;

import com.daltonbaird.wnschat.functional.UnaryAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/27/2016.
 */
public class UnaryEventHandler<T>
{
    /** A list of registered event listeners */
    protected List<UnaryAction<T>> listeners;

    public UnaryEventHandler()
    {
        this.listeners = new ArrayList<UnaryAction<T>>();
    }

    /**
     * Fires the event, invoking all registered event listeners with the specified data
     * @param param The data to pass to the event listeners
     */
    public void fire(T param)
    {
        for (UnaryAction<T> action : this.listeners)
            action.invoke(param);
    }

    /**
     * Adds an event listener
     * @param listener The listener to add
     */
    public void add(UnaryAction<T> listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Removes an event listener
     * @param listener The listener to remove
     * @return True if the listener existed
     */
    public boolean remove(UnaryAction<T> listener)
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
