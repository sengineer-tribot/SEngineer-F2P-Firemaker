package scripts.fm.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.fm.paint.FluffeesPaint;
import scripts.fm.paint.PaintInfo;
import scripts.fm.support.ABC2Support;
import scripts.fm.utils.Utils;

@ScriptManifest(authors = {
		"SEngineer" }, category = "Firemaking", name = "SEngineer's F2P Firemaker", version = 1.00, description = "F2P Firemaking", gameMode = 1)

/**
 * 
 * F2P Firemaking Script
 * 
 * This script will firemake logs in Lumbridge Castle
 * 
 * @author SEngineer
 *
 */
public class SEngineerFiremaker extends Script implements PaintInfo, Painting {
	
	private final Double scriptVersion = 1.00;
	
	private final FluffeesPaint display = new FluffeesPaint(
			this, FluffeesPaint.PaintLocations.TOP_RIGHT_CHATBOX,
			new Color[] { new Color(255, 251, 255) }, "Trebuchet MS", new Color[] { new Color(93, 156, 236, 127) },
			new Color[] { new Color(39, 95, 175) }, 1, false, 5, 3, 0);

	// ~~~ Support Classes

	private ABC2Support abc2Support;
	
	private Utils utils = new Utils();
	
	// ~~~ Script Variables

	private String status = "";
	private int firemakingXP = 0;
	
	private Random rand = new Random();
	
	@Override
	public String[] getPaintInfo() {
        return new String[] {
        		"SEngineer's F2P Firemaker v" + String.format("%.2f", scriptVersion),
        		"Status: " + status,
        		"Firemaking XP: " + (Skills.getXP(SKILLS.FIREMAKING) - firemakingXP),
        		"Script Runtime: " + display.getRuntimeString(),
        };
	}
	
	@Override
	public void onPaint(final Graphics gfx) {
		display.paint(gfx);
	}

	/**
	 * Configures any script pre-requisites
	 */
	private void configure() {
		General.println("[FireMaker] Configuring SEngineer's F2P FireMaker");

		utils.configureDaxWalker();
		Mouse.setSpeed(100 + rand.nextInt(30));
		Mouse.scroll(false, rand.nextInt(20));

		General.useAntiBanCompliance(true);
		abc2Support = ABC2Support.getInstance();
		abc2Support.generateTrackers();

		firemakingXP = Skills.getXP(SKILLS.FIREMAKING);
	}

	/**
	 * 1. Configure the DaxWalker 
	 * 2. Firemake logs
	 */
	@Override
	public void run() {
		configure();
		
		if(!utils.inventoryContains(590)) {
			updateScriptStatus("ERROR: No Tinderbox!");
			return;
		}
		
		updateScriptStatus("Walking to Lumbridge Castle");
		utils.walkTo(new RSTile(3207, 3224, 2));

		while (true) {
			updateScriptStatus("Burning logs");
			while(GroundItems.findNearest(10, 1511).length > 0) {
				utils.burnLogOnGround(1511);
			}

			updateScriptStatus("Hopping worlds");
			WorldHopper.changeWorld(WorldHopper.getRandomWorld(false));
			abc2Support.runAntiBan();
		}
	}
	
	/**
	 * Updates the script status
	 * @param status - Current status of the script
	 */
	public void updateScriptStatus(final String status) {
		General.println(String.format("[FireMaker] %s", status));
		this.status = status;
	}

}