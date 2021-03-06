package parkeersimulator.model;

import java.time.LocalDateTime;

import parkeersimulator.handler.ModelHandler;

/**
 * A model for time used in the simulation
 * @author ThowV
 *
 */
public class TimeModel extends AbstractModel{
	
	//Declaration of the time value that is used for the simulation
	LocalDateTime time;
    
	/**
	 * Constructor for TimeModel
	 * @param handler the handler this model should be added to.
	 */
	public TimeModel(ModelHandler handler) {
		super(handler);
		
		this.time = LocalDateTime.now();
	}

	@Override
	public ModelType getModelType() {
		return ModelType.TIME;
	}

	@Override
	public void tick() {
		advanceTime();
	}
	
	/**
     * A method that is used for incrementing the current time.
     */
    private void advanceTime(){
    	time = time.plusMinutes(1);
    }
	
	/**
     * Starts the time simulation
     */
	public void start() {
		getModelHandler().start();
	}
	
	/**
	 * Stops the currently running time simulation
	 */
	public void stop() {
		getModelHandler().stop();
	}
	
	/**
	 * Sets the pause amount between ticks
	 * @param amount the pause amount between ticks
	 */
	public void setTickPause(int amount) {
		getModelHandler().setTickPause(amount);
	}
	/**
	 * @return the pause amount between ticks
	 */
	public int getTickPause() { return getModelHandler().getTickPause(); }
	
	/**
	 * Sets the pause amount between ticks
	 * @param amount the pause amount between ticks
	 */
	public void tick(int amount) {
		getModelHandler().tick(amount);
	}
    
    /**
     * @return The current year.
     */
    public int getYear() { return time.getYear(); }
    
    /**
     * @return The current month of a year.
     */
    public int getMonth() { return time.getMonthValue(); }
    
    /**
     * @return The current day of the month (1-31)
     */
    public int getDayOfTheMonth() { return time.getDayOfMonth(); }
    
    /**
     * @return The current day of the week. (1-7)
     */
    public int getDay() { return time.getDayOfWeek().getValue() -1; }
    
    /**
     * @return The current hour of the day.
     */
    public int getHour() { return time.getHour(); }
    
    /**
     * @return The current minute of the hour.
     */
    public int getMinute() { return time.getMinute(); }
    
    /**
     * @return The current time in the format: int[day][hour][minute].
     */
    public LocalDateTime getTime() { return time; }
    
    /**
     * @return The bool that tells the program if it is the first day of the month
     */
    public boolean getIsFirstDayOfMonth() { return time.getDayOfMonth() == 1 ? true : false; }
    
}
