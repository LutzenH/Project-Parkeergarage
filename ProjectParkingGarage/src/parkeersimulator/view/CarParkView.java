package parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import parkeersimulator.model.ParkingGarageModel;
import parkeersimulator.model.car.Car;
import parkeersimulator.model.car.Car.CarType;
import parkeersimulator.model.location.Location;
import parkeersimulator.model.location.Place;

/**
 * class of the view of the simulation.
 * 
 * @author ThowV
 * @author b-kuiper
 * @author LutzenH
 */
public class CarParkView extends AbstractView {
	
	///get screen size
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = screenSize.width;
	int height = screenSize.height;
	
	///The x and y offset used for positioning the entire parking garage on the window
    private static int X_OFFSET = 10; 
    private static int Y_OFFSET = 10;
    
    ///The x and y values used for sizing parking places
    public static int XY_LINEBETWEEN_PLACE = 1;
    public static int X_WIDTH_PLACE = 20;
    public static int Y_HEIGHT_PLACE = 10;
    
    ///The x and y offset used for spacing between parking places
    public static int X_OFFSET_PLACE = 0;
    public static int X_OFFSET_PLACE_FINAL = X_WIDTH_PLACE + X_OFFSET_PLACE;
    public static int Y_OFFSET_PLACE = 0;
    public static int Y_OFFSET_PLACE_FINAL = Y_HEIGHT_PLACE + Y_OFFSET_PLACE;
    
    ///The offset used for spacing between each level of the parking garage
    public static int X_OFFSET_FLOORS = 205;
    public static int X_OFFSET_FLOORS_DEFAULT = 55;
    
    ///The x offset used for spacing between each column of parking placed
    public static int X_OFFSET_COLUMN = 15;
    public static int X_OFFSET_COLUMN_FINAL = X_OFFSET_COLUMN + X_WIDTH_PLACE * 2;
    
    ///The x factor used for calculating which row each parking space goes in
    private static float X_ROWPOS_FACTOR = 0.5f;
    
    ///The factor used sizing the entire parking garage
    private static int SIZE_FACTOR = 1;
    
    private Dimension size;
    private Image carParkImage;    
    
    /**
     * Constructor for objects of class CarPark
     * @param model the ParkingGarageModel this view should get data from
     */
    public CarParkView(ParkingGarageModel model) {
    	super(model);
    	    	
        size = new Dimension(0, 0);
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
       return new Dimension(800, 500);
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    /**
     * Repaints this view.
     */
    @Override
    public void updateView() {
    	ParkingGarageModel parkingGarageModel = (ParkingGarageModel) model;
    	
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width + 1, size.height + 2);
        }
        Graphics graphics = carParkImage.getGraphics();
        
        drawCarPark(graphics, Y_OFFSET, X_OFFSET, parkingGarageModel);
        
        repaint();
    }

    /**
     * Draws the carpark at the disered location on the given Graphics device
     * @param graphics the graphics the carpark should be drawn on
     * @param xOffset the pixel x offset where the garage should be drawn
     * @param yOffset the pixel y offset where the garage should be drawn
     * @param model the ParkingGarageModel the drawn park should be based on
     */
    public static void drawCarPark(Graphics graphics, int xOffset, int yOffset, ParkingGarageModel model) {
        for(int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for(int row = 0; row < model.getNumberOfRows(); row++) {
                for(int places = 0; places < model.getNumberOfPlaces(); places++) {
                    Location location = new Location(floor, row, places);
                    
                    Place place = model.getPlaces()[floor][row][places];
                    Car car = place.getCar();

                    Color color = new Color(150, 150, 150);
                    
                    if(place.getCarType() == CarType.PASS)
                    	color = new Color(155, 155, 210);
                    
                    color = car == null ? color : car.getColor();
                    
                    drawPlace(graphics, location, color, place.getReserved(), xOffset, yOffset);
                }
            }
        }
    }
    
    /**
     * Paint a place on this car park view in a given color.
     */
    private static void drawPlace(Graphics graphics, Location location, Color color, boolean isReserved, int xOffset, int yOffset) {
        graphics.setColor(color);
        graphics.fillRect(
                ((int)Math.floor(location.getRow() * X_ROWPOS_FACTOR) * X_OFFSET_COLUMN_FINAL + (location.getRow() % 2) * X_OFFSET_PLACE_FINAL + xOffset) + location.getFloor() * (X_OFFSET_FLOORS + X_OFFSET_FLOORS_DEFAULT) * SIZE_FACTOR,
                (location.getPlace() * Y_OFFSET_PLACE_FINAL + yOffset) * SIZE_FACTOR,
                (X_WIDTH_PLACE - XY_LINEBETWEEN_PLACE) * SIZE_FACTOR,
                (Y_HEIGHT_PLACE - XY_LINEBETWEEN_PLACE) * SIZE_FACTOR); // TODO use dynamic size or constants
        
        if(color.equals(new Color(150, 150, 150)) &&  isReserved) {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(
            		 ((int)Math.floor(location.getRow() * X_ROWPOS_FACTOR) * X_OFFSET_COLUMN_FINAL + (location.getRow() % 2) * X_OFFSET_PLACE_FINAL + xOffset + 2) + location.getFloor() * (X_OFFSET_FLOORS + X_OFFSET_FLOORS_DEFAULT) * SIZE_FACTOR,
                     (location.getPlace() * Y_OFFSET_PLACE_FINAL + yOffset + 2) * SIZE_FACTOR,
                     (X_WIDTH_PLACE - XY_LINEBETWEEN_PLACE * 5) * SIZE_FACTOR,
                     (Y_HEIGHT_PLACE - XY_LINEBETWEEN_PLACE * 5) * SIZE_FACTOR);
        }
    }
    
    public static int[] getGarageSize(ParkingGarageModel model) {
    	//X axis
    	int totalSpaceBetweenPlaces_x = (model.getNumberOfRows() / 2) * X_OFFSET_PLACE;
    	int totalSpaceBetweenColumns = (model.getNumberOfRows() / 2 - 1) * X_OFFSET_COLUMN;
    	int totalSizePlaces_x = model.getNumberOfRows() * X_WIDTH_PLACE;
    	int totalSizeFloor = totalSpaceBetweenPlaces_x + totalSpaceBetweenColumns + totalSizePlaces_x;
    	int totalSizeFloors = totalSizeFloor * model.getNumberOfFloors();
    	
    	int totalSpaceBetweenFloors = (model.getNumberOfFloors() - 1) * X_OFFSET_FLOORS_DEFAULT;

    	int totalSizeGarage_x = totalSpaceBetweenFloors + totalSizeFloors;

    	//Y axis
    	int totalSpaceBetweenPlaces_y = model.getNumberOfPlaces() * Y_OFFSET_PLACE;
    	int totalSizePlaces_y = model.getNumberOfPlaces() * Y_HEIGHT_PLACE;
    	
    	int totalSizeGarage_y = totalSpaceBetweenPlaces_y + totalSizePlaces_y;
    	
    	//Update the space between floors
    	X_OFFSET_FLOORS = totalSizeFloor;

    	//Return our sizes in an array
    	return new int[] {totalSizeGarage_x, totalSizeGarage_y};
    }
}
