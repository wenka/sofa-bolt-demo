package com.wk.study.model.ipo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  上午 09:56
 * Description:
 */
@ToString
@Setter
@Getter
public class ReqRspHeader {

    private Short missId;

    private Short wstnId;

    private Integer connId;

    private Integer msgSeqNo; // 消息序列号

    private Short complCod;

    private Short isix;

    private Long resubmNo;

    private String mrkt;

    private String userId;

    private String resubFlag;
}
