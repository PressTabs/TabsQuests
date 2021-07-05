package tech.presstabs.tabsquests.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tech.presstabs.tabsquests.quests.AbstractQuest;

public class QuestEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    protected final Class<? extends AbstractQuest> quest;

    public QuestEvent(Class<? extends AbstractQuest> quest) {
        this.quest = quest;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Class<? extends AbstractQuest> getQuest() {
        return this.quest;
    }
}
