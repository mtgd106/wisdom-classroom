package com.vkls.wisdom.vo.vod;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VideoVisitorCountVo {

	@ApiModelProperty(value = "开始观看的时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String joinTime;

	@ApiModelProperty(value = "这个时间看视频的人数")
	private Integer userCount;


}

