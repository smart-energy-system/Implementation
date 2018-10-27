package backend;

public class Battery {
  private double maximumStoredEnergy;
  private double maximumChargingRate;
  private double maximumDischargingRate;
  private double storedEnergy;
  
  public void computeStoredEnergy(double dischargingRate, double chargingRate, double chargingEfficiency) {
	  this.storedEnergy = this.storedEnergy-Math.min(((Math.min(dischargingRate,getMaximumDischargingRate()))/chargingEfficiency),storedEnergy)+Math.min((Math.min(chargingRate,getMaximumChargingRate())/chargingEfficiency),getMaximumPossibleCharge());
  }
  
  public double getMaximumPossibleCharge() {
	  return maximumStoredEnergy - storedEnergy;
  }
  
  public void setMaximumStoredEnergy(double maximumStoredEnergy) {
	  this.maximumStoredEnergy = maximumStoredEnergy;
  }
  
  public double getMaximumStoredEnergy() {
	  return maximumStoredEnergy;
  }
  
  public void setMaximumChargingRate(double maximumChargingRate) {
	  this.maximumChargingRate = maximumChargingRate;
  }
  
  public double getMaximumChargingRate() {
	  return maximumChargingRate;
  }
  
  public void setMaximumDischargingRate(double maximumDischargingRate) {
	  this.maximumDischargingRate = maximumDischargingRate;
  }
  
  public double getMaximumDischargingRate() {
	  return maximumDischargingRate;
  }
}
