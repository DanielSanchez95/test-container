package com.assertsolutions.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@JsonAutoDetect
@JsonSerialize
@ApiModel(description = "Request DTO Object")
public class Request {
    
    @JsonProperty(value = "id")
    @ApiModelProperty(dataType = "String")
    private String id;
    
    @JsonProperty(value = "nombre")
    @ApiModelProperty(dataType = "String")
    private String name;
    
    @JsonProperty(value = "duracion")
    @ApiModelProperty(dataType = "String")
    private String duration;
    
    @JsonProperty(value = "fechaInicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(dataType = "Date")
    private Date initDate;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the initDate
	 */
	public Date getInitDate() {
		return initDate;
	}

	/**
	 * @param initDate the initDate to set
	 */
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

    

}
