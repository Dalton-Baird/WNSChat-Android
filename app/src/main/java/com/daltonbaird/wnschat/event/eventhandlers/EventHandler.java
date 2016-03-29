package com.daltonbaird.wnschat.event.eventhandlers;

import com.daltonbaird.wnschat.functional.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/27/2016.
 */
public class EventHandler
{
    /** A list of registered event listeners */
    protected List<Action> listeners;

    public EventHandler()
    {
        this.listeners = new ArrayList<Action>();
    }

    /**
     * Fires the event, invoking all registered event listeners with the specified data
     */
    public void fire()
    {
        for (Action action : this.listeners)
            action.invoke();
    }

    /**
     * Adds an event listener
     * @param listener The listener to add
     */
    public void add(Action listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Removes an event listener
     * @param listener The listener to remove
     * @return True if the listener existed
     */
    public boolean remove(Action listener)
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
