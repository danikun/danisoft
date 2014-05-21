package org.danisoft.services;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.danisoft.model.Account;
import org.danisoft.model.Movement;

/**
 * Service providing operations over accounts.
 * 
 * @author Daniel Garcia
 *
 */
public interface IAccountsService {

	/**
	 * List all the accounts persisted in the system.
	 * @return list of account found
	 */
	List<Account> getAllAccounts();
	/**
	 * Remove a persisted account.
	 * @param account the account to remove
	 */
	void deleteAccount(Account account);
	/**
	 * Persists an account
	 * @param account the account to persist
	 */
	void saveAccount(Account account);
	/**
	 * Loads a list of movements from a file.
	 * @param account the account to load the movements.
	 * @param inputStream input stream with the contents of the file.
 	 * @return the list of persisted movements
	 */
	List<Movement> loadMovementsFromFile(Account account, InputStream inputStream);

}
