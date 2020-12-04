package scripts.fm.utils;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;

import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;

/**
 *
 * Utilities
 *
 * This class contains all random Utilities that have not yet been categorized
 *
 * @author SEngineer
 *
 */
public class Utils {
	
	/**
	 * Checks if the inventory contains a specific item
	 * 
	 * @param item - id of the item to search for
	 * @return true/false
	 */
	public boolean inventoryContains(final int item) {		
		return Inventory.find(item).length > 0;
	}
	
	
	/**
	 * Checks if an item is close to the player
	 * @param item - item to check for
	 * @return true/false
	 */
	public boolean isItemClose(final int id, final int distance) {
		final RSGroundItem[] items = GroundItems.findNearest(5, id);

		return items.length > 0 && Player.getPosition().distanceTo(items[0].getPosition()) < distance;
	}
	
	/**
	 * Burns a log on the ground
	 * 
	 * @param logs - log to burn
	 * @return true/false
	 */
	public boolean burnLogOnGround(final int log) {
		General.println(String.format("[FireMaker] Burning (log %d)", log));

		if (!inventoryContains(590) || !isItemClose(log, 10) || isPlayerPerformingAnimation(733)) {
			return false;
		}

		useItemOnGroundItem(590, log);

		General.sleep(1800, 3000);

		Timing.waitCondition(() -> !isPlayerPerformingAnimation(733), 30000);

		return true;
	}
	
	/**
     * Uses an item in the inventory on a ground item
     * @param srcItemId - item to use
     * @param destItemId - item to click
     * @return true/false
     */
    public boolean useItemOnGroundItem(final int srcItemId, final int destItemId) {
    	final RSGroundItem[] destItems = GroundItems.find(destItemId);
    	
    	if(!inventoryContains(srcItemId) || destItems.length == 0) { 
    		return false; 
    	}
   
    	final RSItem srcItem = Inventory.find(srcItemId)[0];
    	srcItem.click("Use");

		final boolean isUsed = DynamicClicking.clickRSGroundItem(destItems[0], 
				String.format("%s %s -> %s", "Use", srcItem.getDefinition().getName(), destItems[0].getDefinition().getName()));

		General.sleep(600, 1200);

		return isUsed;
    }

	/**
	 * Configures the DaxWalker
	 */
	public void configureDaxWalker() {
		General.println("[FireMaker] Configuring Dax Walker Credentials");

		DaxWalker.setCredentials(new DaxCredentialsProvider() {
			@Override
			public DaxCredentials getDaxCredentials() {
				return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
			}
		});
	}

	/**
	 * Walks to a location if the Player is not already there
	 * 
	 * @param destination - place to walk to
	 */
	public void walkTo(final Positionable destination) {
		while (!isPlayerCloseTo(destination)) {
			General.println(String.format("[FireMaker] Attempting to walk to (%s)", destination.toString()));
			DaxWalker.walkTo(destination);
			General.sleep(Numbers.FIVE_SECONDS, Numbers.EIGHT_SECONDS);
		}
		General.println(String.format("[FireMaker] Cancelling walk - We are already close to (%s)", destination.toString()));
	}

	/**
	 * Checks if the Players current position is within 10 tiles of the destination
	 * 
	 * @param destination - place to walk to
	 * @return true/false
	 */
	public boolean isPlayerCloseTo(final Positionable destination) {
		return Player.getPosition().getPlane() == destination.getPosition().getPlane()
				? Player.getPosition().distanceTo(destination) < 5 : false;
	}

	/**
	 * Checks if an amount of time has elapsed
	 * 
	 * @param startTime    - the start time
	 * @param timeToElapse - the amount of time to elapse
	 * @return true/false
	 */
	public boolean isTimeElapsed(final long startTime, final long time) {
		return (System.currentTimeMillis() - startTime) > time ? true : false;
	}
	
	/**
	 * Check if a player is performing a specific animation
	 * 
	 * @param animation - animation to check
	 * @return true/false
	 */
	public boolean isPlayerPerformingAnimation(final int animation) {
		boolean isAnimating = false;

		long start = System.currentTimeMillis();

		while (!isTimeElapsed(start, Numbers.ONE_SECOND)) {
			if (Player.getAnimation() == animation) {
				isAnimating = true;
				break;
			}
			General.sleep(200);
		}
		return isAnimating;
	}
	
}
