package zabi.minecraft.wamo;

import net.minecraftforge.common.config.Config;

@Config(modid = WAMO.MOD_ID)
@Config.RequiresMcRestart
public class ModConfig {
	
	@Config.Comment("A list of all the categories you want to add")
	public static String[] extraCategories = {};
	
	public static boolean enableSwords = true;
	public static boolean enableShields = true;
	public static boolean enableAxes = true;
	public static boolean enableArmors = true;
	public static boolean enablePicks = true;
	public static boolean enableHoes = true;
	public static boolean enableMagnets = true;
	public static boolean enableShovels = true;
	public static boolean enableBows = true;
	
}
