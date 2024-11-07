package interfaces;
/**
 * Interface to ensure that a class implementing it has the method for saving an account.
 * This interface defines the method signature that must be implemented to handle account-saving functionality.
 */
public interface AccountSaver {
	/**
     * Saves an account. Implementing classes should provide the specific implementation for saving account details.
     */
	public void saveAccount();
}
