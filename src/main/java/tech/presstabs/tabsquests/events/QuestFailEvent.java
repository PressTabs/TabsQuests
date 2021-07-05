package tech.presstabs.tabsquests.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import tech.presstabs.tabsquests.quests.AbstractQuest;

public class QuestFailEvent extends QuestEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private boolean cancelled;

    public QuestFailEvent(Class<? extends AbstractQuest> quest, Player player) {
        super(quest);
        this.player = player;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
