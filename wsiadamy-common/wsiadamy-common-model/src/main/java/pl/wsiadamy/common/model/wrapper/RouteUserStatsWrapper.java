package pl.wsiadamy.common.model.wrapper;

public class RouteUserStatsWrapper {

	Long count;

	Double totalPrice;

	Double totalDistance;

	Double averagePrice;

	Double totalPriceSaved;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public Double getTotalPriceSaved() {
		return totalPriceSaved;
	}

	public void setTotalPriceSaved(Double totalPriceSaved) {
		this.totalPriceSaved = totalPriceSaved;
	}
}
