package tech.presstabs.tabsquests.quests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QuestHandler {
    QuestDifficulty difficulty = QuestDifficulty.NOOB;

    String questName();
}
