package dijon.infuseRevampS2.Commands.OP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class infusegivetabcomplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("strength");
            completions.add("heart");
            completions.add("haste");
            completions.add("invis");
            completions.add("feather");
            completions.add("frost");
            completions.add("thunder");
            completions.add("regen");
            completions.add("ocean");
            completions.add("fire");
            completions.add("emerald");
            completions.add("speed");
            completions.add("ender");
            completions.add("earth");
        }

        return completions;
    }
}