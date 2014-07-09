package org.danisoft.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Contract implements Serializable {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 404452082824427768L;

	/**
	 * Primary key from the DB.
	 */
	private String id;
	
	/**
	 * Concept of the contract.
	 */
	private String concept;
	
	/**
	 * Date in which the last payment was done.
	 */
	private Date lastPaymentDate;
	
	/**
	 * Period.
	 */
	private Period period;
	
	/**
	 * Is fixed amount
	 */
	private boolean fixedAmount;
	
	/**
	 * Amount.
	 */
	private double amount;
	
	/**
	 * Account to charge contract's movements.
	 */
	@DBRef
	private Account account;
	
	/**
	 * Primary constructor.
	 * 
	 * @param id
	 * @param concept
	 * @param lastPaymentDate
	 * @param periodicty
	 * @param amount
	 * @param account
	 */
	public Contract(String concept, Date lastPaymentDate,
			Period period, boolean fixedAmount, double amount, Account account) {
		super();
		this.concept = concept;
		this.lastPaymentDate = lastPaymentDate;
		this.period = period;
		this.fixedAmount = fixedAmount;
		this.amount = amount;
		this.account = account;
	}

	public Contract() {	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the concept
	 */
	public String getConcept() {
		return concept;
	}

	/**
	 * @param concept the concept to set
	 */
	public void setConcept(String concept) {
		this.concept = concept;
	}

	/**
	 * @return the lastPaymentDate
	 */
	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		if (period == null)
			return null;
		return period.getCode();
	}

	/**
	 * @param periodicty the period to set
	 */
	public void setPeriod(String period) {
		this.period = Period.periodByCode(period);
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

	public boolean isFixedAmount() {
		return fixedAmount;
	}

	public void setFixedAmount(boolean fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	
}
