package com.epam.testschool.model;

import java.util.Date;

import com.epam.testschool.annotations.SaleInfo;
import com.epam.testschool.exceptions.AuthorizationException;
/**
 * 
 * Class was created for moving out common structure (constructors, variables, etc.)
 *
 */
public abstract class AbstractVehicle implements Vehicle {
	protected String owner;
	protected VehicleType type;
	@SaleInfo
	protected Date dateOfManufacture;
	protected String vehicleId;
	protected boolean isAuthorized;
	/**
	 * @param owner - owner of the vehicle
	 * @param vehicleType - type of vehicle. 
	 * @see VehicleType
	 * @param dateOfManufacture - date when the vehicle was created
	 * @param vehicleId - unique identificator of the vehicle
	 */
	public AbstractVehicle(
			String owner,
			VehicleType vehicleType,
			Date dateOfManufacture,
			String vehicleId
		) {
		this.owner = owner;
		this.type = vehicleType;
		this.dateOfManufacture = dateOfManufacture;
		this.vehicleId = vehicleId;
		this.isAuthorized = false;
	}
	@Override
	public String toString() {
		return "owner=" + owner + ", type=" + type
				+ ", dateOfManufacture=" + dateOfManufacture + ", vehicleId="
				+ vehicleId + ", isAuthorized=" + isAuthorized + "\n";
	}
	
	protected void ensureAuthorization() {
		if (!isAuthorized){
			throw new AuthorizationException();
		}
	}
	
	/**
	 * Grants access to subsequent manipulations with vehicle
	 * @param owner - owner of the vehicle
	 * @param key - key of vehicle's owner
	 * @exception  AuthorizationException  if the wrong owner and key for this vehicle are provided. 
	 */
	public void authorize(String owner, String key) {
		if (checkCredetentials(owner, key)){
			isAuthorized = true;			
		}else{
			throw new AuthorizationException(key);
		}
	}
	/**
	 * Checks matching of owner with actual owner of the vehicle and key with a hash function which is 
	 * defined in concrete implementation 
	 * @param owner - owner of the vehicle
	 * @param key - key of vehicle's owner 
	 * @return true - in case of matching of owner and key, otherwise - false
	 */
	protected abstract boolean checkCredetentials(String owner, String key);
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public boolean isAuthorized() {
		return isAuthorized;
	}
	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}
	public VehicleType getType() {
		return type;
	}
	public void setType(VehicleType type) {
		this.type = type;
	}
	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}
	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
}
