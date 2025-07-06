package org.bcdtech.CustomEvents;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CustomEvent implements CustomEventINTF {

    String eventID;
    ArrayList<Object> passedValues = new ArrayList<>();

    public CustomEvent(String eventID){
        this.eventID = eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventValues(Object[] values, @Nullable Integer position) {
		this.passedValues.clear();
		if (position == null) {
			for(Object v : values){
				this.passedValues.add(v);
			}
		} else {//
			this.passedValues.set(position-1, values);
		}
    }

	@Override
	public void clearEventValues(@Nullable Integer position) {
		System.out.println("Clear event values");
		if (position == null) {
			this.passedValues.clear();
		} else {
			this.passedValues.remove(position-1);
		}
	}

	@Override
	public void clearEventValues() {
		System.out.println("Clear event values");
		this.clearEventValues(null);
	}


	public Object[] getEventValues(){
		return this.passedValues.toArray();
    }

	@Override
	public Object[] getEventValue(Integer position) throws IndexOutOfBoundsException {
		Object v = this.passedValues.get(position-1);
		return new Object[]{v};
	}

	public String getEventID(){
        return this.eventID;
    }

    public Boolean hasEventValues() {
        try{
            return !this.passedValues.isEmpty();
        } catch(NullPointerException e){
            return false;
        }
    }

	@Override
	public Boolean hasEventValue(Integer position) {
		try{
			return (this.passedValues.get(position-1) != null);
		} catch(NullPointerException e){
			return false;
		}
	}
}
