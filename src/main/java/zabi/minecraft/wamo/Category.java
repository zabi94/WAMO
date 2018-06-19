package zabi.minecraft.wamo;

import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Category {
	
	public static final Logger l = LogManager.getLogger(WAMO.MOD_ID);
	
	private final NonNullList<ItemStack> stacks = NonNullList.create();
	protected final Predicate<ItemStack> filter;
	private final String categoryName;
	
	public Category(Predicate<ItemStack> filter, String name) {
		this.filter = filter;
		categoryName = name;
	}
	
	public void loadCategory() {
		stacks.clear();
		for (Item i:ForgeRegistries.ITEMS) {
			if (i.getHasSubtypes()) {
				NonNullList<ItemStack> pstacks = NonNullList.create();
				i.getSubItems(CreativeTabs.SEARCH, pstacks);
				for (ItemStack is:pstacks) {
					if (filter.test(is)) {
						stacks.add(is);
					}
				}
			} else {
				ItemStack is = new ItemStack(i);
				if (filter.test(is)) {
					stacks.add(is);
				}
			}
		}
		
		l.info(I18n.format(categoryName));
		stacks.forEach(is -> l.info("Valid Stack: "+is));
	}
	
}
