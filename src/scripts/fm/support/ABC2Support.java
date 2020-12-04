package scripts.fm.support;

import java.util.Random;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

/**
 * 
 * AntiBanCompliance Support Class
 * 
 * @author SEngineer
 *
 */
public class ABC2Support extends ABCUtil {

	private static ABC2Support instance = null;

	private long timeChange, activateEnergyTimer, attackStyleTimer, cameraRotateTimer,
			combatCheckTimer, examineTimer, friendCheckTimer, inventoryCheckTimer, mouseBreakTimer, mouseSpeedTimer,
			prayerCheckTimer, skillCheckTimer, spellCheckTimer;

	private long startTime = System.currentTimeMillis();

	private long activateEnergyTime = 300000;
	private long attackStyleTime = 600000;
	private long cameraRotateTime = 35000;
	private long combatCheckTime = 540000;
	private long examineTime = 1200000;
	private long friendCheckTime = 780000;
	private long inventoryCheckTime = 1020000;
	private long mouseBreakTime = 900000;
	private long mouseSpeedTime = 20000;
	private long prayerCheckTime = 660000;
	private long skillCheckTime = 600000;
	private long spellCheckTime = 720000;

	private Random rand = new Random();

	/**
	 * Singleton getInstance()
	 * 
	 * @return instance of TaskSupport
	 */
	public static ABC2Support getInstance() {
		if (instance == null) {
			instance = new ABC2Support();
		} 

		return instance;
	}

	public ABC2Support() {
		super();
	}

	/**
	 * 
	 * Runs the ABC2 Compliance
	 * 
	 */
	public void runAntiBan() {
		long deltaTime = getTimeChange();

		activateEnergyTimer += deltaTime;
		if (shouldActivateEnergy()) {
			activateEnergy();
		}

		attackStyleTimer += deltaTime;
		if (shouldChangeAttackStyle()) {
			changeAttackStyle();
		}

		cameraRotateTimer += deltaTime;
		if (shouldRotateCameraAngle()) {
			rotateCameraAngle();
		}


		combatCheckTimer += deltaTime;
		if (shouldCheckCombat()) {
			checkCombat();
		}

		examineTimer += deltaTime;
		if (shouldExamineSomething()) {
			examineSomething();
		}

		friendCheckTimer += deltaTime;
		if (shouldCheckFriends()) {
			checkFriends();
		}

		inventoryCheckTimer += deltaTime;
		if (shouldCheckInventory()) {
			checkInventory();
		}

		mouseBreakTimer += deltaTime;
		if (shouldTakeMouseBreak()) {
			takeMouseBreak();
		}

		mouseSpeedTimer += deltaTime;
		if (shouldChangeMouseSpeed()) {
			changeMouseSpeed();
		}

		prayerCheckTimer += deltaTime;
		if (shouldCheckPrayers()) {
			checkPrayers();
		}

		skillCheckTimer += deltaTime;
		if (shouldCheckSkills()) {
			checkSkills();
		}

		spellCheckTimer += deltaTime;
		if (shouldCheckSpellbook()) {
			checkSpellbook();
		}

		if (this.shouldCheckTabs()) {
			this.checkTabs();
		}

		if (this.shouldCheckXP()) {
			this.checkXP();
		}

		if (this.shouldExamineEntity()) {
			this.examineEntity();
		}

		if (this.shouldMoveMouse()) {
			this.moveMouse();
		}

		if (this.shouldPickupMouse()) {
			this.pickupMouse();
		}

		if (this.shouldRightClick()) {
			this.rightClick();
		}

		if (this.shouldRotateCamera()) {
			this.rotateCamera();
		}

		if (this.shouldLeaveGame()) {
			this.leaveGame();
		}
	}

	/**
	 * Checks if we should activate run energy or not
	 * 
	 * @return true/false
	 */
	private boolean shouldActivateEnergy() {
		return activateEnergyTimer > activateEnergyTime;
	}

	/**
	 * Turns run on
	 */
	private void activateEnergy() {
		Options.setRunEnabled(true);

		activateEnergyTimer = 0;
	}

	/**
	 * Checks if we should change attack style or not
	 * 
	 * @return true/false
	 */
	private boolean shouldChangeAttackStyle() {
		return attackStyleTimer > attackStyleTime;
	}

	/**
	 * Changes the attack style
	 */
	private void changeAttackStyle() {
		int max = Combat.getAvailableAttackActions().length - 1;
		
		if (max < 0) {
			max = 1;
		}

		Combat.selectIndex(rand.nextInt(max));

		attackStyleTimer = 0;
	}

	/**
	 * Checks if we should check the mouse speed or not
	 * 
	 * @return true/false
	 */
	private boolean shouldChangeMouseSpeed() {
		return mouseSpeedTimer > mouseSpeedTime;
	}

	/**
	 * Changes the mouse speed
	 */
	private void changeMouseSpeed() {
		Mouse.setSpeed(General.random(97, 110));

		mouseSpeedTimer = 0;
	}

	/**
	 * Checks if we should check the prayers or not
	 * 
	 * @return true/false
	 */
	private boolean shouldCheckPrayers() {
		return prayerCheckTimer > prayerCheckTime;
	}

	/**
	 * Opens the prayer tab
	 */
	private void checkPrayers() {
		GameTab.open(GameTab.TABS.PRAYERS);
		General.sleep(100, 200);

		prayerCheckTimer = 0;
	}

	/**
	 * Checks if we should check the spell book or not
	 * 
	 * @return true / false
	 */
	private boolean shouldCheckSpellbook() {
		return spellCheckTimer > spellCheckTime;
	}

	/**
	 * Opens the magic spellbook
	 */
	private void checkSpellbook() {
		GameTab.open(GameTab.TABS.MAGIC);
		General.sleep(100, 200);

		spellCheckTimer = 0;
	}

	/**
	 * Checks if we should check the combat level or not
	 * 
	 * @return true/false
	 */
	private boolean shouldCheckCombat() {
		return combatCheckTimer > combatCheckTime;
	}

	/**
	 * Opens the attack styles
	 */
	private void checkCombat() {
		GameTab.open(GameTab.TABS.COMBAT);
		General.sleep(100, 200);

		combatCheckTimer = 0;
	}

	/**
	 * Checks if we should check the inventory or not
	 * 
	 * @return
	 */
	private boolean shouldCheckInventory() {
		return inventoryCheckTimer > inventoryCheckTime;
	}

	/**
	 * Opens the inventory
	 */
	private void checkInventory() {
		GameTab.open(GameTab.TABS.INVENTORY);
		General.sleep(100, 200);

		inventoryCheckTimer = 0;
	}

	/**
	 * Checks if we should check the friends list or not
	 * 
	 * @return true/false
	 */
	private boolean shouldCheckFriends() {
		return friendCheckTimer > friendCheckTime;
	}

	/**
	 * Opens the friendslist
	 */
	private void checkFriends() {
		GameTab.open(GameTab.TABS.FRIENDS);
		General.sleep(100, 200);

		friendCheckTimer = 0;
	}

	/**
	 * Checks if we should examine something or not
	 * 
	 * @return true/false
	 */
	private boolean shouldExamineSomething() {
		return examineTimer > examineTime;
	}

	/**
	 * Examines a random objects or npc
	 */
	private void examineSomething() {
		Mouse.randomRightClick();

		examineTimer = 0;
	}

	/**
	 * Checks if we should take a mouse break or not
	 * 
	 * @return true/false
	 */
	private boolean shouldTakeMouseBreak() {
		return mouseBreakTimer > mouseBreakTime;
	}

	/**
	 * Moves the mouse off of the screen
	 */
	private void takeMouseBreak() {
		Mouse.leaveGame();
		General.sleep(2000, 5000);

		mouseBreakTimer = 0;
	}

	/**
	 * Checks if we should check the skills or not
	 * 
	 * @return true/false
	 */
	private boolean shouldCheckSkills() {
		return skillCheckTimer > skillCheckTime;
	}

	/**
	 * Opens the players stats
	 */
	private void checkSkills() {
		GameTab.open(GameTab.TABS.STATS);
		General.sleep(100, 200);

		skillCheckTimer = 0;
	}

	/**
	 * Checks if we should rotate the camera angle or not
	 */
	private boolean shouldRotateCameraAngle() {
		return cameraRotateTimer > cameraRotateTime;
	}

	/**
	 * Rotates the camera
	 */
	private void rotateCameraAngle() {
		int randomX = Player.getPosition().getX();
		int randomY = Player.getPosition().getY();
		randomX += General.random(-15, 15);
		randomY += General.random(-15, 15);
		Camera.turnToTile(new RSTile(randomX, randomY));

		cameraRotateTimer = 0;
	}

	/**
	 * Returns the time change since start
	 * 
	 * @return long - time change
	 */
	private long getTimeChange() {
		timeChange = System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();

		return timeChange;
	}

}
