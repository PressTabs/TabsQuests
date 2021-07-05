package tech.presstabs.tabsquests.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import tech.presstabs.tabsquests.quests.AbstractQuest;

public class QuestCreationEvent extends QuestEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public QuestCreationEvent(Class<? extends AbstractQuest> quest) {
        super(quest);
        this.cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
