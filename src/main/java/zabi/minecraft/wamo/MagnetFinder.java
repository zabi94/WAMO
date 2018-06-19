package zabi.minecraft.wamo;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class MagnetFinder implements Predicate<ItemStack> {
	
	@ObjectHolder("extraalchemy:effect.magnetism")
	public static final Potion magnetism = null;
	
	public static final String[] knownItems = {
			"xreliquary:fortune_coin",
			"botania:magnetring",
			"botania:magnetringgreater",
			"bloodmagic:sigil_magnetism",
			"actuallyadditions:item_suction_ring"
			//magic bees magnet, Ars Magica charm
	};

	@Override
	public boolean test(ItemStack is) {
		if (PotionUtils.getEffectsFromStack(is).stream().map(pe -> pe.getPotion()).anyMatch(p -> p.equals(magnetism))) {
			return true;
		}
		String name = is.getItem().getRegistryName().toString();
		for (String s:knownItems) {
			if (s.equals(name)) {
				return true;
			}
		}
		return false;
	}

}
