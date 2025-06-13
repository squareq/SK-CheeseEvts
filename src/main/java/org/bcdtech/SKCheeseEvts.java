package org.bcdtech;

import ch.njol.skript.Skript;
import org.bukkit.plugin.java.JavaPlugin;
import org.skriptlang.skript.addon.SkriptAddon;
import org.skriptlang.skript.util.ClassLoader;

import java.util.logging.Level;
import java.util.logging.LogRecord;
@SuppressWarnings("unused")
public class SKCheeseEvts extends JavaPlugin {

    SKCheeseEvts pinstance;
    SkriptAddon skriptAddon;

    @Override
    public void onEnable() {
        super.onEnable();
        pinstance = this;
        if(Skript.isAcceptRegistrations()){
            skriptAddon = Skript.instance().registerAddon(SKCheeseEvts.class, "SK-CheeseEvts");
            ClassLoader.loadClasses(SKCheeseEvts.class, getFile(), "org.bcdtech.Elements", "Expressions", "Types", "Effects", "Events");
            if(Skript.getAddons().contains(skriptAddon)){
                pinstance.getLogger().log(new LogRecord(Level.FINE, "Loaded SK-CheeseEvts, a revamp of the old CustomEvents addon."));
                Skript.getAddonInstance().setLanguageFileDirectory("lang");
            }
        }
    }

}