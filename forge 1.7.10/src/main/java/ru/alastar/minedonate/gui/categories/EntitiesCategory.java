package ru.alastar.minedonate.gui.categories;

import net.minecraft.client.gui.ScaledResolution;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopCategory;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.merch.Merch;
import ru.alastar.minedonate.merch.info.EntityInfo;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.gui.GuiAbstractItemEntry;
import ru.log_inil.mc.minedonate.gui.GuiItemsScrollArea;
import ru.log_inil.mc.minedonate.gui.GuiScrollingList;
import ru.log_inil.mc.minedonate.gui.items.GuiItemEntryOfEntityMerch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 20.07.2017.
 */
public class EntitiesCategory extends ShopCategory {
    
	public EntitiesCategory ( ) {
		
		catId = 3 ;
		
	}
	
    @Override
    public boolean getEnabled ( ) {
        
    	return MineDonate . cfg . sellEntities ;
        
    }

    @Override
    public int getSourceCount(int shopId) {
    	
    	return MineDonate . shops . containsKey ( shopId ) ? MineDonate . shops . get ( shopId ) . cats [ catId ] . getMerch ( ) . length : 0 ;
   
    }

    @Override
    public String getName ( ) {
        
    	return "Entities" ;
        
    }

    ScaledResolution resolution ;
    
    @Override
    public void draw(ShopGUI relative, int m_Page, int mouseX, int mouseY, float partialTicks, DrawType dt) {

    	resolution = new ScaledResolution(relative.mc, relative.mc.displayWidth, relative.mc.displayHeight);
    	gi.drawScreen(mouseX, mouseY, partialTicks, dt);
    	
    }
    
    @Override
    public void updateButtons(ShopGUI relative, int m_Page ) {
    	
    	refreshGui ( ) ; 

    }
    
    // #LOG
    
	@Override
	public int getButtonWidth ( ) {
		
		return MineDonate.cfgUI.cats.entities.categoryButtonWidth;
		
	}
	
	@Override 
	public String getButtonText ( ) {
		
		return MineDonate.cfgUI.cats.entities.categoryButtonText ;
		
	}

	GuiItemsScrollArea gi ;
	List < GuiAbstractItemEntry > entrs = new ArrayList < > ( ) ;
		
	@Override
	public void initGui ( ) {
	
		if ( subCatId == -1 ) {
			
			setSubCategory ( subCatId ) ;
			
		}
		
		refreshGui ( ) ;
		
	}
	
	EntityInfo eim ;

	List < Merch > noSearchedEntries = new ArrayList < > ( ) ;
	
	public void setSubCategory ( int _subCatId ) {
		
		noSearchedEntries . clear ( ) ;
		
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
			for ( Merch m : MineDonate . shops . get ( 0 ) . cats [ catId ] . getMerch ( ) ) {
				
				if ( m . subCatId == _subCatId || _subCatId == -1 ) {
					
					noSearchedEntries . add ( m ) ;
					
				}
				
			}
			
		}
		
	}
	
	public void refreshGui ( ) {
		
		resolution = new ScaledResolution(gui.mc, gui.mc.displayWidth, gui.mc.displayHeight);
		
		gi = new GuiItemsScrollArea ( resolution, gui, entrs, 0 ) ;
	
		for ( GuiAbstractItemEntry gie : entrs ) {

			gie . undraw ( ) ;
			
		}
		
		entrs . clear ( ) ;
		
		if ( MineDonate . shops . containsKey ( gui . getCurrentShopId ( ) ) ) {
	
	    	if ( search ) {
	    		
	    		for ( Merch m : noSearchedEntries ) {
	        		
	        		eim = ( EntityInfo ) m ; 
	        		
	        		if ( eim . name . toLowerCase ( ) . contains ( searchValue ) ) {
	        			
	            		entrs . add ( new GuiItemEntryOfEntityMerch ( eim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        			
	        		}
	        		
	        	} 
	        		
	    	} else {
	    		
	    		for ( Merch m : noSearchedEntries ) {
	        		
	        		eim = ( EntityInfo ) m ; 
	        		entrs . add ( new GuiItemEntryOfEntityMerch ( eim, this ) . addButtons ( gui ) . updateDrawData ( ) ) ;
	        		
	        	}

	    	}
		
		}
    	
    	gi . entrs = entrs ;
    	gi . applyScrollLimits ( ) ;
    	
	}
	
	@Override
	public GuiScrollingList getScrollList ( ) {
		
		return gi ;
		
	}
	
}
