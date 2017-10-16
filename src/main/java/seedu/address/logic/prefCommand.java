package seedu.address.logic;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Edits the details of an existing person in the address book.
 */
public class prefCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "pref";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits user preferences "
            + "Parameters: KEY NEW_VALUE"
            + "Example: " + " backgroundColour" + " #ff0000";

    public static final String MESSAGE_EDIT_PREF_SUCCESS = "Edited preference: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Please enter the new value for the preference";

    /**
     */
    public prefCommand() {
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        return new CommandResult("Command not implemented");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof prefCommand)) {
            return false;
        }
        return true;
    }
}
