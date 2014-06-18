package at.runaway.splinedemo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.EditMode;
import at.runaway.splinedemo.model.FittingCurveType;
import at.runaway.splinedemo.model.FittingStrategyType;
import at.runaway.splinedemo.model.Node;
import at.runaway.splinedemo.model.PaintMode;
import at.runaway.splinedemo.model.PaintingCurveType;
import at.runaway.splinedemo.service.AlgorithmException;
import at.runaway.splinedemo.utils.Helper;

public class Controller {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	public static final int NO_SELECTION = -1;
	
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private boolean polygonVisible = true;

	private boolean pointsVisible = true;
	
	private boolean curveVisible = true;
	
	private boolean labelVisible = true;
	
	private boolean helperLinesVisible = false;
	
	private DataModel model;
	
	private PaintMode paintMode = PaintMode.PAINTING;
	
	private EditMode editMode = EditMode.NEW;
		
	private transient List<ControllerListener> listeners = new ArrayList<ControllerListener>();
	
	
	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public Controller() {
		initModel();
	}

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public void resetPointList() {
		model.resetPointList();
		editMode = EditMode.NEW;
		notifyModelListeners();
	}
	
	public boolean isModelEmpty() {
		return model == null || model.getControlPoints().isEmpty();
	}
	
	public List<Node> getCurveFittingPoints() {
		return model.getCurveFittingPoints();
	}
	
	public List<Node> getControlPoints() {
		return model.getControlPoints();
	}
	
	public List<Node> getCurveFittingDerivates() {
		return model.getCurveFittingDerivates();
	}
	
	public List<Node> getCurvePoints() throws AlgorithmException {
		return model.getPaintStrategy().createCurvePoints(model);
	}

	
	public void addPoint(Node p) {
		model.getControlPoints().add(p);
		model.calculateKnots();
		if (model.getPaintingCurveType() == PaintingCurveType.BEZIER) {
			int degree = model.getControlPoints().size() - 1;
			setDegree(degree);
		}
	    notifyModelListeners();
	}

	public void addCurveFittingPoint(Node p, Node derivate) {
		model.getCurveFittingPoints().add(p);
		model.getCurveFittingDerivates().add(derivate);
	    notifyModelListeners();
	}
	
	public void removePoint(int index) {
		if (Helper.isIndexInRange(model.getControlPoints(), index)) {
			model.getControlPoints().remove(index);
			model.calculateKnots();
			model.setSelectedIndex(NO_SELECTION);
			notifyModelListeners();
		}
	}
	
	public void removeCurveFittingPoint(int index) {
		if (Helper.isIndexInRange(model.getCurveFittingPoints(), index)) {
			model.getCurveFittingPoints().remove(index);
			model.getCurveFittingDerivates().remove(index);
			model.setSelectedIndex(NO_SELECTION);
			notifyModelListeners();
		}
	}
	
	public void replacePoint(Node oldPoint, Node newPoint) {
		List<Node> list = model.getControlPoints();
		if (list.contains(oldPoint)) {
			int index = list.indexOf(oldPoint);
			list.set(index, newPoint);
			notifyModelListeners();
		}
	}

	public void replaceCurveFittingPoint(Node oldPoint, Node newPoint) {
		List<Node> list = model.getCurveFittingPoints();
		if (list.contains(oldPoint)) {
			int index = list.indexOf(oldPoint);
			list.set(index, newPoint);
			notifyModelListeners();
		}
	}
	
	public void replaceFittingDerivate(int index, Node derivatePoint) {
		List<Node> d = model.getCurveFittingDerivates();
		if (Helper.isIndexInRange(d, index)) {
			d.set(index, derivatePoint);
			notifyModelListeners();
		}		
	}


	public double[] calculateKnotVector() throws AlgorithmException {
		int size = model.getControlPoints().size() - 1;
		if (paintMode == PaintMode.PAINTING) {
			return model.getPaintStrategy().createKnotVector(model, model.getDegree(), size);
		} else {
			// fitting algorithm needs p=3
			model.setDegree(3);
			return model.getPaintStrategy().createKnotVector(model, model.getDegree(), size);
		}
	}
	
	public List<Node> calculateKnotVectorPoints() throws AlgorithmException {
		return model.getPaintStrategy().calculateKnotVectorPoints(model);
	}
	
	public void calculateControlPoints() {
		List<Node> controlPoints = model.getFittingStrategy().calculate(model);
		model.setControlPoints(controlPoints);
	}
	
	public List<List<Node>> createBasisFunctionPoints() throws AlgorithmException { 
		int n = model.getControlPoints().size() - 1;
		int p = (model).getDegree();
		return model.getPaintStrategy().createBasisFunctionPoints(model, n, p); 
	}
	
	public void loadData(File file) throws IOException, ClassNotFoundException, AlgorithmException {
		InputStream fis = new FileInputStream(file); 
		ObjectInputStream o = new ObjectInputStream(fis);
		model.resetPointList();
		model = (DataModel)o.readObject();
		calculateKnotVector();
		notifyModelListeners();
		fis.close(); 
	}
	
	public void saveData(File file) throws IOException {
    	OutputStream fos = new FileOutputStream(file); 
		ObjectOutputStream o = new ObjectOutputStream(fos); 
		o.writeObject(model);
		fos.close(); 
	}
	
	public void setKnots(double[] knots) {
		model.setKnots(knots);
	}
	
	public void setSelectedKnotIndex(int index) {
		model.setSelectedKnotIndex(index);
	}
	
	public int getSelectedKnotIndex() {
		return model.getSelectedKnotIndex();
	}
	
	public void replaceKnot(int knotIndex, double u) {
		model.replaceKnot(knotIndex, u);
		notifyModelListeners();
	}
	
	public int getDegree() {
		return model.getDegree();
	}
	
	public void setDegree(int degree) {
		model.setDegree(degree);
		notifyModelListeners();
	}
	
	public int getSelectedIndex() {
		return model.getSelectedIndex();
	}
	
	public void setSelectedIndex(int selectedIndex) {
		model.setSelectedIndex(selectedIndex);
		notifyModelListeners();
	}	
	
	public boolean isPolygonVisible() {
		return polygonVisible;
	}

	public void setPolygonVisible(boolean polygonVisible) {
		this.polygonVisible = polygonVisible;
		notifyModelListeners();
	}

	public boolean isPointsVisible() {
		return pointsVisible;
	}

	public void setPointsVisible(boolean pointsVisible) {
		this.pointsVisible = pointsVisible;
		notifyModelListeners();
	}

	public boolean isCurveVisible() {
		return curveVisible;
	}

	public void setCurveVisible(boolean curveVisible) {
		this.curveVisible = curveVisible;
		notifyModelListeners();
	}

	public boolean isLabelVisible() {
		return labelVisible;
	}

	public void setLabelVisible(boolean labelVisible) {
		this.labelVisible = labelVisible;
		notifyModelListeners();
	}

	public boolean isHelperLinesVisible() {
		return helperLinesVisible;
	}

	public void setHelperLinesVisible(boolean helperLinesVisible) {
		this.helperLinesVisible = helperLinesVisible;
		notifyModelListeners();
	}

	public PaintMode getPaintMode() {
		return paintMode;
	}

	public void setPaintMode(PaintMode paintMode) {
		this.paintMode = paintMode;
		if (paintMode == PaintMode.FITTING) {
			model.setPaintingCurveType(PaintingCurveType.BSPLINE);
			model.setDegree(3);
		} else {
			model.setPaintingCurveType(PaintingCurveType.BEZIER);
		}
		notifyModelListeners();
	}

	public EditMode getEditMode() {
		return editMode;
	}

	public void setEditMode(EditMode editMode) {
		this.editMode = editMode;
	}

	public PaintingCurveType getPaintingCurveType() {
		return model.getPaintingCurveType();
	}

	public void setPaintingCurveType(PaintingCurveType paintingCurveType) {
		model.setPaintingCurveType(paintingCurveType);
		notifyModelListeners();
	}

	public FittingCurveType getFittingCurveType() {
		return model.getFittingCurveType();
	}

	public void setFittingCurveType(FittingCurveType fittingCurveType) {
		this.setFittingCurveType(fittingCurveType);
		notifyModelListeners();
	}	
	
	public void setFittingStrategyType(FittingStrategyType fittingStrategyType) {
		model.setFittingStrategyType(fittingStrategyType);
		notifyModelListeners();
	}
	
	// ------- Listener Methods ------------
	
	public void addModelListener(ControllerListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
			notifyModelListener(listener);
		}
	}
	
	public void removeAllModelListeners() {
		this.listeners = new ArrayList<ControllerListener>();
	}

	public void removeModelListener(ControllerListener listener) {
		this.listeners.remove(listener);
	}

	protected void notifyModelListeners() {
		for (ControllerListener listener : this.listeners) {
			notifyModelListener(listener);
		}
	}

	protected void notifyModelListener(ControllerListener listener) {
		listener.modelChanged();
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	private void initModel() {
		this.model = new DataModel();
	}	
}
