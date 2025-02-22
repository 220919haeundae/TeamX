package com.teamx.exsite.model.vo.community;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChildrenReply {
	private int childrenReplyNo;
	private String childrenReplyContent;
	
	//댓글 조회 시 날짜+시간까지 노출시키기 위한 필드(formattedPostDatetime)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Timestamp childrenReplyDatetime;
	
	private String childrenReplyStatus;
	private int parentReplyNo;
	private int userNo;
	private String userId;
	
	// 포맷된 날짜를 반환하는 메서드
    public String getFormattedParentReplyDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(childrenReplyDatetime);
    }
}
