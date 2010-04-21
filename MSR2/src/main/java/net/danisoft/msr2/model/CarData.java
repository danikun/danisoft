package net.danisoft.msr2.model;

import com.jme.math.Vector3f;

public class CarData {
	/** The maximum distance the suspension can be compressed (centimetres). */
	private float maxSuspensionTravelCm;
	/** The damping coefficient for when the suspension is compressed. */
	private float suspensionCompression;
	/** The damping coefficient for when the suspension is expanding. */
	private float suspensionDamping;
	/** The stiffness constant for the suspension. */
	private float suspensionStiffness;
	/** 1st wheel position */
	private Vector3f wheelPos1;
	/** 2nd wheel position */
	private Vector3f wheelPos2;
	/** 3rd wheel position */
	private Vector3f wheelPos3;
	/** 4th wheel position */
	private Vector3f wheelPos4;

	/**
	 * Constructor.
	 * 
	 * @param maxSuspensionTravelCm The maximum distance the suspension can be compressed (centimetres).
	 * @param suspensionCompression The damping coefficient for when the suspension is compressed.
	 * @param suspensionDamping The damping coefficient for when the suspension is expanding.
	 * @param suspensionStiffness The stiffness constant for the suspension.
	 * @param wheelPos1 1st wheel position
	 * @param wheelPos2 2nd wheel position
	 * @param wheelPos3 3rd wheel position
	 * @param wheelPos4 4th wheel position
	 */
	public CarData(float maxSuspensionTravelCm, float suspensionCompression,
			float suspensionDamping, float suspensionStiffness,
			Vector3f wheelPos1, Vector3f wheelPos2, Vector3f wheelPos3,
			Vector3f wheelPos4) {
		super();
		this.maxSuspensionTravelCm = maxSuspensionTravelCm;
		this.suspensionCompression = suspensionCompression;
		this.suspensionDamping = suspensionDamping;
		this.suspensionStiffness = suspensionStiffness;
		this.wheelPos1 = wheelPos1;
		this.wheelPos2 = wheelPos2;
		this.wheelPos3 = wheelPos3;
		this.wheelPos4 = wheelPos4;
	}

	/**
	 * @return the maxSuspensionTravelCm
	 */
	public float getMaxSuspensionTravelCm() {
		return maxSuspensionTravelCm;
	}

	/**
	 * @param maxSuspensionTravelCm the maxSuspensionTravelCm to set
	 */
	public void setMaxSuspensionTravelCm(float maxSuspensionTravelCm) {
		this.maxSuspensionTravelCm = maxSuspensionTravelCm;
	}

	/**
	 * @return the suspensionCompression
	 */
	public float getSuspensionCompression() {
		return suspensionCompression;
	}

	/**
	 * @param suspensionCompression the suspensionCompression to set
	 */
	public void setSuspensionCompression(float suspensionCompression) {
		this.suspensionCompression = suspensionCompression;
	}

	/**
	 * @return the suspensionDamping
	 */
	public float getSuspensionDamping() {
		return suspensionDamping;
	}

	/**
	 * @param suspensionDamping the suspensionDamping to set
	 */
	public void setSuspensionDamping(float suspensionDamping) {
		this.suspensionDamping = suspensionDamping;
	}

	/**
	 * @return the suspensionStiffness
	 */
	public float getSuspensionStiffness() {
		return suspensionStiffness;
	}

	/**
	 * @param suspensionStiffness the suspensionStiffness to set
	 */
	public void setSuspensionStiffness(float suspensionStiffness) {
		this.suspensionStiffness = suspensionStiffness;
	}

	/**
	 * @return the wheelPos1
	 */
	public Vector3f getWheelPos1() {
		return wheelPos1;
	}

	/**
	 * @return the wheelPos2
	 */
	public Vector3f getWheelPos2() {
		return wheelPos2;
	}

	/**
	 * @return the wheelPos3
	 */
	public Vector3f getWheelPos3() {
		return wheelPos3;
	}

	/**
	 * @return the wheelPos4
	 */
	public Vector3f getWheelPos4() {
		return wheelPos4;
	}
}
