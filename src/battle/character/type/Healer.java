package battle.character.type;

import battle.RandomUtil;
import battle.character.Combatant;
import battle.character.Damageable;
import battle.character.Disableable;

public abstract class Healer implements Disableable, Combatant {
	//the strength of their magic capabilities
	private final int healingPower;
	
	//the number of turns that the special fighter will be disabled for
	private int disabledTurns;
	
	/**
	 * Creates a healer. Should NOT be disabled initially.
	 * 
	 * @param healingPower an amount corresponding to their magic capabilities
	 */
	public Healer(int healingPower) {
		this.healingPower = healingPower;
		this.disabledTurns = Integer.MAX_VALUE;
	}
	
	/**
	 * Heals a teammate for anywhere between half of their healing power and all of their healing
	 * power (inclusive)
	 * 
	 * @param teammate person to heal
	 * @return how much health the person gained
	 */
	public int heal(Damageable teammate) {
		return teammate.incrementHealth(healingPower*(RandomUtil.randomInclusive(5,10)/10));
	}
	
	/**
	 * Heals a teammate to full health and disables the healer for 5 turns
	 * 
	 * @param teammate person to heal
	 * @return how much health the person gained
	 */
	public int sacrifice(Damageable teammate) {
		disabledTurns = 5;
		return teammate.incrementHealth(Integer.MAX_VALUE);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void skipTurn() {
		if(disabledTurns > 0) {
			disabledTurns--;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDisabled() {
		return disabledTurns > 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStatus() {
		if(isDisabled()) {
			return "disabled for " + disabledTurns + " more turns";
		} else {
			return "available";
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canTakeTurn() {
		return !isDisabled();
	}
}
