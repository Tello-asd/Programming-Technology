package interfaces;

public interface IAttack {
	
	void attack();
	default boolean receiveSlayerAttack(int damage) {return false;};	
	default boolean receiveVampireAttack(int damage) {return false;};
	default boolean receiveDraculaAttack() {return false;};

	
	default boolean flashLight() {return false;};	
	default boolean garlicPush() {return false;};
	
	default boolean water() {return false;};
	
	

}
