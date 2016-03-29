package com.daltonbaird.wnschat.utilities;

import java.util.List;

/**
 * An interface for a list that has events that are fired when items are added, removed, or changed.
 * The default Android one wasn't working.
 *
 * Created by Dalton on 3/28/2016.
 */
public interface ObservableList<E> extends List<E>
{
    //TODO: If I ever add any ObservableList functions, require them here
}
