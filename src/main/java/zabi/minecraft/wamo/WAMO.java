package zabi.minecraft.wamo;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name=WAMO.MOD_NAME, modid=WAMO.MOD_ID, version=WAMO.MOD_VERSION)
public class WAMO {
	public static final String MOD_ID = "wamo";
	public static final String MOD_NAME = "What Are My Options?";
	public static final String MOD_VERSION = "0.0.1";
	
	public static final ArrayList<Category> CATEGORIES = new ArrayList<Category>();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		if (ModConfig.enableSwords) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemSword, "swords"));
		}
		if (ModConfig.enablePicks) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemPickaxe, "picks"));
		}
		if (ModConfig.enableAxes) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemAxe, "axes"));
		}
		if (ModConfig.enableShields) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemShield, "shields"));
		}
		if (ModConfig.enableShovels) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemSpade, "shovels"));
		}
		if (ModConfig.enableBows) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemBow, "bows"));
		}
		if (ModConfig.enableArmors) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemArmor, "armors"));
		}
		if (ModConfig.enableHoes) {
			CATEGORIES.add(new Category(is -> is.getItem() instanceof ItemHoe, "hoes"));
		}
		if (ModConfig.enableMagnets) {
			CATEGORIES.add(new Category(new MagnetFinder(), "magnets"));
		}
		
		String basePath = evt.getModConfigurationDirectory().getAbsolutePath()+File.separatorChar+MOD_ID;
		File baseDir = new File(basePath);
		if (baseDir.mkdir() || (baseDir.exists() && baseDir.isDirectory())) {
			for (String s:ModConfig.extraCategories) {
				CATEGORIES.add(new CustomCategory(new File(basePath+File.separatorChar+s+".txt")));
			}
		} else {
			Category.l.warn("Couldn't access config directory");
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		CATEGORIES.forEach(c -> c.loadCategory());
	}
	
}
