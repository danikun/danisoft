package org.danisoft.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7194168052908098584L;
		
	/**
	 * Primary Key
	 */
	private int id;
	
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
	public Account(int id, String number, double balance, String description, List<Movement> movements) {
		this.id = id;
		this.number = number;
		this.balance = balance;
		this.description = description;
		this.setMovements(movements);
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "account_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	@OneToMany(mappedBy="account", cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
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
