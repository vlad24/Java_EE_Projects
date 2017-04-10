package ru.spbu.math.ais.wrappers;

/**
 * @author vlad
 * Class encapsulating problem and optional additional information such as 
 * time of problem, place of detection, possible reasons, useful meta information for system) 
 */
public class ProblemReport {
	private String time;
	private String place;
	private String reason;
	private String crux;
	private String meta;

	
	public static class Builder{
		private StringBuilder cruxBuilder;
		private String time;
		private String place;
		private StringBuilder reasonBuilder;
		private String meta;
		
		/**
		 * Sets time of problem (optional)
		 * @param time time of problem
		 * @return this builder
		 */
		public Builder setTime(String time){
			this.time = time;
			return this;
		}
		
		/**
		 * Sets place of detection (optional)
		 * @param place
		 * @return
		 */
		public Builder setPlace(String place){
			this.place = place;
			return this;
		}
		
		/**
		 * Sets reason of the problem (optional)
		 * @param reason 
		 * @return this builder
		 */
		public Builder setReason(String reason){
			this.reasonBuilder = new StringBuilder(reason);
			return this;
		}
		
		/**
		 * Appends to existing reason of the problem (optional)
		 * @param adding extra info
		 * @return this builder
		 */
		public Builder appendToReason(String adding){
			if (adding != null){
				reasonBuilder.append(adding);
			}
			return this;
		}
		
		/**
		 * Sets the crux of the problem (mandatory)
		 * @param crux the mere problem's description
		 * @return this builder
		 */
		public Builder setCrux(String crux){
			this.cruxBuilder = new StringBuilder(crux);
			return this;
		}
		
		/**
		 * Appends extra info to the crux of the problem (optional)
		 * @param cruxAdding additional info
		 * @return this builder
		 */
		public Builder appendToCrux(String cruxAdding){
			if (cruxAdding != null){
				cruxBuilder.append(cruxAdding);
			}
			return this;
		}
		
		/**
		 * Sets meta information for system. Typically the metadata 
		 * may be used for further actions taken in order to resolve the problem 
		 * @param meta additional metadata regarding the problem to be used by system further
		 * @return this builder
		 */
		public Builder setMeta(String meta){
			this.meta = meta;
			return this;
		}
		
		/**
		 * Build the report. Note that IllegalStateException may be thrown if crux has nit been set
		 * @return problem report that has been being built
		 */
		public ProblemReport build(){
			if (cruxBuilder.length() != 0){
				String optionalReason = (reasonBuilder != null) ? this.reasonBuilder.toString() : null;
				return new ProblemReport(this.time, this.place, optionalReason, this.cruxBuilder.toString(), this.meta);
			}else{
				throw new IllegalStateException("Not fully built: problem's crux is absent");
			}
		}
		
		
	}
	
	private ProblemReport(String time, String place, String reason, String crux,  String meta) {
		super();
		this.time = time;
		this.place = place;
		this.reason = reason;
		this.crux = crux;
		this.meta = meta;
	}
	
	
	public String getTime() {
		return time;
	}
	public String getPlace() {
		return place;
	}
	public String getReason() {
		return reason;
	}
	public String getCrux() {
		return crux;
	}
	
	public String getMeta() {
		return meta;
	}
	
	/**
	 * Append some comment to problem's reason. It may be useful if the reason is not clear at first and
	 * some computation is needed to specify the potential reason of the problem
	 * @param addition additional Info
	 */
	public void appendToReason(String addition) {
		this.reason += " " + addition;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Problem")
			.append("(").append(crux).append(")")
			.append((time        == null) ? "" : (" at " + time))
			.append((place       == null) ? "" : (" in " + place))
			.append((reason      == null) ? "" : (" due to " + reason))
			.toString();
	}


}
