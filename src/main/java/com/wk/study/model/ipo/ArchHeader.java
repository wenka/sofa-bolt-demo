package com.wk.study.model.ipo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  上午 09:54
 * Description:
 */
@ToString
@Getter
@Setter
public class ArchHeader {

    private Short msgLen;

    private Short dwDevId;

    private Short mdDevId;

    private Short upDevId;

    private Short setId;

    private Short dwSltNo;

    private Integer mdSltNo;

    private Integer upSltNo;

    private Integer archFlg;

    private String envNo;

    private String txnCod;
}
