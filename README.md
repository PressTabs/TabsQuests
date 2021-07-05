# TabsQuests
**THIS DOES REQUIRE A MONGODB DATABASE**

This is just a little Quest framework/api/whatever. It's easy to indicate and mark your methods and Quest classes with annotations. The plugin/api/whatever itself just handles Quests for you, it dosen't create or issue them automatically. 

To add your own quests and other content just clone the repository to your local machine and extend the AbstractQuest class. Then just mark your methods with either **@Reward**, **@QuestEnd**, **@QuestFail**, **@QuestStart** to indicate the function of each method. Then go to your QuestManager instance in the onEnable method and register your Quest.

I will probably only be fixing small bugs but other than that no updates will come except maybe a massive overhaul in the future


To add/remove/reward players you can just use the respective QuestManager static method for each. It will automatically grab from the database and perform such function.

API VERSION: 1.8.8
