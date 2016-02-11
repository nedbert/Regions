package ru.nukkit.regions.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import ru.nukkit.regions.Regions;
import ru.nukkit.regions.util.Message;

@CmdDefine(command = "select", alias = "sel",subCommands = {} , permission = "region.select", description = Message.SEL_DESC)
public class CmdSelect extends Cmd {
    @Override
    public boolean execute(CommandSender sender, Player player, String[] args) {
        if (args.length>0) return false;
        return (Regions.getSelector().toggleSelMode(player) ? Message.SEL_ENABLE : Message.SEL_DISABLE).print(player);
    }

}

