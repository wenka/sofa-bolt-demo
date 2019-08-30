package com.wk.study.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/08/30  下午 03:55
 * Description:
 */
@Getter
@Setter
@ToString
public class MyResponse implements Serializable {

    private String response = "default response...";

}
