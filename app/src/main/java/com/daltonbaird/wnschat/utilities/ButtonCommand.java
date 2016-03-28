package com.daltonbaird.wnschat.utilities;

import com.daltonbaird.wnschat.eventhandlers.EventHandler;
import com.daltonbaird.wnschat.functional.Action;
import com.daltonbaird.wnschat.functional.Predicate;
import com.daltonbaird.wnschat.functional.UnaryPredicate;
import com.daltonbaird.wnschat.functional.UnaryAction;

/**
 * Java version of Dalton's C# ButtonCommand class.
 *
 * Created by Dalton on 3/28/2016.
 */
public class ButtonCommand<TParam>
{
    /** The action to execute */
    protected UnaryAction<TParam> executeAction;
    /** A predicate to determine when the action can be executed */
    protected UnaryPredicate<TParam> canExecutePredicate;

    /**
     * Creates a new ButtonCommand with the specified execute and canExecute delegates.
     * @param execute A delegate to be called on execute
     * @param canExecute A delegate to be called to determine the result of canExecute
     */
    public ButtonCommand(UnaryAction<TParam> execute, UnaryPredicate<TParam> canExecute)
    {
        this.executeAction = execute;
        this.canExecutePredicate = canExecute;
    }

    /**
     * Creates a new ButtonCommand with the specified execute and canExecute delegates.
     * @param execute A delegate to be called on execute
     * @param canExecute A delegate to be called to determine the result of canExecute
     */
    public ButtonCommand(final Action execute, UnaryPredicate<TParam> canExecute)
    {
        this.executeAction = new UnaryAction<TParam>()
        {
            @Override
            public void invoke(TParam param)
            {
                execute.invoke();
            }
        };

        this.canExecutePredicate = canExecute;
    }

    /**
     * Creates a new ButtonCommand with the specified execute and canExecute delegates.
     * @param execute A delegate to be called on execute
     * @param canExecute A delegate to be called to determine the result of canExecute
     */
    public ButtonCommand(final Action execute, final Predicate canExecute)
    {
        this.executeAction = new UnaryAction<TParam>()
        {
            @Override
            public void invoke(TParam param)
            {
                execute.invoke();
            }
        };

        this.canExecutePredicate = new UnaryPredicate<TParam>()
        {
            @Override
            public boolean invoke(TParam param)
            {
                return canExecute.invoke();
            }
        };
    }

    /**
     * Creates a new ButtonCommand with the specified execute delegate that can always be executed.
     * @param execute A delegate to be called on execute
     */
    public ButtonCommand(UnaryAction<TParam> execute)
    {
        this.executeAction = execute;

        this.canExecutePredicate = new UnaryPredicate<TParam>()
        {
            @Override
            public boolean invoke(TParam param)
            {
                return true;
            }
        };
    }

    /**
     * Creates a new ButtonCommand with the specified execute delegate that can always be executed.
     * @param execute A delegate to be called on execute
     */
    public ButtonCommand(final Action execute)
    {
        this.executeAction = new UnaryAction<TParam>()
        {
            @Override
            public void invoke(TParam param)
            {
                execute.invoke();
            }
        };

        this.canExecutePredicate = new UnaryPredicate<TParam>()
        {
            @Override
            public boolean invoke(TParam param)
            {
                return true;
            }
        };
    }

    /**
     * Allows a ButtonCommand to execute any number of other ButtonCommands.  The ButtonCommands must
     * all return true for canExecute in order for this command's canExecute to return true.
     * @param buttonCommands A list of ButtonCommands to execute
     */
    public ButtonCommand(final ButtonCommand ... buttonCommands)
    {
        this.executeAction = new UnaryAction<TParam>()
        {
            @Override
            public void invoke(TParam param)
            {
                for (ButtonCommand command : buttonCommands)
                    command.execute(param);
            }
        };

        this.canExecutePredicate = new UnaryPredicate<TParam>()
        {
            @Override
            public boolean invoke(TParam param)
            {
                for (ButtonCommand command : buttonCommands)
                    if (!command.canExecute(param))
                        return false;
                return true;
            }
        };
    }

    /**
     * If this returns true, the command can be executed
     * @param param The parameter to pass to the command
     * @return True if this command can be executed
     */
    public boolean canExecute(TParam param)
    {
        return this.canExecutePredicate.invoke(param);
    }

    /**
     * Executes this ButtonCommand
     * @param param The parameter to pass to the command
     */
    public void execute(TParam param)
    {
        this.executeAction.invoke(param);
    }

    /** Should be fired when the button's canExecute condition changes. */
    public final EventHandler canExecuteChanged = new EventHandler();

    /**
     * Call this to fire the canExecuteChanged event.  This is only here so it matches the C# version.
     */
    public void onCanExecuteChanged()
    {
        this.canExecuteChanged.fire();
    }
}
