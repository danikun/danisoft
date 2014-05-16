package org.danisoft.model;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7194168052908098584L;
		
	/**
	 * Primary Key
	 */
	private String id;
	
	/**
	 * Account Number.
	 */
	private String number;
	
	/**
	 * Current Balance.
	 */
	private double balance;
	
	/**
	 * Description of the account
	 */
	private String description;
	
	/**
	 * Movements of the account.
	 */
	private List<Movement> movements;

	/**
	 * Primary constructor.
	 * 
	 * @param id primary key in the database
	 * @param number Account number
	 * @param balance current balance
	 */
	public Account(String number, double balance, String description, List<Movement> movements) {
		this.number = number;
		this.balance = balance;
		this.description = description;
		this.setMovements(movements);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the movements
	 */
	public List<Movement> getMovements() {
		return movements;
	}

	/**
	 * @param movements the movements to set
	 */
	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}
}
