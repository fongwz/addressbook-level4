package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListByMostSearchedCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NextMeetingCommand;
import seedu.address.logic.commands.PrefCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetUniqueKeyCommand;
import seedu.address.logic.commands.SetupAsanaCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Meeting;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    /*@Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }*/

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    //@@author Sri-vatsa
    @Test
    public void parseCommand_listByMostSearched() throws Exception {
        assertTrue(parser.parseCommand(ListByMostSearchedCommand.COMMAND_WORD) instanceof ListByMostSearchedCommand);
        assertTrue(parser.parseCommand(ListByMostSearchedCommand.COMMAND_ALIAS) instanceof ListByMostSearchedCommand);
        assertTrue(parser.parseCommand(ListByMostSearchedCommand.COMMAND_WORD + " 3")
                instanceof ListByMostSearchedCommand);
    }

    @Test
    public void parseCommand_addMeeting() throws Exception {

        //Create new Id arrayList
        ArrayList<InternalId> ids = new ArrayList<>();
        ids.add(new InternalId(1));

        //create a new localDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
        String dateTime = "27/10/2020 1800";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        //Create an expected new meeting
        Meeting newMeeting = new Meeting(localDateTime, "Computing", "Coding Project", ids);

        //create new user-input based add meeting command
        AddMeetingCommand command = (AddMeetingCommand)
                parser.parseCommand(AddMeetingCommand.COMMAND_WORD + " "
                        + PREFIX_DATE + " 27/10/2020 "
                        + PREFIX_TIME + " 1800 "
                        + PREFIX_LOCATION + " Computing "
                        + PREFIX_NOTES + " Coding Project "
                        + PREFIX_PERSON + " 1");

        assertEquals(new AddMeetingCommand(newMeeting), command);
    }

    @Test
    public void parseCommand_deleteTag_one() throws Exception {
        String [] tags = {"friends"};
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(
                DeleteTagCommand.COMMAND_WORD + " " + "friends");
        assertEquals(new DeleteTagCommand(tags), command);
    }

    @Test
    public void parseCommand_deleteTag_multiple() throws Exception {
        String [] tags = {"friends", "colleagues"};
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(
                DeleteTagCommand.COMMAND_WORD + " " + "friends" + " " + "colleagues");
        assertEquals(new DeleteTagCommand(tags), command);
    }

    @Test
    public void parseCommand_setUniqueKey() throws Exception {
        String accessCode = "0/b62305d262c673af5c042bfad54ef832";
        SetUniqueKeyCommand command = (SetUniqueKeyCommand) parser.parseCommand(
                SetUniqueKeyCommand.COMMAND_WORD + " " + accessCode);
        assertEquals(new SetUniqueKeyCommand(accessCode), command);
    }

    @Test
    public void parseCommand_setupAsana() throws Exception {
        assertTrue(parser.parseCommand(SetupAsanaCommand.COMMAND_WORD) instanceof SetupAsanaCommand);
        assertTrue(parser.parseCommand(SetupAsanaCommand.COMMAND_ALIAS) instanceof SetupAsanaCommand);
        assertTrue(parser.parseCommand(SetupAsanaCommand.COMMAND_WORD + " 2") instanceof SetupAsanaCommand);
    }

    //@@author

    @Test
    public void parseCommand_nextMeeting() throws Exception {
        assertTrue(parser.parseCommand(NextMeetingCommand.COMMAND_WORD) instanceof NextMeetingCommand);
        assertTrue(parser.parseCommand(NextMeetingCommand.COMMAND_ALIAS) instanceof NextMeetingCommand);
        assertTrue(parser.parseCommand(NextMeetingCommand.COMMAND_WORD + " a") instanceof NextMeetingCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_pref() throws Exception {
        String prefKey = "AddressBookName";
        String prefValue = "Name";
        PrefCommand commandRead = (PrefCommand) parser.parseCommand(PrefCommand.COMMAND_WORD + " "
                + prefKey);
        assertEquals(new PrefCommand(prefKey, ""), commandRead);
        PrefCommand commandEdit = (PrefCommand) parser.parseCommand(PrefCommand.COMMAND_WORD + " "
                + prefKey + " " + prefValue);
        assertEquals(new PrefCommand(prefKey, prefValue), commandEdit);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
