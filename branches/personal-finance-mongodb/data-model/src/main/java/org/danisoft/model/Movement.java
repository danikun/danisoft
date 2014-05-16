package org.danisoft.model;

import java.io.Serializable;
import java.util.Date;

public class Movement implements Serializable {

	/**
	 * Generated Serial Version. 
	 */
	private static final long serialVersionUID = -7003850171775135550L;

	/**
	 * Primary key from the DB.
	 */
	private long id;
	
	/**
	 * Date.
	 */
	private Date date;
	
	/**
	 * Value Date.
	 */
	private Date valueDate;
	
	/**
	 * Description of the movement;
	 */
	private String description;
	
	/**
	 * Amount.
	 */
	private double amount;
	
	/**
	 * Account owning the movement.
	 */
	private Account account;
	
	/**
	 * Contract related to the movement.
	 */
	private Contract contract;

	/**
	 * Primary Constructor.
	 * 
	 * @param id
	 * @param date
	 * @param valueDate
	 * @param description
	 * @param amount
	 * @param account
	 * @param contract
	 */
	public Movement(long id, Date date, Date valueDate, String description,
			double amount) {
		super();
		this.id = id;
		this.date = date;
		this.valueDate = valueDate;
		this.description = description;
		this.amount = amount;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the valueDate
	 */
	public Date getValueDate() {
		return valueDate;
	}

	/**
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract the contract to set
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	
}
