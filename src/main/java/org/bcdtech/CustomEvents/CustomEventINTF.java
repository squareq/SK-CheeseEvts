package org.bcdtech.CustomEvents;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface CustomEventINTF {

     void setEventID(String eventID);

     void setEventValues(Object[] values, @Nullable Integer position);

     void clearEventValues(@Nullable Integer position);

	 void clearEventValues();

     Object[] getEventValues();

     Object[] getEventValue(Integer position);


     String getEventID();

     Boolean hasEventValues();

     Boolean hasEventValue(Integer position);
}
