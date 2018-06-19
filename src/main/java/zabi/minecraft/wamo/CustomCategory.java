package zabi.minecraft.wamo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CustomCategory extends Category {
	
	public CustomCategory(File file) {
		super(new FileFilter(file), file.getName().substring(0, file.getName().indexOf(".")));
		if (file.exists() && file.isDirectory()) {
			l.warn("File "+file+" is a directory. Things may not go as planned");
		} else if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void loadCategory() {
		((FileFilter)this.filter).process();
		super.loadCategory();
	}
	
	static class FileFilter implements Predicate<ItemStack> {
		
		ArrayList<String> itemsString = new ArrayList<>();
		ArrayList<Ingredient> validItems = new ArrayList<>();
		
		public FileFilter(File f) {
			try {
				BufferedReader b = new BufferedReader(new FileReader(f));
				b.lines().filter(s-> !s.startsWith("#")).forEach(s -> itemsString.add(s));
				b.close();
			} catch (IOException e) {
				l.warn("Couldn't read file: "+f.getAbsolutePath());
			}
		}
		
		public void process() {
			for (String s:itemsString) {
				String name = "minecraft:air";
				int meta = 0;
				int at = s.indexOf("@");
				if (at>0) {
					try {
						meta = Integer.parseInt(s.substring(at+1));
						name=s.substring(0, at);
					} catch (Exception e) {
						l.error("Malformed metadata in string "+s);
						return;
					}
				} else {
					name = s;
				}
				Item i = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
				if (i==Items.AIR) {
					l.error("Item not found: "+name); 
					return;
				}
				
				if (at>0) {
					validItems.add(Ingredient.fromStacks(new ItemStack(i, 1, meta)));
				} else {
					validItems.add(Ingredient.fromItem(i));
				}
			}
		}

		@Override
		public boolean test(ItemStack arg0) {
			for (Ingredient i:validItems) {
				if (i.apply(arg0)) {
					return true;
				}
			}
			return false;
		}
		
	}

}
