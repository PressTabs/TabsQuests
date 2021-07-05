package tech.presstabs.tabsquests.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import tech.presstabs.tabsquests.quests.AbstractQuest;

public class QuestQuitEvent extends QuestFailEvent {
    private static final HandlerList handlers = new HandlerList();

    public QuestQuitEvent(Class<? extends AbstractQuest> quest, Player player) {
        super(quest, player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    //The difference between Quitting and Failing is that you can't stop someone from quiting.

    @Override
    public void setCancelled(boolean cancel) {
        //How about No.
    }
}
