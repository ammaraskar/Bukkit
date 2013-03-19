package org.bukkit.command;

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

    protected SubCommand(String name, String description, String usageMessage, List<String> aliases, Command rootCommand) {
        super(name, description, usageMessage, aliases);
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
     * Gets the absolute root of this command, guaranteed to be a Command
     * @return The absolute root command
     */
    public Command getAbsoluteRootCommand() {
        Command root = this.rootCommand;
        while (root instanceof SubCommand) {
            root = ((SubCommand) root).getRootCommand();
        }
        return root;
    }

    /* Override the CommandMap based registration methods to avoid inadvertently registering this as a normal
       command
    */
    @Override
    public boolean register(CommandMap commandMap) {
        this.rootCommand.addSubCommand(this);
        return true;
    }

    @Override
    public boolean unregister(CommandMap commandMap) {
        this.rootCommand.removeSubCommand(this);
        return true;
    }

    @Override
    public boolean isRegistered() {
        return this.rootCommand.getSubCommands().contains(this);
    }
}
