package tech.presstabs.tabsquests.quests;

import org.bukkit.entity.Player;

//@QuestHandler (tbh probably should've just done Class<? extends AbstractQuest> in my register method.
public abstract class AbstractQuest {

    //@QuestStart
    public abstract void start(Player player);

    //@QuestQuit
    public abstract void quit(Player player);

    //@QuestFinish
    public abstract void finish(Player player);

    //@Reward
    public abstract void reward(Player player);
}
