package org.bcdtech.CustomEvents;

@SuppressWarnings("unused")
public interface CustomEventINTF {

    void setEventID(String eventID);

    void setEventValues(Object[] values);

     Object[] getEventValues();

     String getEventID();

     Boolean hasEventValues();
}
