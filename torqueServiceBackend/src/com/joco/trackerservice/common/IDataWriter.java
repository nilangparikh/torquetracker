package com.joco.trackerservice.common;

public interface IDataWriter 
{
	void writeCarData(CarData data);
	void writeGPSData(GPSData data);
	void writePhysicalData(PhysicalData data);

}
