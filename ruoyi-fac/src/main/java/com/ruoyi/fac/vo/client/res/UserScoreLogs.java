package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgf
 * Date 2019/5/1 19:09
 * Description
 *
 * @author zgf
 */
@Data
public class UserScoreLogs implements Serializable {
    private static final long serialVersionUID = 4523438212232090553L;

    private List<UserScoreLog> result = null;
}
