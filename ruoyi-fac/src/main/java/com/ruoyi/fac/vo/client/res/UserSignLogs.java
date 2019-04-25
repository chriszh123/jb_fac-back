package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgf
 * Date 2019/4/25 22:10
 * Description
 */
@Data
public class UserSignLogs implements Serializable {
    private static final long serialVersionUID = 478910455039386406L;

    private List<UserSignLog> result = new ArrayList<>();
    private int totalRow = 0;
    private int totalPage = 1;
}
