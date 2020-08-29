package com.test.appgate.data.dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "register_table")
public class Response{



	@PrimaryKey(autoGenerate = true)
	private int id;

	@NonNull
	@ColumnInfo(name = "user")
	private String user;

	@NonNull
	@ColumnInfo(name = "result")
	private boolean result;

	@SerializedName("sunrise")
	private String sunrise;

	@SerializedName("lng")
	private double lng;

	@SerializedName("countryCode")
	private String countryCode;

	@SerializedName("gmtOffset")
	private int gmtOffset;

	@SerializedName("rawOffset")
	private int rawOffset;

	@SerializedName("sunset")
	private String sunset;

	@SerializedName("timezoneId")
	private String timezoneId;

	@SerializedName("dstOffset")
	private int dstOffset;

	@SerializedName("countryName")
	private String countryName;

	@SerializedName("time")
	private String time;


	@SerializedName("lat")
	private double lat;

	public void setId(int id) {
		this.id = id;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setGmtOffset(int gmtOffset) {
		this.gmtOffset = gmtOffset;
	}

	public void setRawOffset(int rawOffset) {
		this.rawOffset = rawOffset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

	public void setDstOffset(int dstOffset) {
		this.dstOffset = dstOffset;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getSunrise(){
		return sunrise;
	}

	public double getLng(){
		return lng;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public int getGmtOffset(){
		return gmtOffset;
	}

	public int getRawOffset(){
		return rawOffset;
	}

	public String getSunset(){
		return sunset;
	}

	public String getTimezoneId(){
		return timezoneId;
	}

	public int getDstOffset(){
		return dstOffset;
	}

	public String getCountryName(){
		return countryName;
	}

	public String getTime(){
		return time;
	}

	public double getLat(){
		return lat;
	}

	public int getId() {
		return id;
	}

	@NonNull
	public String getUser() { return user; }

	public void setUser(@NonNull String user) {
		this.user = user;
	}


	public void setResult(boolean result) {
		this.result = result;
	}
	@NonNull
	public boolean getResult() {
		return result;
	}
}