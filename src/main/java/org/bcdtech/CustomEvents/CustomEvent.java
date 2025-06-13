package org.bcdtech.CustomEvents;

public class CustomEvent implements CustomEventINTF {

    String eventID;
    Object[] passedValues;

    public CustomEvent(String eventID){
        this.eventID = eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventValues(Object[] values){
        this.passedValues = values;
    }

    public Object[] getEventValues(){
        return this.passedValues;
    }

    public String getEventID(){
        return this.eventID;
    }

    public Boolean hasEventValues() {
        try{
            return this.passedValues.length >= 1;
        } catch(NullPointerException e){
            return false;
        }
    }
}
