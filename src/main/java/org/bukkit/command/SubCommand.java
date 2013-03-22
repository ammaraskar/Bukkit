package org.bukkit.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// TODO: Improve these javadocs, I really suck at explaining in a concise manner

/**
 * A sub command associated under a root command. The root command may be a Command or another SubCommand.
 * This is in all respects a full fledged command supporting aliases, permissions, anything the normal Command class supports.
 * The CommandMap class is responsible for dispatching to a sub command if the proper arguments are supplied in the command
 * text.
 */
public abstract class SubCommand extends Command {

    private Command rootCommand;

    protected SubCommand(String name, Command rootCommand) {
        super(name);
        this.rootCommand = rootCommand;
    }

    /**
     * Gets the root command of this sub command, the root command may or may not be another sub command
     * @return The root command
     * @see #getAbsoluteRootCommand()
     */
    public Command getRootCommand() {
        return this.rootCommand;
    }

    /**
     * Sets the root command of this sub command, the root command may or may not be another sub command
     * @param rootCommand The root command
     */
    public void setRootCommand(Command rootCommand) {
        this.rootCommand = rootCommand;
    }

    /**
     * Gets the absolute root of this command, guaranteed to be a Command
     * @return The absolute root command
     * @see #getRootCommand()
     */
    public Command getAbsoluteRootCommand() {
        Command root = this.rootCommand;
        while (root instanceof SubCommand) {
            root = ((SubCommand) root).getRootCommand();
        }
        return root;
    }

    /**
     * Gets all root commands for this sub command
     * @return A list of the root commands, the top level Command class is guaranteed to be in this list
     */
    public List<Command> getRootCommands() {
        Command root = this.rootCommand;
        List<Command> commands = new ArrayList<Command>();

        while (root instanceof SubCommand) {
            root = ((SubCommand) root).getRootCommand();
            commands.add(root);
        }
        commands.add(root);
        Collections.reverse(commands);
        return commands;
    }

    @Override
    public boolean testPermissionSilent(CommandSender target) {
        return getRootCommand().testPermissionSilent(target) && super.testPermissionSilent(target);
    }

}
