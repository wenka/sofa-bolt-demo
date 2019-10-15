package com.wk.study.model.ipo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/10/14  上午 09:58
 * Description:
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderData {

    private String nonTrdTypCod;

    private String invtAcctID;

    private String membExcIdCod;

    private String ordrNo;

    private String dateLstUpdDat;

    private String ordrQty;

    private String ordrPrc;

    private String ordrEntTim;

    private String amount;

    private String partSubGrpIdCod;

    private String partNoTxt;

    private String partOsSubGrpIdCod;

    private String partOsNoTxt;

    private String acctTypCod;

    private String brnId;

    private String userOrdNum;

    private String text;

    private String deletedFlag;

    private String statusFlag;
}
