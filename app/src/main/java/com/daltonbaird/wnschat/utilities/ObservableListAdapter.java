package com.daltonbaird.wnschat.utilities;

import android.support.annotation.NonNull;

import com.daltonbaird.wnschat.event.EventArgs;
import com.daltonbaird.wnschat.event.eventhandlers.EventHandler;
import com.daltonbaird.wnschat.event.eventhandlers.UnaryEventHandler;
import com.daltonbaird.wnschat.functional.UnaryAction;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * An adapter for a list to make it implement the ObservableList interface.
 * The default Android one wasn't working.
 *
 * Created by Dalton on 3/28/2016.
 */
public class ObservableListAdapter<E> implements ObservableList<E>
{
    /** The list that this observable list adapter is adapting */
    protected List<E> baseList;

    /**
     * Constructs a new ObservableListAdapter which adapts the specified list
     * @param baseList
     */
    public ObservableListAdapter(List<E> baseList)
    {
        this.baseList = baseList;

        /* //Doing this may lead to bad side effects
        //Hook up event handlers to fire the listChanged handler
        this.elementChanged.add(new UnaryAction<ElementChangedEventArgs<E>>()
        {
            @Override
            public void invoke(ElementChangedEventArgs<E> e)
            {
                ObservableListAdapter.this.listChanged.fire();
            }
        });

        this.elementAdded.add(new UnaryAction<ElementAddedEventArgs<E>>()
        {
            @Override
            public void invoke(ElementAddedEventArgs<E> e)
            {
                ObservableListAdapter.this.listChanged.fire();
            }
        });

        this.elementRemoved.add(new UnaryAction<ElementRemovedEventArgs<E>>()
        {
            @Override
            public void invoke(ElementRemovedEventArgs<E> e)
            {
                ObservableListAdapter.this.listChanged.fire();
            }
        });
        */
    }

    //List<E> Interface Stuff

    @Override
    public void add(int i, E e)
    {
        this.baseList.add(i, e);
        this.elementAdded.fire(new ElementAddedEventArgs<E>(e, i));
        this.listChanged.fire();
    }

    @Override
    public boolean add(E e)
    {
        boolean result = this.baseList.add(e);
        this.elementAdded.fire(new ElementAddedEventArgs<E>(e, this.baseList.size()));
        this.listChanged.fire();

        return result;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection)
    {
        boolean modified = false;

        for (E e : collection)
        {
            this.add(i++, e);
            modified = true;
        }

        return modified;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection)
    {
        boolean modified = false;

        for (E e : collection)
        {
            this.add(e);
            modified = true;
        }

        return modified;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.size(); i++)
            this.elementRemoved.fire(new ElementRemovedEventArgs<E>(this.get(i), i));

        this.baseList.clear();

        this.listChanged.fire();
    }

    @Override
    public boolean contains(Object o)
    {
        return this.baseList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection)
    {
        return this.baseList.containsAll(collection);
    }

    @Override
    public E get(int i)
    {
        return this.baseList.get(i);
    }

    @Override
    public int indexOf(Object o)
    {
        return this.baseList.indexOf(o);
    }

    @Override
    public boolean isEmpty()
    {
        return this.baseList.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<E> iterator()
    {
        return this.baseList.iterator();
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return this.baseList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator()
    {
        return this.baseList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int i)
    {
        return this.baseList.listIterator(i);
    }

    @Override
    public E remove(int i)
    {
        E removed = this.baseList.remove(i);
        this.elementRemoved.fire(new ElementRemovedEventArgs<E>(removed, i));
        this.listChanged.fire();

        return removed;
    }

    @Override
    public boolean remove(Object o)
    {
        int index = this.indexOf(o);
        E removed = this.get(index);
        this.elementRemoved.fire(new ElementRemovedEventArgs<E>(removed, index));
        this.listChanged.fire();

        return this.baseList.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Event handling isn't implemented for this method yet!");

        //return this.baseList.removeAll(collection); //TODO: This doesn't use the events!
    }

    @Override
    public boolean retainAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Event handling isn't implemented for this method yet!");

        //return this.baseList.retainAll(collection); //TODO: This doesn't use the events!
    }

    @Override
    public E set(int i, E e)
    {
        E elementChanged = this.baseList.set(i, e);
        this.elementChanged.fire(new ElementChangedEventArgs<E>(e, i));
        this.listChanged.fire();

        return elementChanged;
    }

    @Override
    public int size()
    {
        return this.baseList.size();
    }

    @NonNull
    @Override
    public List<E> subList(int i, int i1)
    {
        return this.baseList.subList(i, i1);
    }

    @NonNull
    @Override
    public Object[] toArray()
    {
        return this.baseList.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] ts)
    {
        return this.baseList.toArray(ts);
    }

    //END List<E> Interface Stuff

    //Events

    /** Fired when the list is changed */
    public final EventHandler listChanged = new EventHandler();

    /** Fired when an element in the list is changed.  This should be called manually when a list element is changed */
    public final UnaryEventHandler<ElementChangedEventArgs<E>> elementChanged = new UnaryEventHandler<ElementChangedEventArgs<E>>();

    /** Fired when an element is added to the list */
    public final UnaryEventHandler<ElementAddedEventArgs<E>> elementAdded = new UnaryEventHandler<ElementAddedEventArgs<E>>();

    /** Fired when an element is removed from the list */
    public final UnaryEventHandler<ElementRemovedEventArgs<E>> elementRemoved = new UnaryEventHandler<ElementRemovedEventArgs<E>>();

    //EventArgs classes

    public static class ElementChangedEventArgs<E> extends EventArgs
    {
        /** The element that was changed */
        public final E elementChanged;
        /** The index of the element that was changed */
        public final int index;

        public ElementChangedEventArgs(E elementChanged, int index)
        {
            this.elementChanged = elementChanged;
            this.index = index;
        }
    }

    public static class ElementAddedEventArgs<E> extends ElementChangedEventArgs<E>
    {
        public ElementAddedEventArgs(E elementChanged, int index)
        {
            super(elementChanged, index);
        }
    }

    public static class ElementRemovedEventArgs<E> extends ElementChangedEventArgs<E>
    {
        public ElementRemovedEventArgs(E elementChanged, int index)
        {
            super(elementChanged, index);
        }
    }
}
