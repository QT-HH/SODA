package com.tak.soda.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MeetingDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoomInfo {
		@ApiModelProperty(value = "Roomd id")
		private long roomId;

		@ApiModelProperty(value = "Host id", example = "456278351")
		private long hostId;

		@ApiModelProperty(value = "Start time", example = "")
		private Date startTime;
		
		@ApiModelProperty(value = "End time", example = "")
		private Date endTime;
		
		@ApiModelProperty(value = "Room code", example = "A1B2C3D4E5")
		private String code;
		
		@ApiModelProperty(value = "Room Name", example = "삼성전자 면접")
		private String roomName;
		
		@ApiModelProperty(value = "Open info", example = "공개 여부")
		private int isPublic;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoomInfoPlus {
		@ApiModelProperty(value = "Roomd info")
		private RoomInfo roominfo;

		@ApiModelProperty(value = "Room Host Records", example = "")
		private String host;
		
		@ApiModelProperty(value = "Room User Records", example = "")
		private List<String> users = new ArrayList<>();
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoomResponse {
		@ApiModelProperty(value = "Room id", example = "1")
		private long roomId;
		
		@ApiModelProperty(value = "Room code", example = "A1B2C3D4E5")
		private String code;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InsertHostInfo {
		@ApiModelProperty(value = "Host id", example = "456278351")
		private long hostId;
		
		@ApiModelProperty(value = "Host nickname", example = "지은")
		private String hostNickName;
		
		@ApiModelProperty(value = "Room Name", example = "삼성전자 면접")
		private String roomName;
		
		@ApiModelProperty(value = "public of private", example = "1(public) 0(private)")
		private int isPublic;
		
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UpdateMemberInfo {
		@ApiModelProperty(value = "User Nickname", example = "지은")
		private String nickName;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UpdateRoomInfo {
		@ApiModelProperty(value = "Room id", example = "1")
		private long roomId;
		
		@ApiModelProperty(value = "Room Name", example = "삼성전자 면접")
		private String roomName;
		
		@ApiModelProperty(value = "Room Public 여부", example = "1(public), 0(private)")
		private int isPublic;
	}
}
