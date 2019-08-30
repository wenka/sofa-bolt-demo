package com.wk.study.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created with IDEA
 * author:wenka wkwenka@gmail.com
 * Date:2019/08/30  下午 03:53
 * Description: 统一请求
 */
@Getter
@Setter
@ToString
public class MyRequest implements Serializable {

    private String request;

}
